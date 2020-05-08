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
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallTask;
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

        final FrontendExtension extension = project.getExtensions().findByType(FrontendExtension.class);

        assertThat(extension).isNotNull();
        assertThat(extension.getNodeDistributionProvided().get()).isFalse();
        assertThat(extension.getNodeVersion().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionUrlPattern().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_NODE_DISTRIBUTION_URL_PATTERN);
        assertThat(extension.getNodeInstallDirectory().getAsFile().get()).isEqualTo(
            project.file(FrontendGradlePlugin.DEFAULT_NODE_INSTALL_DIRNAME));
        assertThat(extension.getNodeDistributionServerUsername().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionServerPassword().isPresent()).isFalse();
        assertThat(extension.getYarnEnabled().get()).isFalse();
        assertThat(extension.getYarnDistributionProvided().get()).isFalse();
        assertThat(extension.getYarnVersion().isPresent()).isFalse();
        assertThat(extension.getYarnDistributionUrlPattern().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_YARN_DISTRIBUTION_URL_PATTERN);
        assertThat(extension.getYarnInstallDirectory().getAsFile().get()).isEqualTo(
            project.file(FrontendGradlePlugin.DEFAULT_YARN_INSTALL_DIRNAME));
        assertThat(extension.getPackageJsonDirectory().get()).isEqualTo(project.getProjectDir());
        assertThat(extension.getProxyHost().isPresent()).isFalse();
        assertThat(extension.getProxyPort().get()).isEqualTo(FrontendGradlePlugin.DEFAULT_PROXY_PORT);
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
        extension.getNodeDistributionUrlPattern().set("https://node");
        extension.getNodeInstallDirectory().set(project.file("node-dist"));
        extension.getYarnEnabled().set(true);
        extension.getYarnDistributionProvided().set(true);
        extension.getYarnVersion().set("6.5.4");
        extension.getYarnDistributionUrlPattern().set("http://yarn");
        extension.getYarnInstallDirectory().set(project.file("yarn-dist"));
        extension.getInstallScript().set("run ci");
        extension.getCleanScript().set("run clean");
        extension.getAssembleScript().set("run build");
        extension.getCheckScript().set("run test");
        extension.getPublishScript().set("run publish");
        extension.getPackageJsonDirectory().set(project.file("frontend"));
        extension.getProxyHost().set("63.72.84.102");
        extension.getProxyPort().set(8443);
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
        assertThat(nodeInstallTask.getNodeDistributionUrlPattern().get()).isEqualTo(
            extension.getNodeDistributionUrlPattern().get());
        assertThat(nodeInstallTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().get());
        assertThat(nodeInstallTask.getProxyHost().getOrNull()).isEqualTo(extension.getProxyHost().getOrNull());
        assertThat(nodeInstallTask.getProxyPort().get()).isEqualTo(extension.getProxyPort().get());
        assertThat(nodeInstallTask.getDependsOn()).isEmpty();

        final YarnInstallTask yarnInstallTask = project
            .getTasks()
            .named(FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, YarnInstallTask.class)
            .get();
        assertThat(yarnInstallTask.getOnlyIf()).isNotNull();
        assertThat(yarnInstallTask.getYarnVersion().getOrNull()).isEqualTo(extension.getYarnVersion().getOrNull());
        assertThat(yarnInstallTask.getYarnDistributionUrlPattern().get()).isEqualTo(
            extension.getYarnDistributionUrlPattern().get());
        assertThat(yarnInstallTask.getYarnInstallDirectory().get()).isEqualTo(
            extension.getYarnInstallDirectory().get());
        assertThat(yarnInstallTask.getProxyHost().getOrNull()).isEqualTo(extension.getProxyHost().getOrNull());
        assertThat(yarnInstallTask.getProxyPort().get()).isEqualTo(extension.getProxyPort().get());
        assertThat(yarnInstallTask.getDependsOn()).isEmpty();

        final InstallTask frontendInstallTask = project
            .getTasks()
            .named(FrontendGradlePlugin.INSTALL_TASK_NAME, InstallTask.class)
            .get();
        assertThat(frontendInstallTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(frontendInstallTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendInstallTask.getYarnInstallDirectory().getOrNull()).satisfies(yarnInstallDirectory -> {
            if (extension.getYarnEnabled().get()) {
                assertThat(yarnInstallDirectory).isEqualTo(extension.getYarnInstallDirectory().get());
            } else {
                assertThat(yarnInstallDirectory).isNull();
            }
        });
        assertThat(frontendInstallTask.getInstallScript().get()).isEqualTo(extension.getInstallScript().get());
        assertThat(frontendInstallTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        assertThat(frontendInstallTask.getDependsOn()).containsExactlyInAnyOrder(nodeInstallTask.getName(),
            yarnInstallTask.getName());

        final CleanTask frontendCleanTask = project
            .getTasks()
            .named(FrontendGradlePlugin.CLEAN_TASK_NAME, CleanTask.class)
            .get();
        assertThat(frontendCleanTask.getNodeInstallDirectory().getOrNull()).isEqualTo(
            extension.getNodeInstallDirectory().getOrNull());
        assertThat(frontendCleanTask.getYarnEnabled().get()).isEqualTo(extension.getYarnEnabled().get());
        assertThat(frontendCleanTask.getYarnInstallDirectory().getOrNull()).satisfies(yarnInstallDirectory -> {
            if (extension.getYarnEnabled().get()) {
                assertThat(yarnInstallDirectory).isEqualTo(extension.getYarnInstallDirectory().get());
            } else {
                assertThat(yarnInstallDirectory).isNull();
            }
        });
        assertThat(frontendCleanTask.getCleanScript().getOrNull()).isEqualTo(extension.getCleanScript().getOrNull());
        assertThat(frontendCleanTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getCleanScript().isPresent()) {
            assertThat(frontendCleanTask.getDependsOn()).containsExactly(frontendInstallTask.getName());
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
        assertThat(frontendAssembleTask.getYarnInstallDirectory().getOrNull()).satisfies(yarnInstallDirectory -> {
            if (extension.getYarnEnabled().get()) {
                assertThat(yarnInstallDirectory).isEqualTo(extension.getYarnInstallDirectory().get());
            } else {
                assertThat(yarnInstallDirectory).isNull();
            }
        });
        assertThat(frontendAssembleTask.getAssembleScript().getOrNull()).isEqualTo(
            extension.getAssembleScript().getOrNull());
        assertThat(frontendAssembleTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getAssembleScript().isPresent()) {
            assertThat(frontendAssembleTask.getDependsOn()).containsExactly(frontendInstallTask.getName());
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
        assertThat(frontendCheckTask.getYarnInstallDirectory().getOrNull()).satisfies(yarnInstallDirectory -> {
            if (extension.getYarnEnabled().get()) {
                assertThat(yarnInstallDirectory).isEqualTo(extension.getYarnInstallDirectory().get());
            } else {
                assertThat(yarnInstallDirectory).isNull();
            }
        });
        assertThat(frontendCheckTask.getCheckScript().getOrNull()).isEqualTo(extension.getCheckScript().getOrNull());
        assertThat(frontendCheckTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().get());
        if (extension.getCheckScript().isPresent()) {
            assertThat(frontendCheckTask.getDependsOn()).containsExactly(frontendInstallTask.getName());
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
        assertThat(frontendPublishTask.getYarnInstallDirectory().getOrNull()).satisfies(yarnInstallDirectory -> {
            if (extension.getYarnEnabled().get()) {
                assertThat(yarnInstallDirectory).isEqualTo(extension.getYarnInstallDirectory().get());
            } else {
                assertThat(yarnInstallDirectory).isNull();
            }
        });
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
