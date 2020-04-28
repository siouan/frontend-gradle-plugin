package org.siouan.frontendgradleplugin;

import java.util.function.BiPredicate;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.gradle.AssembleTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CheckTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CleanTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.FrontendExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.NodeInstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.PublishTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.TaskLoggerConfigurer;
import org.siouan.frontendgradleplugin.infrastructure.gradle.YarnInstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleLoggerAdapter;
import org.siouan.frontendgradleplugin.infrastructure.provider.ArchiverProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.provider.ChannelProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.provider.FileManagerImpl;

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
     * Name of the task that installs a Node distribution.
     */
    public static final String DEFAULT_NODE_INSTALL_DIRNAME = "node";

    /**
     * Default port for the proxy server.
     */
    public static final int DEFAULT_PROXY_PORT = 8080;

    /**
     * Name of the task that installs a Yarn distribution.
     */
    public static final String DEFAULT_YARN_INSTALL_DIRNAME = "yarn";

    /**
     * Name of the task that installs frontend dependencies.
     */
    public static final String INSTALL_TASK_NAME = "installFrontend";

    /**
     * Name of the task that installs a Node distribution.
     */
    public static final String NODE_INSTALL_TASK_NAME = "installNode";

    /**
     * Name of the task that installs a Yarn distribution.
     */
    public static final String YARN_INSTALL_TASK_NAME = "installYarn";

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

        final FrontendExtension extension = project
            .getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project);
        extension.getNodeDistributionProvided().convention(false);
        extension
            .getNodeInstallDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_NODE_INSTALL_DIRNAME));
        extension.getYarnEnabled().convention(false);
        extension.getYarnDistributionProvided().convention(false);
        extension
            .getYarnInstallDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_YARN_INSTALL_DIRNAME));
        extension.getInstallScript().convention(DEFAULT_INSTALL_SCRIPT);
        extension.getPackageJsonDirectory().convention(project.getLayout().getProjectDirectory().getAsFile());
        extension.getProxyPort().convention(DEFAULT_PROXY_PORT);
        extension.getVerboseModeEnabled().convention(false);

        final TaskContainer taskContainer = project.getTasks();
        taskContainer.register(NODE_INSTALL_TASK_NAME, NodeInstallTask.class,
            task -> configureNodeInstallTask(task, extension));
        taskContainer.register(YARN_INSTALL_TASK_NAME, YarnInstallTask.class,
            task -> configureYarnInstallTask(task, extension));
        taskContainer.register(INSTALL_TASK_NAME, InstallTask.class,
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

        Beans.registerBean(new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName()));
        Beans.registerBean(GradleLoggerAdapter.class);
        Beans.registerBean(FileManagerImpl.class);
        Beans.registerBean(ChannelProviderImpl.class);
        Beans.registerBean(ArchiverProviderImpl.class);
        try {
            project.getGradle().addListener(new TaskLoggerConfigurer(Beans.getBean(BeanRegistry.class), extension));
        } catch (final BeanRegistryException e) {
            throw new GradleException("Cannot get instance of bean registry", e);
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
        task.getNodeDistributionUrl().set(extension.getNodeDistributionUrl());
        task.getNodeDistributionUrlPattern().set(extension.getNodeDistributionUrlPattern());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getProxyHost().set(extension.getProxyHost());
        task.getProxyPort().set(extension.getProxyPort());
        task.getNodeDistributionRequestAuthorization().set(extension.getNodeDistributionRequestAuthorization());
        task.setOnlyIf(t -> !extension.getNodeDistributionProvided().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureYarnInstallTask(final YarnInstallTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Yarn distribution.");
        task.getYarnVersion().set(extension.getYarnVersion());
        task.getYarnDistributionUrl().set(extension.getYarnDistributionUrl());
        task.getYarnDistributionUrlPattern().set(extension.getYarnDistributionUrlPattern());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getProxyHost().set(extension.getProxyHost());
        task.getProxyPort().set(extension.getProxyPort());
        task.getYarnDistributionRequestAuthorization().set(extension.getYarnDistributionRequestAuthorization());
        task.setOnlyIf(t -> extension.getYarnEnabled().get() && !extension.getYarnDistributionProvided().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureInstallTask(final TaskContainer taskContainer, final InstallTask task,
        final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs/updates frontend dependencies.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getInstallScript().set(extension.getInstallScript());
        configureDependency(taskContainer, task, NODE_INSTALL_TASK_NAME, NodeInstallTask.class);
        configureDependency(taskContainer, task, YARN_INSTALL_TASK_NAME, YarnInstallTask.class);
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
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getCleanScript().set(extension.getCleanScript());
        task.setOnlyIf(t -> extension.getCleanScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_TASK_NAME, InstallTask.class,
            (cleanTask, installTask) -> cleanTask.getCleanScript().isPresent());
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
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getCheckScript().set(extension.getCheckScript());
        task.setOnlyIf(t -> extension.getCheckScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_TASK_NAME, InstallTask.class,
            (checkTask, installTask) -> checkTask.getCheckScript().isPresent());
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
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getAssembleScript().set(extension.getAssembleScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_TASK_NAME, InstallTask.class,
            (assembleTask, installTask) -> assembleTask.getAssembleScript().isPresent());
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
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
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
}
