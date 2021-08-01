package org.siouan.frontendgradleplugin;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiPredicate;
import javax.annotation.Nonnull;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.Directory;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.siouan.frontendgradleplugin.domain.model.Environment;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.SystemProxySettings;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.gradle.AssembleTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CheckTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CleanTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.EnableYarnBerryTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.FrontendExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallDependenciesTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallYarnTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.NodeInstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.PublishTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.TaskLoggerConfigurer;
import org.siouan.frontendgradleplugin.infrastructure.gradle.YarnGlobalInstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleLoggerAdapter;
import org.siouan.frontendgradleplugin.infrastructure.provider.ArchiverProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.provider.ChannelProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.provider.FileManagerImpl;
import org.siouan.frontendgradleplugin.infrastructure.provider.HttpClientProviderImpl;

/**
 * Main plugin class that bootstraps the plugin by declaring its DSL and its tasks.
 * <ul>
 * <li>The plugin applies the Gradle Base plugin and the Gradle Publishing plugin, to attach its tasks to Gradle
 * lifecycle tasks.</li>
 * <li>Tasks are registered lazily thanks to the use of the configuration avoidance API.</li>
 * <li>Task properties are mapped to the plugin extension (DSL) using the lazy configuration API, allowing their
 * calculation to be delayed until it is required.</li>
 * <li>The plugin initializes a bean registry, a mechanism that handles Inversion of Control, and is in charge of
 * bean instanciation on-the-fly.</li>
 * </ul>
 *
 * @see <a href="https://docs.gradle.org/current/userguide/task_configuration_avoidance.html">Task configuration
 * avoidance</a>
 * @see <a href="https://docs.gradle.org/current/userguide/lazy_configuration.html">Lazy configuration</a>
 */
public class FrontendGradlePlugin implements Plugin<Project> {

    /**
     * Name of the task that assembles the frontend.
     */
    public static final String ASSEMBLE_TASK_NAME = "assembleFrontend";

    /**
     * Name of the task that checks the frontend.
     */
    public static final String CHECK_TASK_NAME = "checkFrontend";

    /**
     * Name of the task that cleans the frontend.
     */
    public static final String CLEAN_TASK_NAME = "cleanFrontend";

    /**
     * Name of the task that publishes the frontend.
     */
    public static final String PUBLISH_TASK_NAME = "publishFrontend";

    /**
     * Name of the task that installs a Node.js distribution.
     */
    public static final String DEFAULT_NODE_INSTALL_DIRNAME = "node";

    /**
     * URL pattern used to download the Node.js distribution.
     */
    public static final String DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN = "vVERSION/node-vVERSION-ARCH.TYPE";

    /**
     * URL pattern used to download the Node.js distribution.
     */
    public static final String DEFAULT_NODE_DISTRIBUTION_URL_ROOT = "https://nodejs.org/dist/";

    /**
     * Default port for the proxy server handling HTTP requests.
     */
    public static final int DEFAULT_HTTP_PROXY_PORT = 80;

    /**
     * Default port for the proxy server handling HTTPS requests.
     */
    public static final int DEFAULT_HTTPS_PROXY_PORT = 443;

    /**
     * NPM command used to install a Yarn Classic 1.x distribution globally.
     *
     * @since 6.0.0
     */
    public static final String DEFAULT_YARN_GLOBAL_INSTALL_SCRIPT = "install -g yarn";

    /**
     * NPM command used to install a Yarn distribution in the current project.
     *
     * @since 6.0.0
     */
    public static final String DEFAULT_YARN_INSTALL_SCRIPT_PREFIX = "set version ";

    /**
     * NPM command used to install a Yarn Berry distribution in the current project.
     *
     * @since 6.0.0
     */
    public static final String YARN_BERRY_VERSION = "berry";

    /**
     * Name of the task that installs frontend dependencies.
     */
    public static final String INSTALL_FRONTEND_TASK_NAME = "installFrontend";

    /**
     * Name of the task that installs a Node distribution.
     */
    public static final String INSTALL_NODE_TASK_NAME = "installNode";

    /**
     * Name of the environment variable providing the path to a global Node.js installation.
     */
    public static final String NODEJS_HOME_ENV_VAR = "FGP_NODEJS_HOME";

    /**
     * Name of the task that installs a Yarn Classic 1.x distribution globally.
     */
    public static final String INSTALL_YARN_GLOBALLY_TASK_NAME = "installYarnGlobally";

    /**
     * Name of the task that enables Yarn Berry.
     */
    public static final String ENABLE_YARN_BERRY_TASK_NAME = "enableYarnBerry";

    /**
     * Name of the task that installs a Yarn distribution.
     */
    public static final String INSTALL_YARN_TASK_NAME = "installYarn";

