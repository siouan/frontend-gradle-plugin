package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;

/**
 * Functional tests to verify the {@link ResolvePackageManagerTask} integration in a Gradle build.
 *
 * @since 7.0.0
 */
class ResolvePackageManagerTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_skip_task_when_package_json_file_is_not_a_file() throws IOException {
        Files.createDirectory(projectDirectoryPath.resolve(FrontendGradlePlugin.PACKAGE_JSON_FILE_NAME));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().nodeDistributionProvided(true).toMap());

        final BuildResult result = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.SKIPPED);
        assertThat(projectDirectoryPath.resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            FrontendGradlePlugin.PACKAGE_MANAGER_SPECIFICATION_FILE_NAME))).doesNotExist();
        assertThat(projectDirectoryPath.resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            FrontendGradlePlugin.PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME))).doesNotExist();
    }

    @Test
    void should_fail_when_package_manager_property_is_not_set_in_package_json_file() throws IOException {
        Files.copy(getResourcePath("package-no-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-provided"))
            .toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.FAILED);
        assertThat(projectDirectoryPath.resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            FrontendGradlePlugin.PACKAGE_MANAGER_SPECIFICATION_FILE_NAME))).doesNotExist();
        assertThat(projectDirectoryPath.resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            FrontendGradlePlugin.PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME))).doesNotExist();
    }

    @Test
    void should_fail_when_package_manager_property_is_invalid_in_package_json_file() throws IOException {
        Files.copy(getResourcePath("package-invalid-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-provided"))
            .toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.FAILED);
        assertThat(projectDirectoryPath.resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            FrontendGradlePlugin.PACKAGE_MANAGER_SPECIFICATION_FILE_NAME))).doesNotExist();
        assertThat(projectDirectoryPath.resolve(Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            FrontendGradlePlugin.PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME))).doesNotExist();
    }

    @Test
    void should_pass_when_package_manager_property_is_valid() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"))
            .toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS);
        final Path packageManagerNameFilePath = projectDirectoryPath.resolve(
            Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
                FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                FrontendGradlePlugin.PACKAGE_MANAGER_SPECIFICATION_FILE_NAME));
        assertThat(packageManagerNameFilePath).content(StandardCharsets.UTF_8).isEqualTo("npm@9.6.7");
        final Path packageManagerExecutablePathFilePath = projectDirectoryPath.resolve(
            Paths.get(FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME,
                FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                FrontendGradlePlugin.PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME));
        assertThat(packageManagerExecutablePathFilePath).exists();

        final BuildResult result2 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE);
        assertThat(packageManagerNameFilePath).content(StandardCharsets.UTF_8).isEqualTo("npm@9.6.7");
        assertThat(packageManagerExecutablePathFilePath).exists();
    }
}
