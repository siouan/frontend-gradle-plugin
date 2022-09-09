package org.siouan.frontendgradleplugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Nonnull;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.siouan.frontendgradleplugin.domain.model.PackageManagerType;
import org.siouan.frontendgradleplugin.domain.model.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.usecase.GetNodeExecutablePath;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;
import org.siouan.frontendgradleplugin.infrastructure.gradle.*;
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
     * Name of the default directory used to cache common files for multiple tasks.
     */
    public static final String DEFAULT_CACHE_DIRECTORY_NAME = "frontend-gradle-plugin";

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
    public static final String METADATA_FILE_NAME = "package.json";

    /**
     * Name of the task that installs a Node distribution.
     */
    public static final String NODE_INSTALL_TASK_NAME = "installNode";

    /**
     * Name of the environment variable providing the path to a global Node.js installation.
     */
    public static final String NODEJS_HOME_ENV_VAR = "FGP_NODEJS_HOME";

    /**
     * Name of the file containing the name of the package manager resolved.
     */
    public static final String PACKAGE_MANAGER_NAME_FILE_NAME = "package-manager-name.txt";

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

    public void apply(final Project project) {
        project.getPluginManager().apply(BasePlugin.class);
        project.getPluginManager().apply(PublishingPlugin.class);

        final SystemExtension systemExtension = new SystemExtension(project.getProviders());

        final FrontendExtension extension = project
            .getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project.getObjects());

        extension.getNodeDistributionProvided().convention(false);
        extension.getNodeDistributionUrlRoot().convention(DEFAULT_NODE_DISTRIBUTION_URL_ROOT);
        extension.getNodeDistributionUrlPathPattern().convention(DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN);
        extension.getInstallScript().convention(DEFAULT_INSTALL_SCRIPT);
        extension.getPackageJsonDirectory().convention(project.getLayout().getProjectDirectory());
        extension.getHttpProxyPort().convention(DEFAULT_HTTP_PROXY_PORT);
        extension.getHttpsProxyPort().convention(DEFAULT_HTTPS_PROXY_PORT);
        extension
            .getCacheDirectory()
            .convention(project.getLayout().getBuildDirectory().dir(DEFAULT_CACHE_DIRECTORY_NAME));
        extension
            .getInternalMetadataFile()
            .fileProvider(extension.getPackageJsonDirectory().file(METADATA_FILE_NAME).map(RegularFile::getAsFile));
        extension
            .getInternalPackageManagerNameFile()
            .convention(extension
                .getCacheDirectory()
                .dir(RESOLVE_PACKAGE_MANAGER_TASK_NAME)
                .map(directory -> directory.file(PACKAGE_MANAGER_NAME_FILE_NAME)));
        extension
            .getInternalPackageManagerExecutablePathFile()
            .convention(extension
                .getCacheDirectory()
                .dir(RESOLVE_PACKAGE_MANAGER_TASK_NAME)
                .map(directory -> directory.file(PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME)));
        extension.getVerboseModeEnabled().convention(false);

        final String beanRegistryId = Beans.getBeanRegistryId(project.getLayout().getProjectDirectory().toString());
        final SystemSettingsProvider systemSettingsProvider = new SystemSettingsProviderImpl(systemExtension,
            DEFAULT_HTTP_PROXY_PORT, DEFAULT_HTTPS_PROXY_PORT);
        final PlatformProvider platformProvider = new PlatformProviderImpl(systemSettingsProvider);
        final GradleSettings gradleSettings = new GradleSettings(project.getLogging().getLevel(),
            project.getGradle().getStartParameter().getLogLevel());
        final ProviderFactory providerFactory = project.getProviders();
        final TaskContext taskContext = new TaskContext(
            project.getLayout().getProjectDirectory().getAsFile().toPath().resolve(DEFAULT_NODE_INSTALL_DIRECTORY_NAME),
            providerFactory.environmentVariable(NODEJS_HOME_ENV_VAR).map(Paths::get), extension);
        Beans.initBeanRegistry(beanRegistryId);
        Beans.registerBean(beanRegistryId, extension);
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

        final TaskContainer taskContainer = project.getTasks();
        taskContainer.register(NODE_INSTALL_TASK_NAME, NodeInstallTask.class,
            task -> configureNodeInstallTask(task, beanRegistryId, taskContext));
        taskContainer.register(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class,
            task -> configureResolvePackageManagerTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class,
            task -> configureInstallPackageManagerTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class,
            task -> configureInstallTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(CLEAN_TASK_NAME, CleanTask.class,
            task -> configureCleanTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(CHECK_TASK_NAME, CheckTask.class,
            task -> configureCheckTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(ASSEMBLE_TASK_NAME, AssembleTask.class,
            task -> configureAssembleTask(task, beanRegistryId, taskContainer, taskContext));
        taskContainer.register(PUBLISH_TASK_NAME, PublishTask.class,
            task -> configurePublishTask(task, beanRegistryId, taskContainer, taskContext));

        configureDependency(taskContainer, GRADLE_CLEAN_TASK_NAME, CLEAN_TASK_NAME, CleanTask.class);
        configureDependency(taskContainer, GRADLE_ASSEMBLE_TASK_NAME, ASSEMBLE_TASK_NAME, AssembleTask.class);
        configureDependency(taskContainer, GRADLE_CHECK_TASK_NAME, CHECK_TASK_NAME, CheckTask.class);
        configureDependency(taskContainer, GRADLE_PUBLISH_TASK_NAME, PUBLISH_TASK_NAME, PublishTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureNodeInstallTask(final NodeInstallTask task, final String beanRegistryId,
        final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Node distribution.");
        task.getNodeVersion().set(extension.getNodeVersion());
        task.getNodeDistributionUrlRoot().set(extension.getNodeDistributionUrlRoot());
        task.getNodeDistributionUrlPathPattern().set(extension.getNodeDistributionUrlPathPattern());
        task.getNodeDistributionServerUsername().set(extension.getNodeDistributionServerUsername());
        task.getNodeDistributionServerPassword().set(extension.getNodeDistributionServerPassword());
        task
            .getNodeInstallDirectory()
            .set(extension
                .getNodeInstallDirectory()
                .map(directory -> directory.getAsFile().getAbsolutePath())
                .orElse(taskContext.getDefaultNodeInstallDirectoryPath().toString()));
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
                .map(nodeInstallDirectoryPath -> {
                    try {
                        return Beans
                            .getBean(beanRegistryId, GetNodeExecutablePath.class)
                            .execute(nodeInstallDirectoryPath,
                                Beans.getBean(beanRegistryId, PlatformProvider.class).getPlatform())
                            .toFile();
                    } catch (final BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                }));
        task.setOnlyIf(t -> !extension.getNodeDistributionProvided().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureResolvePackageManagerTask(final ResolvePackageManagerTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Resolves the package manager.");
        task.getMetadataFile().set(extension.getInternalMetadataFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getPackageManagerNameFile().set(extension.getInternalPackageManagerNameFile());
        task.getPackageManagerExecutablePathFile().set(extension.getInternalPackageManagerExecutablePathFile());
        task.setOnlyIf(t -> extension.getInternalMetadataFile().getAsFile().map(File::toPath).map(Files::exists).getOrElse(false));
        configureDependency(taskContainer, task, NODE_INSTALL_TASK_NAME, NodeInstallTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureInstallPackageManagerTask(final InstallPackageManagerTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Installs the package manager.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task.getPackageManagerNameFile().set(extension.getInternalPackageManagerNameFile());
        task
            .getPackageManagerExecutableFile()
            .set(taskContainer
                .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
                .flatMap(ResolvePackageManagerTask::getPackageManagerExecutablePathFile)
                .map(f -> {
                    if (!Files.exists(f.getAsFile().toPath())) {
                        return null;
                    }
                    try {
                        return Beans
                            .getBean(beanRegistryId, FileManager.class)
                            .readString(f.getAsFile().toPath(), StandardCharsets.UTF_8);
                    } catch (final IOException | BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                })
                .map(packageManagerExecutablePathFilePath -> () -> Paths
                    .get(packageManagerExecutablePathFilePath)
                    .toFile()));
        task.setOnlyIf(t -> extension.getInternalPackageManagerExecutablePathFile().getAsFile().map(File::toPath).map(Files::exists).getOrElse(false));
        configureDependency(taskContainer, task, NODE_INSTALL_TASK_NAME, NodeInstallTask.class);
        configureDependency(taskContainer, task, RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureInstallTask(final InstallDependenciesTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Installs frontend dependencies.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task
            .getExecutableType()
            .set(taskContainer
                .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
                .flatMap(ResolvePackageManagerTask::getPackageManagerNameFile)
                .map(RegularFile::getAsFile)
                .map(f -> {
                    try {
                        return PackageManagerType
                            .fromPackageName(Beans
                                .getBean(beanRegistryId, FileManager.class)
                                .readString(f.toPath(), StandardCharsets.UTF_8))
                            .map(PackageManagerType::getExecutableType)
                            .orElseThrow();
                    } catch (final IOException | BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                }));
        task.getInstallScript().set(extension.getInstallScript());
        task.setOnlyIf(t -> extension.getInternalPackageManagerNameFile().getAsFile().map(File::toPath).map(Files::exists).getOrElse(false));
        configureDependency(taskContainer, task, RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        configureDependency(taskContainer, task, INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureCleanTask(final CleanTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task
            .getExecutableType()
            .set(taskContainer
                .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
                .flatMap(ResolvePackageManagerTask::getPackageManagerNameFile)
                .map(RegularFile::getAsFile)
                .map(f -> {
                    try {
                        return PackageManagerType
                            .fromPackageName(Beans
                                .getBean(beanRegistryId, FileManager.class)
                                .readString(f.toPath(), StandardCharsets.UTF_8))
                            .map(PackageManagerType::getExecutableType)
                            .orElseThrow();
                    } catch (final IOException | BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                }));
        task.getCleanScript().set(extension.getCleanScript());
        task.setOnlyIf(t -> extension.getCleanScript().isPresent());
        configureDependency(taskContainer, task, RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureCheckTask(final CheckTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task
            .getExecutableType()
            .set(taskContainer
                .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
                .flatMap(ResolvePackageManagerTask::getPackageManagerNameFile)
                .map(RegularFile::getAsFile)
                .map(f -> {
                    try {
                        return PackageManagerType
                            .fromPackageName(Beans
                                .getBean(beanRegistryId, FileManager.class)
                                .readString(f.toPath(), StandardCharsets.UTF_8))
                            .map(PackageManagerType::getExecutableType)
                            .orElseThrow();
                    } catch (final IOException | BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                }));
        task.getCheckScript().set(extension.getCheckScript());
        task.setOnlyIf(t -> extension.getCheckScript().isPresent());
        configureDependency(taskContainer, task, RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configureAssembleTask(final AssembleTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task
            .getExecutableType()
            .set(taskContainer
                .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
                .flatMap(ResolvePackageManagerTask::getPackageManagerNameFile)
                .map(RegularFile::getAsFile)
                .map(f -> {
                    try {
                        return PackageManagerType
                            .fromPackageName(Beans
                                .getBean(beanRegistryId, FileManager.class)
                                .readString(f.toPath(), StandardCharsets.UTF_8))
                            .map(PackageManagerType::getExecutableType)
                            .orElseThrow();
                    } catch (final IOException | BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                }));
        task.getAssembleScript().set(extension.getAssembleScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent());
        configureDependency(taskContainer, task, RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        configureDependency(taskContainer, task, INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class);
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param taskContext Configuration context.
     */
    private void configurePublishTask(final PublishTask task, final String beanRegistryId,
        final TaskContainer taskContainer, final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();

        task.setGroup(TASK_GROUP);
        task.setDescription("Publishes frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory().getAsFile());
        task.getNodeInstallDirectory().set(resolveNodeInstallDirectory(beanRegistryId, taskContext));
        task
            .getExecutableType()
            .set(taskContainer
                .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
                .flatMap(ResolvePackageManagerTask::getPackageManagerNameFile)
                .map(RegularFile::getAsFile)
                .map(f -> {
                    try {
                        return PackageManagerType
                            .fromPackageName(Beans
                                .getBean(beanRegistryId, FileManager.class)
                                .readString(f.toPath(), StandardCharsets.UTF_8))
                            .map(PackageManagerType::getExecutableType)
                            .orElseThrow();
                    } catch (final IOException | BeanInstanciationException | TooManyCandidateBeansException |
                        ZeroOrMultiplePublicConstructorsException e) {
                        throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
                    }
                }));
        task.getPublishScript().set(extension.getPublishScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent() && extension.getPublishScript().isPresent());
        configureDependency(taskContainer, task, RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class);
        configureDependency(taskContainer, task, ASSEMBLE_TASK_NAME, AssembleTask.class);
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

    @Nonnull
    private Provider<String> resolveNodeInstallDirectory(@Nonnull final String beanRegistryId,
        @Nonnull final TaskContext taskContext) {
        final FrontendExtension extension = taskContext.getExtension();
        final ResolveNodeInstallDirectoryPath resolveNodeInstallDirectoryPath;
        try {
            resolveNodeInstallDirectoryPath = Beans.getBean(beanRegistryId, ResolveNodeInstallDirectoryPath.class);
        } catch (final BeanInstanciationException | TooManyCandidateBeansException |
            ZeroOrMultiplePublicConstructorsException e) {
            throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
        }

        return extension
            .getNodeDistributionProvided()
            .flatMap(nodeDistributionProvided -> resolveNodeInstallDirectoryPath.execute(nodeDistributionProvided,
                extension.getNodeInstallDirectory().getAsFile().map(File::toPath),
                taskContext.getDefaultNodeInstallDirectoryPath(), taskContext.getNodeInstallDirectoryFromEnvironment()))
            .map(Path::toString);
    }
}
