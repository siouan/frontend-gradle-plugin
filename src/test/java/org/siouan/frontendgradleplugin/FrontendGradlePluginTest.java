package org.siouan.frontendgradleplugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Paths;
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
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallPackageManagerTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.NodeInstallTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.PublishTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ResolvePackageManagerTask;

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
    void should_register_tasks_with_default_extension_values_applied() {
        plugin.apply(project);

        final FrontendExtension extension = Objects.requireNonNull(
            project.getExtensions().findByType(FrontendExtension.class));

        assertThat(extension.getNodeDistributionProvided().get()).isFalse();
        assertThat(extension.getNodeVersion().isPresent()).isFalse();
        assertThat(extension.getNodeInstallDirectory().getAsFile().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionUrlRoot().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_NODE_DISTRIBUTION_URL_ROOT);
        assertThat(extension.getNodeDistributionUrlPathPattern().get()).isEqualTo(
            FrontendGradlePlugin.DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN);
        assertThat(extension.getNodeDistributionServerUsername().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionServerPassword().isPresent()).isFalse();
        assertThat(extension.getInstallScript().get()).isEqualTo(FrontendGradlePlugin.DEFAULT_INSTALL_SCRIPT);
        assertThat(extension.getCleanScript().isPresent()).isFalse();
        assertThat(extension.getAssembleScript().isPresent()).isFalse();
        assertThat(extension.getCheckScript().isPresent()).isFalse();
        assertThat(extension.getPublishScript().isPresent()).isFalse();
        assertThat(extension.getPackageJsonDirectory().getAsFile().get()).isEqualTo(project.getProjectDir());
        assertThat(extension.getHttpProxyHost().isPresent()).isFalse();
        assertThat(extension.getHttpProxyPort().get()).isEqualTo(FrontendGradlePlugin.DEFAULT_HTTP_PROXY_PORT);
        assertThat(extension.getHttpProxyUsername().isPresent()).isFalse();
        assertThat(extension.getHttpProxyPassword().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyHost().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyPort().get()).isEqualTo(FrontendGradlePlugin.DEFAULT_HTTPS_PROXY_PORT);
        assertThat(extension.getHttpsProxyUsername().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyPassword().isPresent()).isFalse();
        assertThat(extension.getInternalMetadataFile().getAsFile().get()).isEqualTo(
            project.getProjectDir().toPath().resolve(FrontendGradlePlugin.METADATA_FILE_NAME).toFile());
        assertThat(extension.getInternalPackageManagerNameFile().getAsFile().get()).isEqualTo(project
            .getBuildDir()
            .toPath()
            .resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
                FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                FrontendGradlePlugin.PACKAGE_MANAGER_NAME_FILE_NAME))
            .toFile());
        assertThat(extension.getInternalPackageManagerExecutablePathFile().getAsFile().get()).isEqualTo(project
            .getBuildDir()
            .toPath()
            .resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
                FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                FrontendGradlePlugin.PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME))
            .toFile());
        assertThat(extension.getVerboseModeEnabled().get()).isFalse();

        assertThatTasksAreConfigured(project, extension);
    }

    @Test
    void should_register_tasks_with_custom_extension_values_applied() {
        plugin.apply(project);

        final FrontendExtension extension = Objects.requireNonNull(
            project.getExtensions().findByType(FrontendExtension.class));
        extension.getNodeDistributionProvided().set(true);
        extension.getNodeVersion().set("3.65.4");
        extension.getNodeDistributionUrlRoot().set("https://node");
        extension.getNodeDistributionUrlPathPattern().set("/node.tar.gz");
        extension.getNodeInstallDirectory().set(project.file("node-dist"));
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
        extension.getInternalMetadataFile().set(new File("metadata.json"));

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
        assertThat(nodeInstallTask.getNodeDistributionServerUsername().getOrNull()).isEqualTo(
            extension.getNodeDistributionServerUsername().getOrNull());
        assertThat(nodeInstallTask.getNodeDistributionServerPassword().getOrNull()).isEqualTo(
            extension.getNodeDistributionServerPassword().getOrNull());
        assertThat(nodeInstallTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(nodeInstallTask.getHttpProxyHost().getOrNull()).isEqualTo(extension.getHttpProxyHost().getOrNull());
        assertThat(nodeInstallTask.getHttpProxyPort().get()).isEqualTo(extension.getHttpProxyPort().get());
        assertThat(nodeInstallTask.getHttpProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpProxyUsername().getOrNull());
        assertThat(nodeInstallTask.getHttpProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpProxyPassword().getOrNull());
        assertThat(nodeInstallTask.getHttpsProxyHost().getOrNull()).isEqualTo(
            extension.getHttpsProxyHost().getOrNull());
        assertThat(nodeInstallTask.getHttpsProxyPort().get()).isEqualTo(extension.getHttpsProxyPort().get());
        assertThat(nodeInstallTask.getHttpsProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpsProxyUsername().getOrNull());
        assertThat(nodeInstallTask.getHttpsProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpsProxyPassword().getOrNull());
        assertThat(nodeInstallTask.getDependsOn()).isEmpty();

        final ResolvePackageManagerTask resolvePackageManagerTask = project
            .getTasks()
            .named(FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
            .get();
        assertThat(resolvePackageManagerTask.getMetadataFile().getAsFile().get()).isEqualTo(
            extension.getInternalMetadataFile().getAsFile().get());
        assertThat(resolvePackageManagerTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(resolvePackageManagerTask.getPackageManagerNameFile().getAsFile().get()).isEqualTo(
            extension.getInternalPackageManagerNameFile().getAsFile().get());
        assertThat(resolvePackageManagerTask.getPackageManagerExecutablePathFile().getAsFile().get()).isEqualTo(
            extension.getInternalPackageManagerExecutablePathFile().getAsFile().get());
        assertThat(nodeInstallTask.getDependsOn()).isEmpty();
        assertThat(resolvePackageManagerTask.getDependsOn()).containsExactlyInAnyOrder(
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        final InstallPackageManagerTask installPackageManagerTask = project
            .getTasks()
            .named(FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class)
            .get();
        assertThat(installPackageManagerTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(installPackageManagerTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(installPackageManagerTask.getPackageManagerNameFile().getAsFile().get()).isEqualTo(
            extension.getInternalPackageManagerNameFile().getAsFile().get());
        assertThat(installPackageManagerTask.getDependsOn()).containsExactlyInAnyOrder(nodeInstallTask.getName(),
            resolvePackageManagerTask.getName());

        final InstallDependenciesTask installDependenciesTask = project
            .getTasks()
            .named(FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, InstallDependenciesTask.class)
            .get();
        assertThat(installDependenciesTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(installDependenciesTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(installDependenciesTask.getInstallScript().get()).isEqualTo(extension.getInstallScript().get());
        assertThat(installDependenciesTask.getDependsOn()).containsExactlyInAnyOrder(
            resolvePackageManagerTask.getName(), installPackageManagerTask.getName());

        final CleanTask frontendCleanTask = project
            .getTasks()
            .named(FrontendGradlePlugin.CLEAN_TASK_NAME, CleanTask.class)
            .get();
        assertThat(frontendCleanTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(frontendCleanTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(frontendCleanTask.getCleanScript().getOrNull()).isEqualTo(extension.getCleanScript().getOrNull());
        assertThat(frontendCleanTask.getDependsOn()).containsExactlyInAnyOrder(resolvePackageManagerTask.getName(),
            installDependenciesTask.getName());
        assertThat(project.getTasks().named(BasePlugin.CLEAN_TASK_NAME).get().getDependsOn()).contains(
            frontendCleanTask.getName());

        final AssembleTask frontendAssembleTask = project
            .getTasks()
            .named(FrontendGradlePlugin.ASSEMBLE_TASK_NAME, AssembleTask.class)
            .get();
        assertThat(frontendAssembleTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(frontendAssembleTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(frontendAssembleTask.getAssembleScript().getOrNull()).isEqualTo(
            extension.getAssembleScript().getOrNull());
        assertThat(frontendAssembleTask.getDependsOn()).containsExactlyInAnyOrder(resolvePackageManagerTask.getName(),
            installDependenciesTask.getName());
        assertThat(project.getTasks().named(BasePlugin.ASSEMBLE_TASK_NAME).get().getDependsOn()).contains(
            frontendAssembleTask.getName());

        final CheckTask frontendCheckTask = project
            .getTasks()
            .named(FrontendGradlePlugin.CHECK_TASK_NAME, CheckTask.class)
            .get();
        assertThat(frontendCheckTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(frontendCheckTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(frontendCheckTask.getCheckScript().getOrNull()).isEqualTo(extension.getCheckScript().getOrNull());
        assertThat(frontendCheckTask.getDependsOn()).containsExactlyInAnyOrder(resolvePackageManagerTask.getName(),
            installDependenciesTask.getName());
        assertThat(project.getTasks().named(LifecycleBasePlugin.CHECK_TASK_NAME).get().getDependsOn()).contains(
            frontendCheckTask.getName());

        final PublishTask frontendPublishTask = project
            .getTasks()
            .named(FrontendGradlePlugin.PUBLISH_TASK_NAME, PublishTask.class)
            .get();
        assertThat(frontendPublishTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(frontendPublishTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(frontendPublishTask.getPublishScript().getOrNull()).isEqualTo(
            extension.getPublishScript().getOrNull());
        assertThat(frontendPublishTask.getDependsOn()).containsExactlyInAnyOrder(resolvePackageManagerTask.getName(),
            frontendAssembleTask.getName());
        assertThat(
            project.getTasks().named(PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME).get().getDependsOn()).contains(
            frontendPublishTask.getName());
    }
}
