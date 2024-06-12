package org.siouan.frontendgradleplugin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.*;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.HTTPS_PROXY_HOST;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.HTTPS_PROXY_PORT;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.HTTP_PROXY_HOST;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.HTTP_PROXY_PORT;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.JVM_ARCH;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.NON_PROXY_HOSTS;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.NON_PROXY_HOSTS_SPLIT_PATTERN;
import static org.siouan.frontendgradleplugin.domain.SystemProperties.OS_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.AssembleTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CheckTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.CleanTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.FrontendExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallCorepackTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallFrontendTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallNodeTask;
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallPackageManagerTask;
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
    void should_register_tasks_with_default_extension_values_applied() throws IOException {
        Files.createDirectory(project.file(DEFAULT_NODE_INSTALL_DIRECTORY_NAME).toPath());
        plugin.apply(project);

        final FrontendExtension extension = Objects.requireNonNull(
            project.getExtensions().findByType(FrontendExtension.class));

        assertThat(extension.getNodeDistributionProvided().get()).isFalse();
        assertThat(extension.getNodeVersion().isPresent()).isFalse();
        assertThat(extension.getNodeInstallDirectory().getAsFile().get()).isEqualTo(
            project.getLayout().getProjectDirectory().dir(DEFAULT_NODE_INSTALL_DIRECTORY_NAME).getAsFile());
        assertThat(extension.getNodeDistributionUrlRoot().get()).isEqualTo(DEFAULT_NODE_DISTRIBUTION_URL_ROOT);
        assertThat(extension.getNodeDistributionUrlPathPattern().get()).isEqualTo(
            DEFAULT_NODE_DISTRIBUTION_URL_PATH_PATTERN);
        assertThat(extension.getNodeDistributionServerUsername().isPresent()).isFalse();
        assertThat(extension.getNodeDistributionServerPassword().isPresent()).isFalse();
        assertThat(extension.getCorepackVersion().isPresent()).isFalse();
        assertThat(extension.getInstallScript().get()).isEqualTo(DEFAULT_INSTALL_SCRIPT);
        assertThat(extension.getCleanScript().isPresent()).isFalse();
        assertThat(extension.getAssembleScript().isPresent()).isFalse();
        assertThat(extension.getCheckScript().isPresent()).isFalse();
        assertThat(extension.getPublishScript().isPresent()).isFalse();
        assertThat(extension.getPackageJsonDirectory().getAsFile().get()).isEqualTo(project.getProjectDir());
        assertThat(extension.getHttpProxyHost().isPresent()).isFalse();
        assertThat(extension.getHttpProxyPort().get()).isEqualTo(DEFAULT_HTTP_PROXY_PORT);
        assertThat(extension.getHttpProxyUsername().isPresent()).isFalse();
        assertThat(extension.getHttpProxyPassword().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyHost().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyPort().get()).isEqualTo(DEFAULT_HTTPS_PROXY_PORT);
        assertThat(extension.getHttpsProxyUsername().isPresent()).isFalse();
        assertThat(extension.getHttpsProxyPassword().isPresent()).isFalse();
        assertThat(extension.getMaxDownloadAttempts().get()).isEqualTo(DEFAULT_MAX_DOWNLOAD_ATTEMPTS);
        assertThat(extension.getRetryHttpStatuses().get()).containsExactlyInAnyOrderElementsOf(
            DEFAULT_RETRY_HTTP_STATUSES);
        assertThat(extension.getRetryInitialIntervalMs().get()).isEqualTo(DEFAULT_RETRY_INITIAL_INTERVAL_MS);
        assertThat(extension.getRetryIntervalMultiplier().get()).isEqualTo(DEFAULT_RETRY_INTERVAL_MULTIPLIER);
        assertThat(extension.getRetryMaxIntervalMs().get()).isEqualTo(DEFAULT_RETRY_MAX_INTERVAL_MS);
        assertThat(extension.getCacheDirectory().getAsFile().get()).isEqualTo(
            project.getLayout().getProjectDirectory().dir(DEFAULT_CACHE_DIRECTORY_NAME).getAsFile());
        assertThat(extension.getVerboseModeEnabled().get()).isFalse();

        final Map<String, String> expectedSystemProperties = new HashMap<>();
        Set
            .of(HTTP_PROXY_HOST, HTTP_PROXY_PORT, HTTPS_PROXY_HOST, HTTPS_PROXY_PORT, NON_PROXY_HOSTS, JVM_ARCH,
                OS_NAME)
            .forEach(systemPropertyName -> {
                final String systemPropertyValue = System.getProperty(systemPropertyName);
                if (systemPropertyValue != null) {
                    expectedSystemProperties.put(systemPropertyName, systemPropertyValue);
                }
            });
        assertThatTasksAreConfigured(project, extension, expectedSystemProperties);
    }

    @Test
    @SetSystemProperty(key = HTTP_PROXY_HOST, value = "104.53.49.1")
    @SetSystemProperty(key = HTTP_PROXY_PORT, value = "52")
    @SetSystemProperty(key = HTTPS_PROXY_HOST, value = "239.19.0.5")
    @SetSystemProperty(key = HTTPS_PROXY_PORT, value = "492")
    @SetSystemProperty(key = NON_PROXY_HOSTS, value = "localhost|48.8.*|127.0.0.1")
    @SetSystemProperty(key = JVM_ARCH, value = "x86 64 bits")
    @SetSystemProperty(key = OS_NAME, value = "FreeOS")
    void should_register_tasks_with_custom_extension_values_applied() throws IOException {
        final String nodeInstallDirectoryName = "node-dist";
        Files.createDirectory(project.file(nodeInstallDirectoryName).toPath());
        plugin.apply(project);

        final FrontendExtension extension = Objects.requireNonNull(
            project.getExtensions().findByType(FrontendExtension.class));
        extension.getNodeDistributionProvided().set(true);
        extension.getNodeVersion().set("3.65.4");
        extension.getNodeDistributionUrlRoot().set("https://node");
        extension.getNodeDistributionUrlPathPattern().set("/node.tar.gz");
        extension.getNodeInstallDirectory().set(project.file(nodeInstallDirectoryName));
        extension.getCorepackVersion().set("latest");
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
        extension.getMaxDownloadAttempts().set(2);
        extension.getRetryHttpStatuses().set(Set.of(404, 503));
        extension.getRetryInitialIntervalMs().set(539);
        extension.getRetryIntervalMultiplier().set(7.3);
        extension.getRetryMaxIntervalMs().set(9623);
        extension.getRetryMaxIntervalMs().set(9623);
        extension.getCacheDirectory().set(project.file("cache"));
        extension.getVerboseModeEnabled().set(true);

        assertThatTasksAreConfigured(project, extension,
            Map.of(HTTP_PROXY_HOST, "104.53.49.1", HTTP_PROXY_PORT, "52", HTTPS_PROXY_HOST, "239.19.0.5",
                HTTPS_PROXY_PORT, "492", NON_PROXY_HOSTS, "localhost|48.8.*|127.0.0.1", JVM_ARCH, "x86 64 bits",
                OS_NAME, "FreeOS"));
    }

    private void assertThatTasksAreConfigured(final Project project, final FrontendExtension extension,
        final Map<String, String> expectedSystemProperties) {
        final InstallNodeTask installNodeTask = project
            .getTasks()
            .named(INSTALL_NODE_TASK_NAME, InstallNodeTask.class)
            .get();
        assertThat(installNodeTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(installNodeTask.getNodeVersion().getOrNull()).isEqualTo(extension.getNodeVersion().getOrNull());
        assertThat(installNodeTask.getNodeDistributionUrlRoot().get()).isEqualTo(
            extension.getNodeDistributionUrlRoot().get());
        assertThat(installNodeTask.getNodeDistributionUrlPathPattern().get()).isEqualTo(
            extension.getNodeDistributionUrlPathPattern().get());
        assertThat(installNodeTask.getNodeDistributionServerUsername().getOrNull()).isEqualTo(
            extension.getNodeDistributionServerUsername().getOrNull());
        assertThat(installNodeTask.getNodeDistributionServerPassword().getOrNull()).isEqualTo(
            extension.getNodeDistributionServerPassword().getOrNull());
        assertThat(installNodeTask.getNodeInstallDirectory().isPresent()).isTrue();
        assertThat(installNodeTask.getHttpProxyHost().getOrNull()).isEqualTo(extension.getHttpProxyHost().getOrNull());
        assertThat(installNodeTask.getHttpProxyPort().get()).isEqualTo(extension.getHttpProxyPort().get());
        assertThat(installNodeTask.getHttpProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpProxyUsername().getOrNull());
        assertThat(installNodeTask.getHttpProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpProxyPassword().getOrNull());
        assertThat(installNodeTask.getHttpsProxyHost().getOrNull()).isEqualTo(
            extension.getHttpsProxyHost().getOrNull());
        assertThat(installNodeTask.getHttpsProxyPort().get()).isEqualTo(extension.getHttpsProxyPort().get());
        assertThat(installNodeTask.getHttpsProxyUsername().getOrNull()).isEqualTo(
            extension.getHttpsProxyUsername().getOrNull());
        assertThat(installNodeTask.getHttpsProxyPassword().getOrNull()).isEqualTo(
            extension.getHttpsProxyPassword().getOrNull());
        assertThat(installNodeTask.getMaxDownloadAttempts().getOrNull()).isEqualTo(
            extension.getMaxDownloadAttempts().getOrNull());
        assertThat(installNodeTask.getRetryHttpStatuses().getOrNull()).isEqualTo(
            extension.getRetryHttpStatuses().getOrNull());
        assertThat(installNodeTask.getRetryInitialIntervalMs().getOrNull()).isEqualTo(
            extension.getRetryInitialIntervalMs().getOrNull());
        assertThat(installNodeTask.getRetryIntervalMultiplier().getOrNull()).isEqualTo(
            extension.getRetryIntervalMultiplier().getOrNull());
        assertThat(installNodeTask.getRetryMaxIntervalMs().getOrNull()).isEqualTo(
            extension.getRetryMaxIntervalMs().getOrNull());
        assertThat(installNodeTask.getVerboseModeEnabled().get()).isEqualTo(extension.getVerboseModeEnabled().get());
        assertThat(installNodeTask.getSystemHttpProxyHost().getOrNull()).isEqualTo(
            expectedSystemProperties.get(HTTP_PROXY_HOST));
        assertThat(installNodeTask.getSystemHttpProxyPort().get()).isEqualTo(Optional
            .ofNullable(expectedSystemProperties.get(HTTP_PROXY_PORT))
            .map(Integer::parseInt)
            .orElse(DEFAULT_HTTP_PROXY_PORT));
        assertThat(installNodeTask.getSystemHttpsProxyHost().getOrNull()).isEqualTo(
            expectedSystemProperties.get(HTTPS_PROXY_HOST));
        assertThat(installNodeTask.getSystemHttpsProxyPort().get()).isEqualTo(Optional
            .ofNullable(expectedSystemProperties.get(HTTPS_PROXY_PORT))
            .map(Integer::parseInt)
            .orElse(DEFAULT_HTTPS_PROXY_PORT));
        assertThat(installNodeTask.getSystemNonProxyHosts().get()).isEqualTo(Optional
            .ofNullable(expectedSystemProperties.get(NON_PROXY_HOSTS))
            .map(nonProxyHosts -> Set.of(nonProxyHosts.split(NON_PROXY_HOSTS_SPLIT_PATTERN)))
            .orElseGet(Set::of));
        assertThat(installNodeTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(installNodeTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(installNodeTask.getDependsOn()).isEmpty();

        final InstallCorepackTask installCorepackTask = project
            .getTasks()
            .named(INSTALL_COREPACK_TASK_NAME, InstallCorepackTask.class)
            .get();
        assertThat(installCorepackTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(installCorepackTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(installCorepackTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        assertThat(installCorepackTask.getCorepackVersion().getOrNull()).isEqualTo(
            extension.getCorepackVersion().getOrNull());
        assertThat(installCorepackTask.getVerboseModeEnabled().get()).isEqualTo(
            extension.getVerboseModeEnabled().get());
        assertThat(installCorepackTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(installCorepackTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(installCorepackTask.getDependsOn()).containsExactlyInAnyOrder(INSTALL_NODE_TASK_NAME);

        final ResolvePackageManagerTask resolvePackageManagerTask = project
            .getTasks()
            .named(RESOLVE_PACKAGE_MANAGER_TASK_NAME, ResolvePackageManagerTask.class)
            .get();
        assertThat(resolvePackageManagerTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(resolvePackageManagerTask.getPackageJsonFile().getAsFile().getOrNull()).isEqualTo(extension
            .getPackageJsonDirectory()
            .file(PACKAGE_JSON_FILE_NAME)
            .map(RegularFile::getAsFile)
            .filter(packageJsonFile -> Files.isRegularFile(packageJsonFile.toPath()))
            .getOrNull());
        assertThat(resolvePackageManagerTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        assertThat(resolvePackageManagerTask.getPackageManagerSpecificationFile().getAsFile().get()).isEqualTo(extension
            .getCacheDirectory()
            .dir(RESOLVE_PACKAGE_MANAGER_TASK_NAME)
            .map(directory -> directory.file(PACKAGE_MANAGER_SPECIFICATION_FILE_NAME).getAsFile())
            .get());
        assertThat(resolvePackageManagerTask.getPackageManagerExecutablePathFile().getAsFile().get()).isEqualTo(
            extension
                .getCacheDirectory()
                .dir(RESOLVE_PACKAGE_MANAGER_TASK_NAME)
                .map(directory -> directory.file(PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME).getAsFile())
                .get());
        assertThat(resolvePackageManagerTask.getVerboseModeEnabled().get()).isEqualTo(
            extension.getVerboseModeEnabled().get());
        assertThat(resolvePackageManagerTask.getSystemJvmArch().get()).isEqualTo(
            expectedSystemProperties.get(JVM_ARCH));
        assertThat(resolvePackageManagerTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(resolvePackageManagerTask.getDependsOn()).containsExactlyInAnyOrder(INSTALL_NODE_TASK_NAME);

        final InstallPackageManagerTask installPackageManagerTask = project
            .getTasks()
            .named(INSTALL_PACKAGE_MANAGER_TASK_NAME, InstallPackageManagerTask.class)
            .get();
        assertThat(installPackageManagerTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(installPackageManagerTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(installPackageManagerTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        assertThat(installPackageManagerTask.getPackageManagerSpecificationFile().getAsFile().get()).isEqualTo(
            resolvePackageManagerTask.getPackageManagerSpecificationFile().getAsFile().get());
        //installPackageManagerTask.getPackageManagerExecutableFile()
        assertThat(installPackageManagerTask.getVerboseModeEnabled().get()).isEqualTo(
            extension.getVerboseModeEnabled().get());
        assertThat(installPackageManagerTask.getSystemJvmArch().get()).isEqualTo(
            expectedSystemProperties.get(JVM_ARCH));
        assertThat(installPackageManagerTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(installPackageManagerTask.getDependsOn()).containsExactlyInAnyOrder(INSTALL_COREPACK_TASK_NAME);

        final InstallFrontendTask installFrontendTask = project
            .getTasks()
            .named(INSTALL_FRONTEND_TASK_NAME, InstallFrontendTask.class)
            .get();
        assertThat(installFrontendTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(installFrontendTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(installFrontendTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        //installFrontendTask.getPackageManagerExecutableFile()
        assertThat(installFrontendTask.getInstallScript().get()).isEqualTo(extension.getInstallScript().get());
        assertThat(installFrontendTask.getVerboseModeEnabled().get()).isEqualTo(
            extension.getVerboseModeEnabled().get());
        assertThat(installFrontendTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(installFrontendTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(installFrontendTask.getDependsOn()).containsExactlyInAnyOrder(installPackageManagerTask.getName());

        final CleanTask cleanFrontendTask = project
            .getTasks()
            .named(CLEAN_TASK_NAME, CleanTask.class)
            .get();
        assertThat(cleanFrontendTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(cleanFrontendTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(cleanFrontendTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        //frontendCleanTask.getPackageManagerExecutableFile()
        assertThat(cleanFrontendTask.getCleanScript().getOrNull()).isEqualTo(extension.getCleanScript().getOrNull());
        assertThat(cleanFrontendTask.getVerboseModeEnabled().get()).isEqualTo(extension.getVerboseModeEnabled().get());
        assertThat(cleanFrontendTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(cleanFrontendTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(cleanFrontendTask.getDependsOn()).containsExactlyInAnyOrder(installFrontendTask.getName());
        assertThat(project.getTasks().named(BasePlugin.CLEAN_TASK_NAME).get().getDependsOn()).contains(
            cleanFrontendTask.getName());

        final AssembleTask assembleFrontendTask = project
            .getTasks()
            .named(ASSEMBLE_TASK_NAME, AssembleTask.class)
            .get();
        assertThat(assembleFrontendTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(assembleFrontendTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        //frontendAssembleTask.getPackageManagerExecutableFile()
        assertThat(assembleFrontendTask.getAssembleScript().getOrNull()).isEqualTo(
            extension.getAssembleScript().getOrNull());
        assertThat(assembleFrontendTask.getVerboseModeEnabled().get()).isEqualTo(
            extension.getVerboseModeEnabled().get());
        assertThat(assembleFrontendTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(assembleFrontendTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(assembleFrontendTask.getDependsOn()).containsExactlyInAnyOrder(installFrontendTask.getName());
        assertThat(project.getTasks().named(BasePlugin.ASSEMBLE_TASK_NAME).get().getDependsOn()).contains(
            assembleFrontendTask.getName());

        final CheckTask checkFrontendTask = project
            .getTasks()
            .named(CHECK_TASK_NAME, CheckTask.class)
            .get();
        assertThat(checkFrontendTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(checkFrontendTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(checkFrontendTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        //frontendCheckTask.getPackageManagerExecutableFile()
        assertThat(checkFrontendTask.getCheckScript().getOrNull()).isEqualTo(extension.getCheckScript().getOrNull());
        assertThat(checkFrontendTask.getVerboseModeEnabled().get()).isEqualTo(extension.getVerboseModeEnabled().get());
        assertThat(checkFrontendTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(checkFrontendTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(checkFrontendTask.getDependsOn()).containsExactlyInAnyOrder(installFrontendTask.getName());
        assertThat(project.getTasks().named(LifecycleBasePlugin.CHECK_TASK_NAME).get().getDependsOn()).contains(
            checkFrontendTask.getName());

        final PublishTask publishFrontendTask = project
            .getTasks()
            .named(PUBLISH_TASK_NAME, PublishTask.class)
            .get();
        assertThat(publishFrontendTask.getBeanRegistryBuildService().get().getBeanRegistry()).isNotNull();
        assertThat(publishFrontendTask.getPackageJsonDirectory().get()).isEqualTo(
            extension.getPackageJsonDirectory().getAsFile().get());
        assertThat(publishFrontendTask.getNodeInstallDirectory().get()).isEqualTo(
            extension.getNodeInstallDirectory().getAsFile().get());
        //frontendPublishTask.getPackageManagerExecutableFile()
        assertThat(publishFrontendTask.getPublishScript().getOrNull()).isEqualTo(
            extension.getPublishScript().getOrNull());
        assertThat(publishFrontendTask.getVerboseModeEnabled().get()).isEqualTo(
            extension.getVerboseModeEnabled().get());
        assertThat(publishFrontendTask.getSystemJvmArch().get()).isEqualTo(expectedSystemProperties.get(JVM_ARCH));
        assertThat(publishFrontendTask.getSystemOsName().get()).isEqualTo(expectedSystemProperties.get(OS_NAME));
        assertThat(publishFrontendTask.getDependsOn()).containsExactlyInAnyOrder(assembleFrontendTask.getName());
        assertThat(
            project.getTasks().named(PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME).get().getDependsOn()).contains(
            publishFrontendTask.getName());
    }
}