    public static final String GRADLE_CHECK_TASK_NAME = LifecycleBasePlugin.CHECK_TASK_NAME;

    /**
     * Name of the npm/Yarn command that shall be executed to install frontend dependencies.
     */
    private static final String DEFAULT_INSTALL_SCRIPT = "install";

    /**
     * Root name of the plugin extension.
     */
    private static final String EXTENSION_NAME = "frontend";

    /**
     * The Gradle group in which all this plugin's tasks will be categorized.
     */
    private static final String TASK_GROUP = "Frontend";

    public void apply(final Project project) {
        project.getPluginManager().apply(BasePlugin.class);
        project.getPluginManager().apply(PublishingPlugin.class);

        final Path nodejsHomePath = getEnvironmentVariable(NODEJS_HOME_ENV_VAR).map(Paths::get).orElse(null);
        final SystemProxySettings systemProxySettings = new SystemProxySettings(SystemUtils.getHttpProxyHost(),
            SystemUtils.getHttpProxyPort().orElse(DEFAULT_HTTP_PROXY_PORT), SystemUtils.getHttpsProxyHost(),
            SystemUtils.getHttpsProxyPort().orElse(DEFAULT_HTTPS_PROXY_PORT), SystemUtils.getNonProxyHosts());
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName(),
            new Environment(nodejsHomePath), systemProxySettings);

