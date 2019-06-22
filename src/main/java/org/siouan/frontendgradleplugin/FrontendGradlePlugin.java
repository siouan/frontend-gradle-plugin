package org.siouan.frontendgradleplugin;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.siouan.frontendgradleplugin.tasks.AssembleTask;
import org.siouan.frontendgradleplugin.tasks.CheckTask;
import org.siouan.frontendgradleplugin.tasks.CleanTask;
import org.siouan.frontendgradleplugin.tasks.InstallTask;
import org.siouan.frontendgradleplugin.tasks.NodeInstallTask;
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

        final FrontendExtension extension = project.getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project);
        extension.getNodeInstallDirectory().convention(new File(project.getProjectDir(), DEFAULT_NODE_INSTALL_DIRNAME));
        extension.getYarnEnabled().convention(false);
        extension.getYarnInstallDirectory().convention(new File(project.getProjectDir(), DEFAULT_YARN_INSTALL_DIRNAME));

        final TaskContainer projectTasks = project.getTasks();
        projectTasks
            .register(NODE_INSTALL_TASK_NAME, NodeInstallTask.class, task -> configureNodeInstallTask(task, extension));
        projectTasks
            .register(YARN_INSTALL_TASK_NAME, YarnInstallTask.class, task -> configureYarnInstallTask(task, extension));
        projectTasks.register(INSTALL_TASK_NAME, InstallTask.class, task -> configureInstallTask(task, extension));
        projectTasks.register(CLEAN_TASK_NAME, CleanTask.class, task -> configureCleanTask(task, extension));
        projectTasks.register(CHECK_TASK_NAME, CheckTask.class, task -> configureCheckTask(task, extension));
        projectTasks.register(ASSEMBLE_TASK_NAME, AssembleTask.class, task -> configureAssembleTask(task, extension));

        projectTasks.named(INSTALL_TASK_NAME, InstallTask.class, task -> {
            task.dependsOn(NODE_INSTALL_TASK_NAME);
            final TaskProvider<YarnInstallTask> yarnInstallTask = projectTasks
                .named(YARN_INSTALL_TASK_NAME, YarnInstallTask.class);
            if (yarnInstallTask.isPresent() && yarnInstallTask.get().isEnabled()) {
                task.dependsOn(yarnInstallTask.getName());
            }
        });
        projectTasks.named(CLEAN_TASK_NAME, CleanTask.class, task -> {
            final TaskProvider<InstallTask> installTask = projectTasks.named(INSTALL_TASK_NAME, InstallTask.class);
            if (task.getCleanScript().isPresent()) {
                task.dependsOn(installTask.getName());
            }
        });
        projectTasks.named(ASSEMBLE_TASK_NAME, AssembleTask.class, task -> {
            final TaskProvider<InstallTask> installTask = projectTasks.named(INSTALL_TASK_NAME, InstallTask.class);
            if (task.getAssembleScript().isPresent()) {
                task.dependsOn(installTask.getName());
            }
        });
        projectTasks.named(CHECK_TASK_NAME, CheckTask.class, task -> {
            final TaskProvider<InstallTask> installTask = projectTasks.named(INSTALL_TASK_NAME, InstallTask.class);
            if (task.getCheckScript().isPresent()) {
                task.dependsOn(installTask.getName());
            }
        });
        projectTasks.named(GRADLE_CLEAN_TASK_NAME, task -> {
            final TaskProvider<CleanTask> cleanTask = projectTasks.named(CLEAN_TASK_NAME, CleanTask.class);
            if (cleanTask.isPresent() && cleanTask.get().getCleanScript().isPresent()) {
                task.dependsOn(cleanTask.getName());
            }
        });
        projectTasks.named(GRADLE_ASSEMBLE_TASK_NAME, task -> {
            final TaskProvider<AssembleTask> assembleTask = projectTasks.named(ASSEMBLE_TASK_NAME, AssembleTask.class);
            if (assembleTask.isPresent() && assembleTask.get().getAssembleScript().isPresent()) {
                task.dependsOn(assembleTask.getName());
            }
        });
        projectTasks.named(GRADLE_CHECK_TASK_NAME, task -> {
            final TaskProvider<CheckTask> checkTask = projectTasks.named(CHECK_TASK_NAME, CheckTask.class);
            if (checkTask.isPresent() && checkTask.get().getCheckScript().isPresent()) {
                task.dependsOn(checkTask.getName());
            }
        });
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
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureYarnInstallTask(final YarnInstallTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Yarn distribution.");
        task.setEnabled(extension.getYarnEnabled().get());
        task.getYarnVersion().set(extension.getYarnVersion());
        task.getYarnDistributionUrl().set(extension.getYarnDistributionUrl());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureInstallTask(final InstallTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs/updates frontend dependencies.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getInstallScript().set(extension.getInstallScript());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureCleanTask(final CleanTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getCleanScript().set(extension.getCleanScript());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureCheckTask(final CheckTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getCheckScript().set(extension.getCheckScript());
    }

    /**
     * Configures the given task with the plugin extension.
     *
     * @param task Task.
     * @param extension Plugin extension.
     */
    private void configureAssembleTask(final AssembleTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles the frontend by running a specific script.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getAssembleScript().set(extension.getAssembleScript());
    }
}
