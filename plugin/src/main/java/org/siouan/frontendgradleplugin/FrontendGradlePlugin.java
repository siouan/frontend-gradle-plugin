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
import org.gradle.api.tasks.TaskState;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.MalformedPackageManagerSpecification;
import org.siouan.frontendgradleplugin.domain.ParsePackageManagerSpecification;
import org.siouan.frontendgradleplugin.domain.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.ResolveExecutablePathCommand;
import org.siouan.frontendgradleplugin.domain.ResolveNodeExecutablePath;
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
        frontendExtension
            .getNodeInstallDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_NODE_INSTALL_DIRECTORY_NAME));
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
        Beans.initBeanRegistry(beanRegistryId);
        Beans.registerBean(beanRegistryId, frontendExtension);
        Beans.registerBean(beanRegistryId, systemSettingsProvider);
        Beans.registerBean(beanRegistryId, platformProvider);
        Beans.registerBean(beanRegistryId, gradleSettings);
        Beans.registerBean(beanRegistryId, GradleLoggerAdapter.class);
        Beans.registerBean(beanRegistryId, TaskLoggerConfigurer.class);
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
        final FrontendExtension frontendExtension = getBeanOrFail(beanRegistryId, FrontendExtension.class);
        taskContainer.register(INSTALL_NODE_TASK_NAME, InstallNodeTask.class,
            task -> configureInstallNodeTask(task, beanRegistryId, frontendExtension));
        taskContainer.register(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class,
            task -> configureResolvePackageManagerTask(task, taskContainer, frontendExtension));
        taskContainer.register(INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class,
            task -> configureInstallPackageManagerTask(task, beanRegistryId, taskContainer, frontendExtension));
        taskContainer.register(INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class,
            task -> configureInstallFrontendTask(task, beanRegistryId, taskContainer, frontendExtension));
        taskContainer.register(CLEAN_TASK_NAME, CleanTask.class,
            task -> configureCleanTask(task, beanRegistryId, taskContainer, frontendExtension));
        taskContainer.register(CHECK_TASK_NAME, CheckTask.class,
            task -> configureCheckTask(task, beanRegistryId, taskContainer, frontendExtension));
        taskContainer.register(ASSEMBLE_TASK_NAME, AssembleTask.class,
            task -> configureAssembleTask(task, beanRegistryId, taskContainer, frontendExtension));
        taskContainer.register(PUBLISH_TASK_NAME, PublishTask.class,
            task -> configurePublishTask(task, beanRegistryId, taskContainer, frontendExtension));

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
     * @param frontendExtension Plugin extension.
     */
    protected void configureInstallNodeTask(final InstallNodeTask task, final String beanRegistryId,
        final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Node.js distribution.");
        task.getNodeVersion().set(frontendExtension.getNodeVersion());
        task.getNodeDistributionUrlRoot().set(frontendExtension.getNodeDistributionUrlRoot());
        task.getNodeDistributionUrlPathPattern().set(frontendExtension.getNodeDistributionUrlPathPattern());
        task.getNodeDistributionServerUsername().set(frontendExtension.getNodeDistributionServerUsername());
        task.getNodeDistributionServerPassword().set(frontendExtension.getNodeDistributionServerPassword());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getHttpProxyHost().set(frontendExtension.getHttpProxyHost());
        task.getHttpProxyPort().set(frontendExtension.getHttpProxyPort());
        task.getHttpProxyUsername().set(frontendExtension.getHttpProxyUsername());
        task.getHttpProxyPassword().set(frontendExtension.getHttpProxyPassword());
        task.getHttpsProxyHost().set(frontendExtension.getHttpsProxyHost());
        task.getHttpsProxyPort().set(frontendExtension.getHttpsProxyPort());
        task.getHttpsProxyUsername().set(frontendExtension.getHttpsProxyUsername());
        task.getHttpsProxyPassword().set(frontendExtension.getHttpsProxyPassword());
        task
            .getNodeExecutableFile()
            .fileProvider(frontendExtension
                .getNodeInstallDirectory()
                .map(directory -> getBeanOrFail(beanRegistryId, ResolveNodeExecutablePath.class)
                    .execute(ResolveExecutablePathCommand
                        .builder()
                        .nodeInstallDirectoryPath(directory.getAsFile().toPath())
                        .platform(getBeanOrFail(beanRegistryId, PlatformProvider.class).getPlatform())
                        .build())
                    .toFile()));
        task.getMaxDownloadAttempts().set(frontendExtension.getMaxDownloadAttempts());
        task.getRetryHttpStatuses().set(frontendExtension.getRetryHttpStatuses());
        task.getRetryInitialIntervalMs().set(frontendExtension.getRetryInitialIntervalMs());
        task.getRetryIntervalMultiplier().set(frontendExtension.getRetryIntervalMultiplier());
        task.getRetryMaxIntervalMs().set(frontendExtension.getRetryMaxIntervalMs());
        task.setOnlyIf(t -> !frontendExtension.getNodeDistributionProvided().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContainer Gradle task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configureResolvePackageManagerTask(final ResolvePackageManagerTask task,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Resolves the package manager.");
        final Provider<File> packageJsonFileProvider = frontendExtension
            .getPackageJsonDirectory()
            .file(PACKAGE_JSON_FILE_NAME)
            .map(RegularFile::getAsFile);
        task.getPackageJsonFile().fileProvider(packageJsonFileProvider);
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getPackageManagerSpecificationFile().set(frontendExtension.getInternalPackageManagerSpecificationFile());
        task.getPackageManagerExecutablePathFile().set(frontendExtension.getInternalPackageManagerExecutablePathFile());
        // The task is skipped when there's no package.json file. It allows to define a project that installs only a
        // Node.js distribution.
        task.setOnlyIf(t -> packageJsonFileProvider.map(File::toPath).map(Files::isRegularFile).getOrElse(false));

        configureDependency(taskContainer, task, INSTALL_NODE_TASK_NAME, InstallNodeTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configureInstallPackageManagerTask(final InstallPackageManagerTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs the package manager.");
        task.getPackageJsonDirectory().set(frontendExtension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().map(nodeInstallDirectory -> {
            final Path nodeInstallDirectoryPath = nodeInstallDirectory.getAsFile().toPath();
            if (!Files.isDirectory(nodeInstallDirectoryPath)) {
                // Performing this verification ahead of time avoids automatic creation of any parent directory by
                // Gradle if the task is not skipped. This may be the case if the Node.js distribution is provided, but
                // the install directory is not correctly configured and points to nowhere.
                throw new GradleException("Node.js install directory not found: " + nodeInstallDirectoryPath);
            }
            return nodeInstallDirectory.getAsFile();
        }));
        final Provider<ResolvePackageManagerTask> resolvePackageManagerTaskProvider = taskContainer.named(
            RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        task
            .getPackageManagerSpecificationFile()
            .set(resolvePackageManagerTaskProvider.flatMap(
                ResolvePackageManagerTask::getPackageManagerSpecificationFile));
        task
            .getPackageManagerExecutableFile()
            .fileProvider(resolvePackageManagerTaskProvider
                .flatMap(ResolvePackageManagerTask::getPackageManagerExecutablePathFile)
                .map(f -> {
                    final Path filePath = f.getAsFile().toPath();
                    if (!Files.exists(filePath)) {
                        // Setting the output property to null avoids automatic creation of any parent directory by
                        // Gradle if the task is not skipped. If it is skipped, the file system would not have been
                        // touched by Gradle.
                        return null;
                    }
                    try {
                        return Paths
                            .get(getBeanOrFail(beanRegistryId, FileManager.class).readString(filePath,
                                StandardCharsets.UTF_8))
                            .toFile();
                    } catch (final IOException e) {
                        throw new GradleException(
                            "Cannot read path to package manager executable from file: " + filePath, e);
                    }
                }));
        task.setOnlyIf(t -> isTaskExecuted(taskContainer, RESOLVE_PACKAGE_MANAGER_TASK_NAME));
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configureInstallFrontendTask(final InstallFrontendTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs frontend dependencies.");
        task.getPackageJsonDirectory().set(frontendExtension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getInstallScript().set(frontendExtension.getInstallScript());
        task.setOnlyIf(t -> isTaskExecuted(taskContainer, INSTALL_PACKAGE_MANAGER_TASK_NAME));
        configureDependency(taskContainer, task, INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configureCleanTask(final CleanTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getPackageJsonDirectory().set(frontendExtension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getCleanScript().set(frontendExtension.getCleanScript());
        task.setOnlyIf(t -> isTaskExecuted(taskContainer, INSTALL_FRONTEND_TASK_NAME) && frontendExtension
            .getCleanScript()
            .isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configureCheckTask(final CheckTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getPackageJsonDirectory().set(frontendExtension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getCheckScript().set(frontendExtension.getCheckScript());
        task.setOnlyIf(t -> isTaskExecuted(taskContainer, INSTALL_FRONTEND_TASK_NAME) && frontendExtension
            .getCheckScript()
            .isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configureAssembleTask(final AssembleTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(frontendExtension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getAssembleScript().set(frontendExtension.getAssembleScript());
        task.setOnlyIf(t -> isTaskExecuted(taskContainer, INSTALL_FRONTEND_TASK_NAME) && frontendExtension
            .getAssembleScript()
            .isPresent());
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param beanRegistryId Bean registry ID.
     * @param taskContainer Task container.
     * @param frontendExtension Plugin extension.
     */
    protected void configurePublishTask(final PublishTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final FrontendExtension frontendExtension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Publishes frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(frontendExtension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(frontendExtension.getNodeInstallDirectory().getAsFile());
        task.getExecutableType().set(getExecutableType(taskContainer, beanRegistryId));
        task.getPublishScript().set(frontendExtension.getPublishScript());
        task.setOnlyIf(t -> isTaskExecuted(taskContainer, INSTALL_FRONTEND_TASK_NAME) && frontendExtension
            .getAssembleScript()
            .isPresent() && frontendExtension.getPublishScript().isPresent());
        configureDependency(taskContainer, task, ASSEMBLE_TASK_NAME, AssembleTask.class);
    }

    private Provider<ExecutableType> getExecutableType(final TaskContainer taskContainer, final String beanRegistryId) {
        return taskContainer
            .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
            .flatMap(ResolvePackageManagerTask::getPackageManagerSpecificationFile)
            .map(f -> {
                final Path packageManagerSpecificationFilePath = f.getAsFile().toPath();
                try {
                    return getBeanOrFail(beanRegistryId, ParsePackageManagerSpecification.class)
                        .execute(getBeanOrFail(beanRegistryId, FileManager.class).readString(
                            packageManagerSpecificationFilePath, StandardCharsets.UTF_8))
                        .getType()
                        .getExecutableType();
                } catch (final IOException | MalformedPackageManagerSpecification |
                    UnsupportedPackageManagerException e) {
                    throw new GradleException(
                        "Cannot read package manager specification from file: " + packageManagerSpecificationFilePath,
                        e);
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

    /**
     * Tells whether a task has been executed in the current build.
     *
     * @param taskContainer Task container.
     * @param taskName Task name.
     * @return {@code true} if the task was executed (i.e. not skipped or up-to-date).
     */
    private boolean isTaskExecuted(final TaskContainer taskContainer, final String taskName) {
        return taskContainer.named(taskName).map(task -> {
            final TaskState taskState = task.getState();
            return taskState.getUpToDate() || !taskState.getSkipped();
        }).getOrElse(false);
    }
}
