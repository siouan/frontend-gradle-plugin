package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.FrontendExtension;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.tasks.AssembleTask;
import org.siouan.frontendgradleplugin.tasks.CheckTask;
import org.siouan.frontendgradleplugin.tasks.CleanTask;
import org.siouan.frontendgradleplugin.tasks.InstallTask;
import org.siouan.frontendgradleplugin.tasks.NodeInstallTask;
import org.siouan.frontendgradleplugin.tasks.YarnInstallTask;

/**
 * Unit tests for the {@link FrontendGradlePlugin} class.
 */
@ExtendWith(MockitoExtension.class)
class FrontendGradlePluginTest {

    private FrontendGradlePlugin plugin;

    private Project project;

    @BeforeEach
    void setUp() {
        project = ProjectBuilder.builder().build();
        plugin = new FrontendGradlePlugin();
    }

    @Test
    void shouldRegisterTasksWithDefaultExtensionValuesWhenApplied() {
        plugin.apply(project);

        final FrontendExtension extension = project.getExtensions().findByType(FrontendExtension.class);

        assertThat(extension).isNotNull();
        assertThat(extension.getYarnEnabled().get()).isFalse();
        assertThat(extension.getNodeInstallDirectory().get())
            .isEqualTo(new File(project.getProjectDir(), FrontendGradlePlugin.DEFAULT_NODE_INSTALL_DIRNAME));
        assertThat(extension.getYarnInstallDirectory().get())
            .isEqualTo(new File(project.getProjectDir(), FrontendGradlePlugin.DEFAULT_YARN_INSTALL_DIRNAME));

        final NodeInstallTask nodeInstallTask = project.getTasks()
            .named(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, NodeInstallTask.class).get();
        assertThat(nodeInstallTask.getNodeVersion().isPresent()).isFalse();
        assertThat(nodeInstallTask.getNodeDistributionUrl().isPresent()).isFalse();
        assertThat(nodeInstallTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());

        final YarnInstallTask yarnInstallTask = project.getTasks()
            .named(FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, YarnInstallTask.class).get();
        assertThat(yarnInstallTask.isEnabled()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(yarnInstallTask.getYarnVersion().isPresent()).isFalse();
        assertThat(yarnInstallTask.getYarnDistributionUrl().isPresent()).isFalse();
        assertThat(yarnInstallTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());

        final InstallTask frontendInstallTask = project.getTasks()
            .named(FrontendGradlePlugin.INSTALL_TASK_NAME, InstallTask.class).get();
        assertThat(frontendInstallTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendInstallTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendInstallTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());

        final CleanTask frontendCleanTask = project.getTasks()
            .named(FrontendGradlePlugin.CLEAN_TASK_NAME, CleanTask.class).get();
        assertThat(frontendCleanTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCleanTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendCleanTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());
        assertThat(frontendCleanTask.getCleanScript().isPresent()).isFalse();

        final AssembleTask frontendAssembleTask = project.getTasks()
            .named(FrontendGradlePlugin.ASSEMBLE_TASK_NAME, AssembleTask.class).get();
        assertThat(frontendAssembleTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendAssembleTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendAssembleTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());
        assertThat(frontendAssembleTask.getAssembleScript().isPresent()).isFalse();

        final CheckTask frontendCheckTask = project.getTasks()
            .named(FrontendGradlePlugin.CHECK_TASK_NAME, CheckTask.class).get();
        assertThat(frontendCheckTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCheckTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendCheckTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());
        assertThat(frontendCheckTask.getCheckScript().isPresent()).isFalse();
    }

    @Test
    void shouldRegisterTasksWithCustomExtensionValuesWhenApplied() {
        plugin.apply(project);

        final FrontendExtension extension = project.getExtensions().findByType(FrontendExtension.class);
        extension.getNodeVersion().set("3.65.4");
        extension.getNodeDistributionUrl().set("https://node");
        extension.getNodeInstallDirectory().set(new File(project.getProjectDir(), "node-dist"));
        extension.getYarnEnabled().set(true);
        extension.getYarnVersion().set("6.5.4");
        extension.getYarnDistributionUrl().set("http://yarn");
        extension.getYarnInstallDirectory().set(new File(project.getProjectDir(), "yarn-dist"));
        extension.getCleanScript().set("clean");
        extension.getAssembleScript().set("assemble");
        extension.getCheckScript().set("test");

        final NodeInstallTask nodeInstallTask = project.getTasks()
            .named(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, NodeInstallTask.class).get();
        assertThat(nodeInstallTask.getNodeVersion().get()).isEqualTo(extension.getNodeVersion().get());
        assertThat(nodeInstallTask.getNodeDistributionUrl().get()).isEqualTo(extension.getNodeDistributionUrl().get());
        assertThat(nodeInstallTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());

        final YarnInstallTask yarnInstallTask = project.getTasks()
            .named(FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, YarnInstallTask.class).get();
        assertThat(yarnInstallTask.isEnabled()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(yarnInstallTask.getYarnVersion().get()).isEqualTo(extension.getYarnVersion().get());
        assertThat(yarnInstallTask.getYarnDistributionUrl().get()).isEqualTo(extension.getYarnDistributionUrl().get());
        assertThat(yarnInstallTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());

        final InstallTask frontendInstallTask = project.getTasks()
            .named(FrontendGradlePlugin.INSTALL_TASK_NAME, InstallTask.class).get();
        assertThat(frontendInstallTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendInstallTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendInstallTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());

        final CleanTask frontendCleanTask = project.getTasks()
            .named(FrontendGradlePlugin.CLEAN_TASK_NAME, CleanTask.class).get();
        assertThat(frontendCleanTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCleanTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendCleanTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());
        assertThat(frontendCleanTask.getCleanScript().get()).isEqualTo(extension.getCleanScript().get());

        final AssembleTask frontendAssembleTask = project.getTasks()
            .named(FrontendGradlePlugin.ASSEMBLE_TASK_NAME, AssembleTask.class).get();
        assertThat(frontendAssembleTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendAssembleTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendAssembleTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());
        assertThat(frontendAssembleTask.getAssembleScript().get()).isEqualTo(extension.getAssembleScript().get());

        final CheckTask frontendCheckTask = project.getTasks()
            .named(FrontendGradlePlugin.CHECK_TASK_NAME, CheckTask.class).get();
        assertThat(frontendCheckTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCheckTask.getNodeInstallDirectory().get())
            .isEqualTo(extension.getNodeInstallDirectory().get());
        assertThat(frontendCheckTask.getYarnInstallDirectory().get())
            .isEqualTo(extension.getYarnInstallDirectory().get());
        assertThat(frontendCheckTask.getCheckScript().get()).isEqualTo(extension.getCheckScript().get());
    }
}
