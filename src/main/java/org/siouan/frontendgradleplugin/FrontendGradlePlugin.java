package org.siouan.frontendgradleplugin;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;
import org.siouan.frontendgradleplugin.node.NodeInstallTask;
import org.siouan.frontendgradleplugin.yarn.YarnInstallTask;

/**
 * Main plugin class which bootstraps the plugin by declaring its DSL and its tasks.
 */
public class FrontendGradlePlugin implements Plugin<Project> {

    public static final String DEFAULT_NODE_INSTALL_DIRNAME = "node";

    public static final String DEFAULT_YARN_INSTALL_DIRNAME = "yarn";

    public static final String EXTENSION_NAME = "frontend";

    public static final String TASK_GROUP = "Frontend";

    public void apply(final Project project) {
        project.getPluginManager().apply(BasePlugin.class);

        final FrontendExtension extension = project.getExtensions()
            .create(EXTENSION_NAME, FrontendExtension.class, project);
        extension.getNodeInstallDirectory().convention(new File(project.getProjectDir(), DEFAULT_NODE_INSTALL_DIRNAME));
        extension.getYarnEnabled().convention(false);
        extension.getYarnInstallDirectory().convention(new File(project.getProjectDir(), DEFAULT_YARN_INSTALL_DIRNAME));

        final TaskContainer projectTasks = project.getTasks();
        projectTasks
            .register(NodeInstallTask.DEFAULT_NAME, NodeInstallTask.class, task -> configureTask(task, extension));

        projectTasks
            .register(YarnInstallTask.DEFAULT_NAME, YarnInstallTask.class, task -> configureTask(task, extension));

        projectTasks.register(InstallTask.DEFAULT_NAME, InstallTask.class, task -> configureTask(task, extension));

        projectTasks.register(CleanTask.DEFAULT_NAME, CleanTask.class, task -> configureTask(task, extension));
        projectTasks.named("clean", task -> task.dependsOn(projectTasks.named(CleanTask.DEFAULT_NAME)));

        projectTasks.register(CheckTask.DEFAULT_NAME, CheckTask.class, task -> configureTask(task, extension));
        projectTasks.named("check", task -> task.dependsOn(projectTasks.named(CheckTask.DEFAULT_NAME)));

        projectTasks.register(AssembleTask.DEFAULT_NAME, AssembleTask.class, task -> configureTask(task, extension));
        projectTasks.named("assemble", task -> task.dependsOn(projectTasks.named(AssembleTask.DEFAULT_NAME)));

        projectTasks.register(RunScriptTask.DEFAULT_NAME, RunScriptTask.class, task -> configureTask(task, extension));
    }

    private void configureTask(final NodeInstallTask task, final FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Node distribution.");
        task.getNodeVersion().set(extension.getNodeVersion());
        task.getNodeDistributionUrl().set(extension.getNodeDistributionUrl());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
    }

    private void configureTask(final YarnInstallTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Downloads and installs a Yarn distribution.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getYarnVersion().set(extension.getYarnVersion());
        task.getYarnDistributionUrl().set(extension.getYarnDistributionUrl());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.dependsOn(NodeInstallTask.DEFAULT_NAME);
    }

    private void configureTask(final InstallTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Installs/updates frontend dependencies.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.dependsOn(NodeInstallTask.DEFAULT_NAME, YarnInstallTask.DEFAULT_NAME);
    }

    private void configureTask(final CleanTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Cleans frontend resources outside the build directory by running a specific script.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getCleanScript().set(extension.getCleanScript());
        task.dependsOn(InstallTask.DEFAULT_NAME);
    }

    private void configureTask(final CheckTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Checks frontend by running a specific script.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getCheckScript().set(extension.getCheckScript());
        task.dependsOn(InstallTask.DEFAULT_NAME);
    }

    private void configureTask(final AssembleTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Assembles the frontend by running a specific script.");
        task.getYarnEnabled().set(extension.getYarnEnabled());
        task.getNodeInstallDirectory().set(extension.getNodeInstallDirectory());
        task.getYarnInstallDirectory().set(extension.getYarnInstallDirectory());
        task.getAssembleScript().set(extension.getAssembleScript());
        task.dependsOn(InstallTask.DEFAULT_NAME);
    }

    private void configureTask(final RunScriptTask task, FrontendExtension extension) {
        task.setGroup(TASK_GROUP);
        task.setDescription("Runs a frontend script.");
        task.dependsOn(InstallTask.DEFAULT_NAME);
    }
}
