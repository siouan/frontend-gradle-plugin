package org.siouan.frontendgradleplugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.Directory;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.MalformedPackageManagerSpecification;
import org.siouan.frontendgradleplugin.domain.ParsePackageManagerSpecification;
import org.siouan.frontendgradleplugin.domain.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.ResolveGlobalExecutablePathCommand;
import org.siouan.frontendgradleplugin.domain.ResolveGlobalNodeExecutablePath;
import org.siouan.frontendgradleplugin.domain.SystemSettingsProvider;
import org.siouan.frontendgradleplugin.domain.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.infrastructure.archiver.ArchiverProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.bean.Beans;
import org.siouan.frontendgradleplugin.infrastructure.bean.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.bean.ZeroOrMultiplePublicConstructorsException;
import org.siouan.frontendgradleplugin.infrastructure.gradle.*;
import org.siouan.frontendgradleplugin.infrastructure.httpclient.HttpClientProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.system.ChannelProviderImpl;
import org.siouan.frontendgradleplugin.infrastructure.system.FileManagerImpl;
import org.siouan.frontendgradleplugin.infrastructure.system.PlatformProviderImpl;

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
     * Name of the default directory used to cache common files for multiple tasks.
     */
    public static final String DEFAULT_CACHE_DIRECTORY_NAME = ".frontend-gradle-plugin";

    /**
     * Default port for the proxy server handling HTTP requests.
     */
    public static final int DEFAULT_HTTP_PROXY_PORT = 80;

    /**
     * Default port for the proxy server handling HTTPS requests.
     */
    public static final int DEFAULT_HTTPS_PROXY_PORT = 443;

    /**
     * Name of the npm/pnpm/yarn command that shall be executed to install frontend dependencies.
     */
    public static final String DEFAULT_INSTALL_SCRIPT = "install";

    /**
     * Maximum number of attempts to download a file.
     */
    public static final int DEFAULT_MAX_DOWNLOAD_ATTEMPTS = 1;

    /**
     * Name of the task that installs a Node.js distribution.
     */
    public static final String DEFAULT_NODE_INSTALL_DIRECTORY_NAME = "node";

    /**
     * URL pattern used to download the Node.js distribution.
     */
    public static final String DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN = "vVERSION/node-vVERSION-ARCH.TYPE";

    /**
     * URL pattern used to download the Node.js distribution.
     */
    public static final String DEFAULT_NODE_DISTRIBUTION_URL_ROOT = "https://nodejs.org/dist/";

    /**
     * HTTP statuses that should trigger another download attempt.
     */
    public static final Set<Integer> DEFAULT_RETRY_HTTP_STATUSES = Set.of(408, 429, 500, 502, 503, 504);

    public static final int DEFAULT_RETRY_INITIAL_INTERVAL_MS = 1000;

    public static final double DEFAULT_RETRY_INTERVAL_MULTIPLIER = 2;

    public static final int DEFAULT_RETRY_MAX_INTERVAL_MS = 30000;

    public static final String GRADLE_ASSEMBLE_TASK_NAME = LifecycleBasePlugin.ASSEMBLE_TASK_NAME;

    public static final String GRADLE_CHECK_TASK_NAME = LifecycleBasePlugin.CHECK_TASK_NAME;

    public static final String GRADLE_CLEAN_TASK_NAME = LifecycleBasePlugin.CLEAN_TASK_NAME;

    public static final String GRADLE_PUBLISH_TASK_NAME = PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME;

    /**
     * Name of the task that installs frontend dependencies.
     */
    public static final String INSTALL_FRONTEND_TASK_NAME = "installFrontend";

    /**
     * Name of the task that installs the relevant package manager.
     */
    public static final String INSTALL_PACKAGE_MANAGER_TASK_NAME = "installPackageManager";

    /**
     * Name of the file containing Javascript project metadata.
     */
    public static final String PACKAGE_JSON_FILE_NAME = "package.json";

    /**
     * Name of the task that installs a Node distribution.
     */
    public static final String INSTALL_NODE_TASK_NAME = "installNode";

    /**
     * Name of the environment variable providing the path to a global Node.js installation.
     */
    public static final String NODEJS_HOME_ENV_VAR = "FGP_NODEJS_HOME";

    /**
     * Name of the file containing the name and version of the package manager resolved.
     */
    public static final String PACKAGE_MANAGER_SPECIFICATION_FILE_NAME = "package-manager-specification.txt";

    /**
     * Name of the file containing the path to the package manager executable resolved.
     */
    public static final String PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME = "package-manager-executable-path.txt";

    /**
     * Name of the task that resolves the applicable package manager.
     */
    public static final String RESOLVE_PACKAGE_MANAGER_TASK_NAME = "resolvePackageManager";

    /**
     * Root name of the plugin extension.
     */
    private static final String EXTENSION_NAME = "frontend";

    /**
     * The Gradle group in which all this plugin's tasks will be categorized.
     */
    private static final String TASK_GROUP = "Frontend";

    @Override
    public void apply(final Project project) {
        project.getPluginManager().apply(BasePlugin.class);
        project.getPluginManager().apply(PublishingPlugin.class);

        final SystemExtension systemExtension = new SystemExtension(project.getProviders());
        final FrontendExtension frontendExtension = project
            .getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project.getObjects());

        // Set default configuration.
        frontendExtension.getNodeDistributionProvided().convention(false);
        frontendExtension.getNodeDistributionUrlRoot().convention(DEFAULT_NODE_DISTRIBUTION_URL_ROOT);
        frontendExtension.getNodeDistributionUrlPathPattern().convention(DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN);
        frontendExtension.getInstallScript().convention(DEFAULT_INSTALL_SCRIPT);
        frontendExtension.getPackageJsonDirectory().convention(project.getLayout().getProjectDirectory());
        frontendExtension.getHttpProxyPort().convention(DEFAULT_HTTP_PROXY_PORT);
        frontendExtension.getHttpsProxyPort().convention(DEFAULT_HTTPS_PROXY_PORT);
        frontendExtension.getMaxDownloadAttempts().convention(DEFAULT_MAX_DOWNLOAD_ATTEMPTS);
        frontendExtension.getRetryHttpStatuses().convention(DEFAULT_RETRY_HTTP_STATUSES);
        frontendExtension.getRetryInitialIntervalMs().convention(DEFAULT_RETRY_INITIAL_INTERVAL_MS);
        frontendExtension.getRetryIntervalMultiplier().convention(DEFAULT_RETRY_INTERVAL_MULTIPLIER);
        frontendExtension.getRetryMaxIntervalMs().convention(DEFAULT_RETRY_MAX_INTERVAL_MS);
        frontendExtension
            .getCacheDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_CACHE_DIRECTORY_NAME));
        frontendExtension
            .getInternalPackageJsonFile()
            .fileProvider(
                frontendExtension.getPackageJsonDirectory().file(PACKAGE_JSON_FILE_NAME).map(RegularFile::getAsFile));
        frontendExtension
            .getInternalPackageManagerSpecificationFile()
            .convention(frontendExtension
                .getCacheDirectory()
                .dir(RESOLVE_PACKAGE_MANAGER_TASK_NAME)
                .map(directory -> directory.file(PACKAGE_MANAGER_SPECIFICATION_FILE_NAME)));
        frontendExtension
            .getInternalPackageManagerExecutablePathFile()
            .convention(frontendExtension
                .getCacheDirectory()
                .dir(RESOLVE_PACKAGE_MANAGER_TASK_NAME)
                .map(directory -> directory.file(PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME)));
        frontendExtension.getVerboseModeEnabled().convention(false);

        // Create bean registry and register concrete base implementations.
        final String beanRegistryId = configureBeanRegistry(project, systemExtension, frontendExtension);

        // Create and configure all tasks.
        configureTasks(project, beanRegistryId);
    }

    /**
     * Creates a bean registry and registers concrete implementations for injection.
     *
     * @param project Project.
     * @param systemExtension Extension providing system properties.
     * @param frontendExtension Extension providing frontend properties.
     * @return ID of the bean registry.
     */
    protected String configureBeanRegistry(final Project project, final SystemExtension systemExtension,
        final FrontendExtension frontendExtension) {
        final String beanRegistryId = Beans.getBeanRegistryId(project.getLayout().getProjectDirectory().toString());
        final SystemSettingsProvider systemSettingsProvider = new SystemSettingsProviderImpl(systemExtension,
            DEFAULT_HTTP_PROXY_PORT, DEFAULT_HTTPS_PROXY_PORT);
        final PlatformProvider platformProvider = new PlatformProviderImpl(systemSettingsProvider);
        final GradleSettings gradleSettings = new GradleSettings(project.getLogging().getLevel(),
            project.getGradle().getStartParameter().getLogLevel());
        final ProviderFactory providerFactory = project.getProviders();
        final TaskContext taskContext = TaskContext.builder()
            // This default install directory must not be applied with a convention on the related extension property,
            // because, when null, it allows to know whether the install directory must be resolved from an environment
            // variable.
            .defaultNodeInstallDirectoryPath(project
                .getLayout()
                .getProjectDirectory()
                .getAsFile()
                .toPath()
                .resolve(DEFAULT_NODE_INSTALL_DIRECTORY_NAME))
            .nodeInstallDirectoryFromEnvironment(
                providerFactory.environmentVariable(NODEJS_HOME_ENV_VAR).map(Paths::get))
            .extension(frontendExtension)
            .build();
        Beans.initBeanRegistry(beanRegistryId);
        Beans.registerBean(beanRegistryId, frontendExtension);
        Beans.registerBean(beanRegistryId, systemSettingsProvider);
        Beans.registerBean(beanRegistryId, platformProvider);
        Beans.registerBean(beanRegistryId, gradleSettings);
        Beans.registerBean(beanRegistryId, taskContext);
        Beans.registerBean(beanRegistryId, GradleLoggerAdapter.class);
        Beans.registerBean(beanRegistryId, TaskLoggerConfigurer.class);
        Beans.registerBean(beanRegistryId, ResolveNodeInstallDirectoryPath.class);
        Beans.registerBean(beanRegistryId, FileManagerImpl.class);
        Beans.registerBean(beanRegistryId, ChannelProviderImpl.class);
        Beans.registerBean(beanRegistryId, ArchiverProviderImpl.class);
        Beans.registerBean(beanRegistryId, HttpClientProviderImpl.class);
        return beanRegistryId;
    }

    /**
     * Registers plugin tasks.
     *
     * @param project Project.
     * @param beanRegistryId Bean registry ID.
     */
    protected void configureTasks(final Project project, final String beanRegistryId) {
        final TaskContainer taskContainer = project.getTasks();
        final TaskContext taskContext = getBeanOrFail(beanRegistryId, TaskContext.class);
        taskContainer.register(INSTALL_NODE_TASK_NAME, InstallNodeTask.class,
            task -> configureInstallNodeTask(task, beanRegistryId, taskContext));
        taskContainer.register(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class,
            task -> configureResolvePackageManagerTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class,
            task -> configureInstallPackageManagerTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class,
            task -> configureInstallFrontendTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(CLEAN_TASK_NAME, CleanTask.class,
            task -> configureCleanTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(CHECK_TASK_NAME, CheckTask.class,
            task -> configureCheckTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(ASSEMBLE_TASK_NAME, AssembleTask.class,
            task -> configureAssembleTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(PUBLISH_TASK_NAME, PublishTask.class,
            task -> configurePublishTask(task, beanRegistryId, taskContainer, taskContext));

        // Configure dependencies with Gradle built-in tasks.
        configureDependency(taskContainer, GRADLE_CLEAN_TASK_NAME, CLEAN_TASK_NAME, CleanTask.class);
        configureDependency(taskContainer, GRADLE_ASSEMBLE_TASK_NAME, ASSEMBLE_TASK_NAME, AssembleTask.class);
        configureDependency(taskContainer, GRADLE_CHECK_TASK_NAME, CHECK_TASK_NAME, CheckTask.class);
        configureDependency(taskContainer, GRADLE_PUBLISH_TASK_NAME, PUBLISH_TASK_NAME, PublishTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContext Configuration context.
     */
    protected void configureInstallNodeTask(final InstallNodeTask task, final String beanRegistryId,
        final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Node.js distribution.");
        task.getNodeVersion().set(extension.getNodeVersion());
        task.getNodeDistributionUrlRoot().set(extension.getNodeDistributionUrlRoot());
        task.getNodeDistributionUrlPathPattern().set(extension.getNodeDistributionUrlPathPattern());
        task.getNodeDistributionServerUsername().set(extension.getNodeDistributionServerUsername());
        task.getNodeDistributionServerPassword().set(extension.getNodeDistributionServerPassword());
        task
            .getNodeInstallDirectory()
            .set(extension
                .getNodeInstallDirectory()
                .map(Directory::getAsFile)
                .orElse(taskContext.getDefaultNodeInstallDirectoryPath().toFile()));
        task.getHttpProxyHost().set(extension.getHttpProxyHost());
        task.getHttpProxyPort().set(extension.getHttpProxyPort());
        task.getHttpProxyUsername().set(extension.getHttpProxyUsername());
        task.getHttpProxyPassword().set(extension.getHttpProxyPassword());
        task.getHttpsProxyHost().set(extension.getHttpsProxyHost());
        task.getHttpsProxyPort().set(extension.getHttpsProxyPort());
        task.getHttpsProxyUsername().set(extension.getHttpsProxyUsername());
        task.getHttpsProxyPassword().set(extension.getHttpsProxyPassword());
        task
            .getNodeExecutableFile()
            .fileProvider(extension
                .getNodeInstallDirectory()
                .map(directory -> directory.getAsFile().toPath())
                .orElse(taskContext.getDefaultNodeInstallDirectoryPath())
                .map(nodeInstallDirectoryPath -> getBeanOrFail(beanRegistryId, ResolveGlobalNodeExecutablePath.class)
                    .execute(ResolveGlobalExecutablePathCommand
                        .builder()
                        .nodeInstallDirectoryPath(nodeInstallDirectoryPath)
                        .platform(getBeanOrFail(beanRegistryId, PlatformProvider.class).getPlatform())
                        .build())
                    .toFile()));
        task.getMaxDownloadAttempts().set(extension.getMaxDownloadAttempts());
        task.getRetryHttpStatuses().set(extension.getRetryHttpStatuses());
        task.getRetryInitialIntervalMs().set(extension.getRetryInitialIntervalMs());
        task.getRetryIntervalMultiplier().set(extension.getRetryIntervalMultiplier());
        task.getRetryMaxIntervalMs().set(extension.getRetryMaxIntervalMs());
        task.setOnlyIf(t -> !extension.getNodeDistributionProvided().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Gradle task container.
     * @param taskContext Configuration context.
     */
    protected void configureResolvePackageManagerTask(final ResolvePackageManagerTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Resolves the package manager.");
        task.getPackageJsonFile().set(extension.getInternalPackageJsonFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getPackageManagerSpecificationFile().set(extension.getInternalPackageManagerSpecificationFile());
        task.getPackageManagerExecutablePathFile().set(extension.getInternalPackageManagerExecutablePathFile());
        // The task is skipped when there's no package.json file. It allows to define a project that installs only a
        // Node.js distribution.
        task.setOnlyIf(t -> extension
            .getInternalPackageJsonFile()
            .getAsFile()
            .map(File::toPath)
            .map(Files::exists)
            .getOrElse(false));
        configureDependency(taskContainer, task, INSTALL_NODE_TASK_NAME, InstallNodeTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param taskContext Configuration context.
     */
    protected void configureInstallPackageManagerTask(final InstallPackageManagerTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Installs the package manager.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        final Provider<ResolvePackageManagerTask> resolvePackageManagerTaskProvider = taskContainer.named(
            RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        task
            .getPackageManagerSpecificationFile()
            .set(resolvePackageManagerTaskProvider.flatMap(
                ResolvePackageManagerTask::getPackageManagerSpecificationFile));
        task
            .getPackageManagerExecutableFile()
            .set(resolvePackageManagerTaskProvider
                .flatMap(ResolvePackageManagerTask::getPackageManagerExecutablePathFile)
                .map(f -> {
                    if (!Files.exists(f.getAsFile().toPath())) {
                        return null;
                    }
                    try {
                        return getBeanOrFail(beanRegistryId, FileManager.class).readString(f.getAsFile().toPath(),
                            StandardCharsets.UTF_8);
                    } catch (final IOException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                })
                .map(packageManagerExecutablePathFilePath -> () -> Paths
                    .get(packageManagerExecutablePathFilePath)
                    .toFile()));
        task.setOnlyIf(t -> extension
            .getInternalPackageManagerExecutablePathFile()
            .getAsFile()
            .map(File::toPath)
            .map(Files::exists)
            .getOrElse(false));
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param taskContext Configuration context.
     */
    protected void configureInstallFrontendTask(final InstallFrontendTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Installs frontend dependencies.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getInstallScript().set(extension.getInstallScript());
        task.setOnlyIf(t -> extension
            .getInternalPackageManagerSpecificationFile()
            .getAsFile()
            .map(File::toPath)
            .map(Files::exists)
            .getOrElse(false));
        configureDependency(taskContainer, task, INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param taskContext Configuration context.
     */
    protected void configureCleanTask(final CleanTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getCleanScript().set(extension.getCleanScript());
        task.setOnlyIf(t -> extension.getCleanScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param taskContext Configuration context.
     */
    protected void configureCheckTask(final CheckTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getCheckScript().set(extension.getCheckScript());
        task.setOnlyIf(t -> extension.getCheckScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param taskContext Configuration context.
     */
    protected void configureAssembleTask(final AssembleTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getAssembleScript().set(extension.getAssembleScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param taskContext Configuration context.
     */
    protected void configurePublishTask(final PublishTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Publishes frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getPublishScript().set(extension.getPublishScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent() && extension.getPublishScript().isPresent());
        configureDependency(taskContainer, task, ASSEMBLE_TASK_NAME, AssembleTask.class);
    }

    private Provider<ExecutableType> getExecutableType(final TaskContainer taskContainer, final String beanRegistryId) {
        return taskContainer
            .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
            .flatMap(ResolvePackageManagerTask::getPackageManagerSpecificationFile)
            .map(RegularFile::getAsFile)
            .map(f -> {
                try {
                    return getBeanOrFail(beanRegistryId, ParsePackageManagerSpecification.class)
                        .execute(getBeanOrFail(beanRegistryId, FileManager.class).readString(f.toPath(),
                            StandardCharsets.UTF_8))
                        .getType()
                        .getExecutableType();
                } catch (final IOException | MalformedPackageManagerSpecification |
                    UnsupportedPackageManagerException e) {
                    throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                }
            });
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
        task.dependsOn(taskContainer.named(dependsOnTaskName, dependsOnTaskClass).getName());
    }

    private Provider<File> resolveNodeInstallDirectory(final String beanRegistryId, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();
        final ResolveNodeInstallDirectoryPath resolveNodeInstallDirectoryPath;
        try {
            resolveNodeInstallDirectoryPath = Beans.getBean(beanRegistryId, ResolveNodeInstallDirectoryPath.class);
        } catch (final BeanInstanciationException | TooManyCandidateBeansException |
            ZeroOrMultiplePublicConstructorsException e) {
            throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
        }

        return resolveNodeInstallDirectoryPath
            .execute(ResolveNodeInstallDirectoryPathCommand
                .builder()
                .nodeInstallDirectoryFromUser(extension.getNodeInstallDirectory().getAsFile().map(File::toPath))
                .nodeDistributionProvided(extension.getNodeDistributionProvided())
                .nodeInstallDirectoryFromEnvironment(taskContext.getNodeInstallDirectoryFromEnvironment())
                .defaultPath(taskContext.getDefaultNodeInstallDirectoryPath())
                .build())
            .map(Path::toFile);
    }

    /**
     * Returns a bean from a registry of throws an error if no bean is registered or is instanciable.
     *
     * @param beanRegistryId ID of the bean registry.
     * @param beanClass Class of the bean to be found/instanciated.
     * @param <T> Type of bean.
     * @return The bean.
     */
    private <T> T getBeanOrFail(final String beanRegistryId, final Class<T> beanClass) {
        try {
            return Beans.getBean(beanRegistryId, beanClass);
        } catch (final BeanInstanciationException | TooManyCandidateBeansException |
            ZeroOrMultiplePublicConstructorsException e) {
            throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }
}
