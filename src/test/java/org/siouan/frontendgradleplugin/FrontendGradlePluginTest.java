package org.siouan.frontendgradleplugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import javax.annotation.Nonnull;

import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.AssembleTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CheckTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CleanTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.FrontendExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallDependenciesTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.NodeInstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.PublishTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.YarnInstallTask;

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

        final FrontendExtension extension = Objects.requireNonNull(
            project.getExtensions().findByType(FrontendExtension.class));

        assertThat(extension.getNodeDistributionProvided().get()).isFalse();
        assertThat(extension.getNodeVersion().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionUrlRoot().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_NODE_DISTRIBUTION_URL_ROOT);
        assertThat(extension.getNodeDistributionUrlPathPattern().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN);
        assertThat(extension.getNodeInstallDirectory().getAsFile().get()).isEqualTo(
            project.file(FrontendGradlePlugin.DEFAULT_NODE_INSTALL_DIRNAME));
        assertThat(extension.getNodeDistributionServerUsername().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionServerPassword().isPresent()).isFalse();
        assertThat(extension.getYarnEnabled().get()).isFalse();
        assertThat(extension.getYarnDistributionProvided().get()).isFalse();
        assertThat(extension.getYarnVersion().isPresent()).isFalse();
        assertThat(extension.getYarnDistributionUrlRoot().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_YARN_DISTRIBUTION_URL_ROOT);
        assertThat(extension.getYarnDistributionUrlPathPattern().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_YARN_DISTRIBUTION_URL_PATH_PATTERN);
        assertThat(extension.getYarnInstallDirectory().getAsFile().get()).isEqualTo(
            project.file(FrontendGradlePlugin.DEFAULT_YARN_INSTALL_DIRNAME));
        assertThat(extension.getPackageJsonDirectory().get()).isEqualTo(project.getProjectDir());
        assertThat(extension.getHttpsProxyHost().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyPort().get()).isEqualTo(FrontendGradlePlugin.DEFAULT_HTTPS_PROXY_PORT);
        assertThat(extension.getHttpsProxyUsername().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyPassword().isPresent()).isFalse();
        assertThat(extension.getHttpProxyHost().isPresent()).isFalse();
        assertThat(extension.getHttpProxyPort().get()).isEqualTo(FrontendGradlePlugin.DEFAULT_HTTP_PROXY_PORT);
        assertThat(extension.getHttpProxyUsername().isPresent()).isFalse();
        assertThat(extension.getHttpProxyPassword().isPresent()).isFalse();
        assertThat(extension.getVerboseModeEnabled().get()).isFalse();

        assertThatTasksAreConfigured(project, extension);
    }

    @Test
    void shouldRegisterTasksWithCustomExtensionValuesWhenApplied() {
        plugin.apply(project);

        final FrontendExtension extension = Objects.requireNonNull(
            project.getExtensions().findByType(FrontendExtension.class));
        extension.getNodeDistributionProvided().set(true);
        extension.getNodeVersion().set("3.65.4");
        extension.getNodeDistributionUrlRoot().set("https://node");
        extension.getNodeDistributionUrlPathPattern().set("/node.tar.gz");
        extension.getNodeInstallDirectory().set(project.file("node-dist"));
        extension.getYarnEnabled().set(true);
        extension.getYarnDistributionProvided().set(true);
        extension.getYarnVersion().set("6.5.4");
        extension.getYarnDistributionUrlRoot().set("http://yarn");
        extension.getYarnDistributionUrlPathPattern().set("/yarn.tar.gz");
        extension.getYarnInstallDirectory().set(project.file("yarn-dist"));
        extension.getInstallScript().set("run ci");
        extension.getCleanScript().set("run clean");
        extension.getAssembleScript().set("run build");
        extension.getCheckScript().set("run test");
        extension.getPublishScript().set("run publish");
        extension.getPackageJsonDirectory().set(project.file("frontend"));
        extension.getHttpsProxyHost().set("63.72.84.103");
        extension.getHttpsProxyPort().set(8443);
        extension.getHttpsProxyUsername().set("gerg5Y54F");
        extension.getHttpsProxyPassword().set("ERV9RK34DR32");
        extension.getHttpProxyHost().set("63.72.84.102");
        extension.getHttpProxyPort().set(8080);
        extension.getHttpProxyUsername().set("htrshPDA2v6ESar");
        extension.getHttpProxyPassword().set("hts`{(gK65geR5=a");
        extension.getVerboseModeEnabled().set(true);

        assertThatTasksAreConfigured(project, extension);
    }

    private void assertThatTasksAreConfigured(@Nonnull final Project project,
        @Nonnull final FrontendExtension extension) {

        final NodeInstallTask nodeInstallTask = project
            .getTasks()
            .named(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, NodeInstallTask.class)
            .get();
        assertThat(nodeInstallTask.getNodeVersion().getOrNull()).isEqualTo(extension.getNodeVersion().getOrNull());
        assertThat(nodeInstallTask.getNodeDistributionUrlRoot().get()).isEqualTo(
            extension.getNodeDistributionUrlRoot().get());
        assertThat(nodeInstallTask.getNodeDistributionUrlPathPattern().get()).isEqualTo(
            extension.getNodeDistributionUrlPathPattern().get());
        assertThat(nodeInstallTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().get());
        assertThat(nodeInstallTask.getHttpsProxyHost().getOrNull()).isEqualTo(
            extension.getHttpsProxyHost().getOrNull());
        assertThat(nodeInstallTask.getHttpsProxyPort().get()).isEqualTo(extension.getHttpsProxyPort().get());
        assertThat(nodeInstallTask.getHttpsProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpsProxyUsername().getOrNull());
        assertThat(nodeInstallTask.getHttpsProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpsProxyPassword().getOrNull());
        assertThat(nodeInstallTask.getHttpProxyHost().getOrNull()).isEqualTo(extension.getHttpProxyHost().getOrNull());
        assertThat(nodeInstallTask.getHttpProxyPort().get()).isEqualTo(extension.getHttpProxyPort().get());
        assertThat(nodeInstallTask.getHttpProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpProxyUsername().getOrNull());
        assertThat(nodeInstallTask.getHttpProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpProxyPassword().getOrNull());
        assertThat(nodeInstallTask.getDependsOn()).isEmpty();

        final YarnInstallTask yarnInstallTask = project
            .getTasks()
            .named(FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, YarnInstallTask.class)
            .get();
        assertThat(yarnInstallTask.getOnlyIf()).isNotNull();
        assertThat(yarnInstallTask.getYarnVersion().getOrNull()).isEqualTo(extension.getYarnVersion().getOrNull());
        assertThat(yarnInstallTask.getYarnDistributionUrlRoot().get()).isEqualTo(
            extension.getYarnDistributionUrlRoot().get());
        assertThat(yarnInstallTask.getYarnDistributionUrlPathPattern().get()).isEqualTo(
            extension.getYarnDistributionUrlPathPattern().get());
        assertThat(yarnInstallTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(yarnInstallTask.getHttpsProxyHost().getOrNull()).isEqualTo(
            extension.getHttpsProxyHost().getOrNull());
        assertThat(yarnInstallTask.getHttpsProxyPort().get()).isEqualTo(extension.getHttpsProxyPort().get());
        assertThat(yarnInstallTask.getHttpsProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpsProxyUsername().getOrNull());
        assertThat(yarnInstallTask.getHttpsProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpsProxyPassword().getOrNull());
        assertThat(yarnInstallTask.getHttpProxyHost().getOrNull()).isEqualTo(extension.getHttpProxyHost().getOrNull());
        assertThat(yarnInstallTask.getHttpProxyPort().get()).isEqualTo(extension.getHttpProxyPort().get());
        assertThat(yarnInstallTask.getHttpProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpProxyUsername().getOrNull());
        assertThat(yarnInstallTask.getHttpProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpProxyPassword().getOrNull());
        assertThat(yarnInstallTask.getDependsOn()).isEmpty();

        final InstallDependenciesTask installDependenciesTask = project
            .getTasks()
            .named(FrontendGradlePlugin.INSTALL_TASK_NAME, InstallDependenciesTask.class)
            .get();
        assertThat(installDependenciesTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(installDependenciesTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(installDependenciesTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(installDependenciesTask.getInstallScript().get()).isEqualTo(extension.getInstallScript().get());
        assertThat(installDependenciesTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        assertThat(installDependenciesTask.getDependsOn()).containsExactlyInAnyOrder(nodeInstallTask.getName(),
            yarnInstallTask.getName());

        final CleanTask frontendCleanTask = project
            .getTasks()
            .named(FrontendGradlePlugin.CLEAN_TASK_NAME, CleanTask.class)
            .get();
        assertThat(frontendCleanTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(frontendCleanTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCleanTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(frontendCleanTask.getCleanScript().getOrNull()).isEqualTo(extension.getCleanScript().getOrNull());
        assertThat(frontendCleanTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getCleanScript().isPresent()) {
            assertThat(frontendCleanTask.getDependsOn()).containsExactly(installDependenciesTask.getName());
        } else {
            assertThat(frontendCleanTask.getDependsOn()).isEmpty();
        }
        assertThat(project.getTasks().named(BasePlugin.CLEAN_TASK_NAME).get().getDependsOn()).contains(
            frontendCleanTask.getName());

        final AssembleTask frontendAssembleTask = project
            .getTasks()
            .named(FrontendGradlePlugin.ASSEMBLE_TASK_NAME, AssembleTask.class)
            .get();
        assertThat(frontendAssembleTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(frontendAssembleTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendAssembleTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(frontendAssembleTask.getAssembleScript().getOrNull()).isEqualTo(
            extension.getAssembleScript().getOrNull());
        assertThat(frontendAssembleTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getAssembleScript().isPresent()) {
            assertThat(frontendAssembleTask.getDependsOn()).containsExactly(installDependenciesTask.getName());
        } else {
            assertThat(frontendAssembleTask.getDependsOn()).isEmpty();
        }
        assertThat(project.getTasks().named(BasePlugin.ASSEMBLE_TASK_NAME).get().getDependsOn()).contains(
            frontendAssembleTask.getName());

        final CheckTask frontendCheckTask = project
            .getTasks()
            .named(FrontendGradlePlugin.CHECK_TASK_NAME, CheckTask.class)
            .get();
        assertThat(frontendCheckTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(frontendCheckTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCheckTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(frontendCheckTask.getCheckScript().getOrNull()).isEqualTo(extension.getCheckScript().getOrNull());
        assertThat(frontendCheckTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getCheckScript().isPresent()) {
            assertThat(frontendCheckTask.getDependsOn()).containsExactly(installDependenciesTask.getName());
        } else {
            assertThat(frontendCheckTask.getDependsOn()).isEmpty();
        }
        assertThat(project.getTasks().named(LifecycleBasePlugin.CHECK_TASK_NAME).get().getDependsOn()).contains(
            frontendCheckTask.getName());

        final PublishTask frontendPublishTask = project
            .getTasks()
            .named(FrontendGradlePlugin.PUBLISH_TASK_NAME, PublishTask.class)
            .get();
        assertThat(frontendPublishTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(frontendPublishTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendPublishTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(frontendPublishTask.getPublishScript().getOrNull()).isEqualTo(
            extension.getPublishScript().getOrNull());
        assertThat(frontendPublishTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getPublishScript().isPresent()) {
            assertThat(frontendPublishTask.getDependsOn()).containsExactly(frontendAssembleTask.getName());
        } else {
            assertThat(frontendPublishTask.getDependsOn()).isEmpty();
        }
        assertThat(
            project.getTasks().named(PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME).get().getDependsOn()).contains(
            frontendPublishTask.getName());
    }
}
