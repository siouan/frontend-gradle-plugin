package org.siouan.frontendgradleplugin;

import java.util.function.BiPredicate;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UnknownTaskException;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.siouan.frontendgradleplugin.tasks.AssembleTask;
import org.siouan.frontendgradleplugin.tasks.CheckTask;
import org.siouan.frontendgradleplugin.tasks.CleanTask;
import org.siouan.frontendgradleplugin.tasks.InstallTask;
import org.siouan.frontendgradleplugin.tasks.NodeInstallTask;
import org.siouan.frontendgradleplugin.tasks.PublishTask;
import org.siouan.frontendgradleplugin.tasks.YarnInstallTask;

/**
 * Main plugin class that bootstraps the plugin by declaring its DSL and its tasks.
 * <ul>
 * <li>The plugin applies the Gradle Base plugin, to attach its tasks to the Gradle lifecyle task.</li>
 * <li>Tasks are registered lazily thanks to the use of the configuration avoidance API.</li>
 * <li>Task properties are mapped the plugin extension (DSL) using the lazy configuration API, allowing there
 * calculation is delayed until it is required.</li>
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

    public static final String GRADLE_ASSEMBLE_TASK_NAME = "assemble";

    public static final String GRADLE_CHECK_TASK_NAME = "check";

    public static final String GRADLE_CLEAN_TASK_NAME = "clean";

    public static final String MAVEN_GENERAL_PUBLISH_TASK_NAME = "publish";

    /**
     * Name of the NPM/Yarn command that shall be executed to install frontend dependencies.
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

        final FrontendExtension extension = project
            .getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project);
        extension.getPackageJsonDirectory().convention(project.getLayout().getProjectDirectory().getAsFile());
        extension.getLoggingLevel().convention(LogLevel.LIFECYCLE);
        extension
            .getNodeInstallDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_NODE_INSTALL_DIRNAME));
        extension.getYarnEnabled().convention(false);
        extension
            .getYarnInstallDirectory()
            .convention(project.getLayout().getProjectDirectory().dir(DEFAULT_YARN_INSTALL_DIRNAME));
        extension.getInstallScript().convention(DEFAULT_INSTALL_SCRIPT);

        final TaskContainer projectTasks = project.getTasks();
        projectTasks.register(NODE_INSTALL_TASK_NAME, NodeInstallTask.class,
            task -> configureNodeInstallTask(task, extension));
        projectTasks.register(YARN_INSTALL_TASK_NAME, YarnInstallTask.class,
            task -> configureYarnInstallTask(task, extension));
        projectTasks.register(INSTALL_TASK_NAME, InstallTask.class, task -> configureInstallTask(task, extension));
        projectTasks.register(CLEAN_TASK_NAME, CleanTask.class, task -> configureCleanTask(task, extension));
        projectTasks.register(CHECK_TASK_NAME, CheckTask.class, task -> configureCheckTask(task, extension));
        projectTasks.register(ASSEMBLE_TASK_NAME, AssembleTask.class, task -> configureAssembleTask(task, extension));
        projectTasks.register(PUBLISH_TASK_NAME, PublishTask.class, task -> configurePublishTask(task, extension));

        configureDependency(projectTasks, INSTALL_TASK_NAME, InstallTask.class, NODE_INSTALL_TASK_NAME,
            NodeInstallTask.class);
        configureDependency(projectTasks, INSTALL_TASK_NAME, InstallTask.class, YARN_INSTALL_TASK_NAME,
            YarnInstallTask.class);
        configureDependency(projectTasks, CLEAN_TASK_NAME, CleanTask.class, INSTALL_TASK_NAME, InstallTask.class,
            (cleanTask, installTask) -> cleanTask.getCleanScript().isPresent());
        configureDependency(projectTasks, ASSEMBLE_TASK_NAME, AssembleTask.class, INSTALL_TASK_NAME, InstallTask.class,
            (assembleTask, installTask) -> assembleTask.getAssembleScript().isPresent());
        configureDependency(projectTasks, CHECK_TASK_NAME, CheckTask.class, INSTALL_TASK_NAME, InstallTask.class,
            (checkTask, installTask) -> checkTask.getCheckScript().isPresent());
        configureDependency(projectTasks, PUBLISH_TASK_NAME, PublishTask.class, ASSEMBLE_TASK_NAME, AssembleTask.class,
            (publishTask, assembleTask) -> publishTask.getPublishScript().isPresent());
        configureDependency(projectTasks, GRADLE_CLEAN_TASK_NAME, Task.class, CLEAN_TASK_NAME, CleanTask.class);
        configureDependency(projectTasks, GRADLE_ASSEMBLE_TASK_NAME, Task.class, ASSEMBLE_TASK_NAME,
            AssembleTask.class);
        configureDependency(projectTasks, GRADLE_CHECK_TASK_NAME, Task.class, CHECK_TASK_NAME, CheckTask.class);
        configureDependencyIfPresent(project, projectTasks, MAVEN_GENERAL_PUBLISH_TASK_NAME, Task.class,
            PUBLISH_TASK_NAME, PublishTask.class);
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
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getNodeVersion().set(extension.getNodeVersion());
        task.getNodeDistributionUrl().set(extension.getNodeDistributionUrl());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
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
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getYarnVersion().set(extension.getYarnVersion());
        task.getYarnDistributionUrl().set(extension.getYarnDistributionUrl());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.setOnlyIf(t -> extension.getYarnEnabled().get());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureInstallTask(final InstallTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs/updates frontend dependencies.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getInstallScript().set(extension.getInstallScript());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureCleanTask(final CleanTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getCleanScript().set(extension.getCleanScript());
        task.setOnlyIf(t -> extension.getCleanScript().isPresent());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureCheckTask(final CheckTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getCheckScript().set(extension.getCheckScript());
        task.setOnlyIf(t -> extension.getCheckScript().isPresent());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureAssembleTask(final AssembleTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getAssembleScript().set(extension.getAssembleScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configurePublishTask(final PublishTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Publishes frontend artifacts by running a specific script.");
        task.getPackageJsonDirectory().set(extension.getPackageJsonDirectory());
        task.getLoggingLevel().set(extension.getLoggingLevel());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnEnabled().set(extension.getYarnEnabled());
        if (task.getYarnEnabled().get()) {
            task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        }
        task.getPublishScript().set(extension.getPublishScript());
        task.setOnlyIf(t -> extension.getAssembleScript().isPresent() && extension.getPublishScript().isPresent());
    }

    /**
     * Configures a static dependency between 2 tasks: task {@code taskName} depends on task {@code dependsOnTaskName}.
     *
     * @param taskContainer Task container.
     * @param taskName Name of the task that may depend on another task.
     * @param taskClass Task class.
     * @param dependsOnTaskName Name of the depending task.
     * @param dependsOnTaskClass Depending task class.
     * @param <T> Type of the dependent task.
     * @param <D> Type of the depending task.
     */
    private <T extends Task, D extends Task> void configureDependency(final TaskContainer taskContainer,
        final String taskName, final Class<T> taskClass, final String dependsOnTaskName,
        final Class<D> dependsOnTaskClass) {
        taskContainer.named(taskName, taskClass,
            task -> task.dependsOn(taskContainer.named(dependsOnTaskName, dependsOnTaskClass).getName()));
    }

    /**
     * Configures a static dependency between 2 tasks: task {@code taskName} depends on task {@code dependsOnTaskName}.
     * If the task {@code taskName} is not found, the dependency is not configured and a warning message is logged in
     * Gradle.
     *
     * @param taskContainer Task container.
     * @param taskName Name of the task that may depend on another task.
     * @param taskClass Task class.
     * @param dependsOnTaskName Name of the depending task.
     * @param dependsOnTaskClass Depending task class.
     * @param <T> Type of the dependent task.
     * @param <D> Type of the depending task.
     */
    private <T extends Task, D extends Task> void configureDependencyIfPresent(final Project project,
        final TaskContainer taskContainer, final String taskName, final Class<T> taskClass,
        final String dependsOnTaskName, final Class<D> dependsOnTaskClass) {
        try {
            configureDependency(taskContainer, taskName, taskClass, dependsOnTaskName, dependsOnTaskClass);
        } catch (final UnknownTaskException e) {
            project.getLogger().info(
                "Cannot configure dependency between task '{}' and task '{}': task '{}' not found.",
                taskName, dependsOnTaskName, taskName);
        }
    }

    /**
     * Configures a dynamic dependency between 2 tasks, based on the evaluation of a condition: : task {@code taskName}
     * depends on task {@code dependsOnTaskName} if the condition is verified.
     *
     * @param taskContainer Task container.
     * @param taskName Name of the task that may depend on another task.
     * @param taskClass Task class.
     * @param dependsOnTaskName Name of the depending task.
     * @param dependsOnTaskClass Depending task class.
     * @param condition Function to configure any of the 2 tasks and return a decision to make the task depends on the
     * depending task.
     * @param <T> Type of the dependent task.
     * @param <D> Type of the depending task.
     */
    private <T extends Task, D extends Task> void configureDependency(final TaskContainer taskContainer,
        final String taskName, final Class<T> taskClass, final String dependsOnTaskName,
        final Class<D> dependsOnTaskClass, final BiPredicate<T, D> condition) {
        taskContainer.named(taskName, taskClass, task -> {
            final TaskProvider<D> dependsOnTask = taskContainer.named(dependsOnTaskName, dependsOnTaskClass);
            if (canDependOn(task, dependsOnTask, condition)) {
                task.dependsOn(dependsOnTask.getName());
            }
        });
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