        final FrontendExtension extension = project
            .getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project);

        extension.getNodeDistributionProvided().convention(false);
        extension.getNodeDistributionUrlRoot().convention(DEFAULT_NODE_DISTRIBUTION_URL_ROOT);
        extension.getNodeDistributionUrlPathPattern().convention(DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN);
        extension
            .getNodeInstallDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_NODE_INSTALL_DIRNAME));
        extension.getYarnEnabled().convention(false);
        extension.getYarnGlobalInstallScript().convention(DEFAULT_YARN_GLOBAL_INSTALL_SCRIPT);
        extension.getYarnBerryEnableScript().convention(DEFAULT_YARN_INSTALL_SCRIPT_PREFIX + YARN_BERRY_VERSION);
        extension
            .getYarnInstallScript()
            .convention(extension
                .getYarnVersion()
                .map(yarnVersion -> DEFAULT_YARN_INSTALL_SCRIPT_PREFIX + yarnVersion)
                .orElse(DEFAULT_YARN_INSTALL_SCRIPT_PREFIX));
        extension.getInstallScript().convention(DEFAULT_INSTALL_SCRIPT);
        extension.getPackageJsonDirectory().convention(project.getLayout().getProjectDirectory().getAsFile());
        extension.getHttpProxyPort().convention(DEFAULT_HTTP_PROXY_PORT);
        extension.getHttpsProxyPort().convention(DEFAULT_HTTPS_PROXY_PORT);
        extension.getVerboseModeEnabled().convention(false);

        final TaskContainer taskContainer = project.getTasks();
        taskContainer.register(INSTALL_NODE_TASK_NAME, NodeInstallTask.class,
            task -> configureNodeInstallTask(task, extension));
        taskContainer.register(INSTALL_YARN_GLOBALLY_TASK_NAME, YarnGlobalInstallTask.class,
            task -> configureYarnGlobalInstallTask(taskContainer, task, extension));
        taskContainer.register(ENABLE_YARN_BERRY_TASK_NAME, EnableYarnBerryTask.class,
            task -> configureYarnBerryEnablingTask(taskContainer, task, extension));
        taskContainer.register(INSTALL_YARN_TASK_NAME, InstallYarnTask.class,
            task -> configureYarnInstallTask(taskContainer, task, extension));
        taskContainer.register(INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class,
            task -> configureInstallTask(taskContainer, task, extension));
        taskContainer.register(CLEAN_TASK_NAME, CleanTask.class,
            task -> configureCleanTask(taskContainer, task, extension));
        taskContainer.register(CHECK_TASK_NAME, CheckTask.class,
            task -> configureCheckTask(taskContainer, task, extension));
        taskContainer.register(ASSEMBLE_TASK_NAME, AssembleTask.class,
            task -> configureAssembleTask(taskContainer, task, extension));
        taskContainer.register(PUBLISH_TASK_NAME, PublishTask.class,
            task -> configurePublishTask(taskContainer, task, extension));

        configureDependency(taskContainer, BasePlugin.CLEAN_TASK_NAME, CLEAN_TASK_NAME, CleanTask.class);
        configureDependency(taskContainer, BasePlugin.ASSEMBLE_TASK_NAME, ASSEMBLE_TASK_NAME, AssembleTask.class);
        configureDependency(taskContainer, GRADLE_CHECK_TASK_NAME, CHECK_TASK_NAME, CheckTask.class);
        configureDependency(taskContainer, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME, PUBLISH_TASK_NAME,
            PublishTask.class);

        final String beanRegistryId = project.getPath();
        Beans.initBeanRegistry(beanRegistryId);
        Beans.registerBean(beanRegistryId, systemProxySettings);
        Beans.registerBean(beanRegistryId, platform);
        Beans.registerBean(beanRegistryId, GradleLoggerAdapter.class);
        Beans.registerBean(beanRegistryId, FileManagerImpl.class);
        Beans.registerBean(beanRegistryId, ChannelProviderImpl.class);
        Beans.registerBean(beanRegistryId, ArchiverProviderImpl.class);
        Beans.registerBean(beanRegistryId, HttpClientProviderImpl.class);
        try {
            project
                .getGradle()
                .addListener(new TaskLoggerConfigurer(Beans.getBean(beanRegistryId, BeanRegistry.class), extension));
            project.getLogger().debug("Platform: {}", Beans.getBean(beanRegistryId, Platform.class));
        } catch (final BeanRegistryException e) {
            throw new GradleException("Cannot get instance of bean registry", e);
        }

        project.afterEvaluate(p -> finalizeExtension(extension));
    }

    /**
     * Finalizes configuration by applying additional conventions and values based on an evaluated extension.
     *
     * @param extension Extension.
     */
    private void finalizeExtension(@Nonnull final FrontendExtension extension) {
        // When a distribution is provided, the install directory is optional. The plugin may use environment variables
        // to locate the executables required.
        if (extension.getNodeDistributionProvided().get()) {
            extension.getNodeInstallDirectory().convention((Directory) null);
        }
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureNodeInstallTask(final NodeInstallTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Node distribution.");
        task.getNodeVersion().set(extension.getNodeVersion());
        task.getNodeDistributionUrlRoot().set(extension.getNodeDistributionUrlRoot());
        task.getNodeDistributionUrlPathPattern().set(extension.getNodeDistributionUrlPathPattern());
        task.getNodeDistributionServerUsername().set(extension.getNodeDistributionServerUsername());
        task.getNodeDistributionServerPassword().set(extension.getNodeDistributionServerPassword());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getHttpProxyHost().set(extension.getHttpProxyHost());
        task.getHttpProxyPort().set(extension.getHttpProxyPort());
        task.getHttpProxyUsername().set(extension.getHttpProxyUsername());
        task.getHttpProxyPassword().set(extension.getHttpProxyPassword());
        task.getHttpsProxyHost().set(extension.getHttpsProxyHost());
        task.getHttpsProxyPort().set(extension.getHttpsProxyPort());
        task.getHttpsProxyUsername().set(extension.getHttpsProxyUsername());
        task.getHttpsProxyPassword().set(extension.getHttpsProxyPassword());
        task.setOnlyIf(t -> !extension.getNodeDistributionProvided().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     * @since 6.0.0
     */
    private void configureYarnGlobalInstallTask(final TaskContainer taskContainer, final YarnGlobalInstallTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs Yarn globally.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnGlobalInstallScript().set(extension.getYarnGlobalInstallScript());
        task.setOnlyIf(t -> extension.getYarnEnabled().get());
        configureDependency(taskContainer, task, INSTALL_NODE_TASK_NAME, NodeInstallTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureYarnBerryEnablingTask(final TaskContainer taskContainer, final EnableYarnBerryTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Enables Yarn Berry.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getYarnBerryEnableScript().set(extension.getYarnBerryEnableScript());
        task.setOnlyIf(t -> extension.getYarnEnabled().get());
        configureDependency(taskContainer, task, INSTALL_YARN_GLOBALLY_TASK_NAME, YarnGlobalInstallTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureYarnInstallTask(final TaskContainer taskContainer, final InstallYarnTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs the appropriate Yarn distribution in the project.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getYarnVersion().set(extension.getYarnVersion());
        task.getYarnInstallScript().set(extension.getYarnInstallScript());
        task.setOnlyIf(t -> extension.getYarnEnabled().get());
        configureDependency(taskContainer, task, ENABLE_YARN_BERRY_TASK_NAME, EnableYarnBerryTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureInstallTask(final TaskContainer taskContainer, final InstallDependenciesTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs frontend dependencies.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getInstallScript().set(extension.getInstallScript());
        configureDependency(taskContainer, task, INSTALL_NODE_TASK_NAME, NodeInstallTask.class);
        configureDependency(taskContainer, task, INSTALL_YARN_TASK_NAME, InstallYarnTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureCleanTask(final TaskContainer taskContainer, final CleanTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getCleanScript().set(extension.getCleanScript());
        task.setOnlyIf(t -> extension.getCleanScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class,
            (cleanTask, installDependenciesTask) -> cleanTask.getCleanScript().isPresent());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureCheckTask(final TaskContainer taskContainer, final CheckTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getCheckScript().set(extension.getCheckScript());
        task.setOnlyIf(t -> extension.getCheckScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class,
            (checkTask, installDependenciesTask) -> checkTask.getCheckScript().isPresent());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureAssembleTask(final TaskContainer taskContainer, final AssembleTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getAssembleScript().set(extension.getAssembleScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class,
            (assembleTask, installDependenciesTask) -> assembleTask.getAssembleScript().isPresent());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configurePublishTask(final TaskContainer taskContainer, final PublishTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Publishes frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getPublishScript().set(extension.getPublishScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent() && extension.getPublishScript().isPresent());
        configureDependency(taskContainer, task, ASSEMBLE_TASK_NAME, AssembleTask.class,
            (publishTask, assembleTask) -> publishTask.getPublishScript().isPresent());
    }

    /**
     * Configures a static dependency between 2 tasks: task {@code taskName} depends on task {@code dependsOnTaskName}.
     *
     * @param taskContainer Task container.
     * @param taskName Name of the task that may depend on another task.
     * @param dependsOnTaskName Name of the depending task.
     * @param dependsOnTaskClass Depending task class.
     * @param <D> Type of the depending task.
     */
    private <D extends Task> void configureDependency(final TaskContainer taskContainer, final String taskName,
        final String dependsOnTaskName, final Class<D> dependsOnTaskClass) {
        taskContainer.named(taskName, Task.class,
            task -> configureDependency(taskContainer, task, dependsOnTaskName, dependsOnTaskClass));
    }

    /**
     * Configures a static dependency between 2 tasks: task {@code taskName} depends on task {@code dependsOnTaskName}.
     *
     * @param taskContainer Task container.
     * @param task Task that may depend on another task.
     * @param dependsOnTaskName Name of the depending task.
     * @param dependsOnTaskClass Depending task class.
     * @param <T> Type of the dependent task.
     * @param <D> Type of the depending task.
     */
    private <T extends Task, D extends Task> void configureDependency(final TaskContainer taskContainer, T task,
        final String dependsOnTaskName, final Class<D> dependsOnTaskClass) {
        configureDependency(taskContainer, task, dependsOnTaskName, dependsOnTaskClass, null);
    }

    /**
     * Configures a dynamic dependency between 2 tasks, based on the evaluation of a condition: : task {@code taskName}
     * depends on task {@code dependsOnTaskName} if the condition is verified.
     *
     * @param taskContainer Task container.
     * @param task Task that may depend on another task.
     * @param dependsOnTaskName Name of the depending task.
     * @param dependsOnTaskClass Depending task class.
     * @param condition Function to configure any of the 2 tasks and return a decision to make the task depends on the
     * depending task.
     * @param <T> Type of the dependent task.
     * @param <D> Type of the depending task.
     */
    private <T extends Task, D extends Task> void configureDependency(final TaskContainer taskContainer, T task,
        final String dependsOnTaskName, final Class<D> dependsOnTaskClass, final BiPredicate<T, D> condition) {
        final TaskProvider<D> dependsOnTask = taskContainer.named(dependsOnTaskName, dependsOnTaskClass);
        if ((condition == null) || canDependOn(task, dependsOnTask, condition)) {
            task.dependsOn(dependsOnTask.getName());
        }
    }

    /**
     * Whether the given task can depend on the depending task using its provider.
     *
     * @param task Task.
     * @param dependsOnTaskProvider Provider of the depending task.
     * @param condition Function to configure any of the 2 tasks and return a decision to make the task depends on the
     * depending task.
     * @param <T> Type of the dependent task.
     * @param <D> Type of the depending task.
     * @return {@code true} if the depending task exists in the provider and if the condition is met.
     */
    private <T extends Task, D extends Task> boolean canDependOn(final T task,
        final TaskProvider<D> dependsOnTaskProvider, final BiPredicate<T, D> condition) {
        return dependsOnTaskProvider.isPresent() && condition.test(task, dependsOnTaskProvider.get());
    }

    /**
     * Gets the value of an environment variable.
     *
     * @param variableName Variable name.
     * @return Variable value.
     */
    @Nonnull
    private Optional<String> getEnvironmentVariable(@Nonnull final String variableName) {
        return Optional.ofNullable(System.getenv(variableName)).filter(value -> !value.trim().isEmpty());
    }
}
