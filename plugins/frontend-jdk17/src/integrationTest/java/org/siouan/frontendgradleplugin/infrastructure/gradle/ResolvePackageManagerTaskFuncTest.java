package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.DEFAULT_CACHE_DIRECTORY_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.PACKAGE_JSON_FILE_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.PACKAGE_MANAGER_SPECIFICATION_FILE_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.FAILED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.IGNORED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link ResolvePackageManagerTask} integration in a Gradle build.
 *
 * @since 7.0.0
 */
class ResolvePackageManagerTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_delete_output_files_when_package_json_file_does_not_exist() throws IOException {
        Files.createDirectory(projectDirectoryPath.resolve(PACKAGE_JSON_FILE_NAME));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().nodeDistributionProvided(true).toMap());
        final Path packageManagerSpecificationFilePath = projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_SPECIFICATION_FILE_NAME));
        Files.createDirectories(packageManagerSpecificationFilePath.getParent());
        Files.createFile(packageManagerSpecificationFilePath);
        final Path packageManagerExecutablePathFilePath = projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME));
        Files.createFile(packageManagerExecutablePathFilePath);

        final BuildResult result1 = runGradle(projectDirectoryPath, RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result1, SKIPPED, IGNORED, SUCCESS);
        assertThat(packageManagerSpecificationFilePath).doesNotExist();
        assertThat(packageManagerExecutablePathFilePath).doesNotExist();

        final BuildResult result2 = runGradle(projectDirectoryPath, RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result2, SKIPPED, IGNORED, UP_TO_DATE);
        assertThat(packageManagerSpecificationFilePath).doesNotExist();
        assertThat(packageManagerExecutablePathFilePath).doesNotExist();
    }

    @Test
    void should_fail_when_package_manager_property_is_not_set_in_package_json_file() throws IOException {
        Files.copy(getResourcePath("package-no-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-provided"))
            .toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result, SKIPPED, IGNORED, FAILED);
        assertThat(projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_SPECIFICATION_FILE_NAME))).doesNotExist();
        assertThat(projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME))).doesNotExist();
    }

    @Test
    void should_fail_when_package_manager_property_is_invalid_in_package_json_file() throws IOException {
        Files.copy(getResourcePath("package-invalid-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-provided"))
            .toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result, SKIPPED, IGNORED, FAILED);
        assertThat(projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_SPECIFICATION_FILE_NAME))).doesNotExist();
        assertThat(projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME))).doesNotExist();
    }

    @Test
    void should_pass_when_package_manager_property_is_valid() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder()
            .nodeVersion("20.18.0")
            .nodeDistributionUrl(getResourcePath("node-v20.18.0.zip"))
            .toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result1, SUCCESS, IGNORED, SUCCESS);
        final Path packageManagerNameFilePath = projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_SPECIFICATION_FILE_NAME));
        assertThat(packageManagerNameFilePath).content(StandardCharsets.UTF_8).isEqualTo("npm@10.9.0");
        final Path packageManagerExecutablePathFilePath = projectDirectoryPath.resolve(
            Paths.get(DEFAULT_CACHE_DIRECTORY_NAME, RESOLVE_PACKAGE_MANAGER_TASK_NAME,
                PACKAGE_MANAGER_EXECUTABLE_PATH_FILE_NAME));
        assertThat(packageManagerExecutablePathFilePath).exists();

        final BuildResult result2 = runGradle(projectDirectoryPath, RESOLVE_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result2, UP_TO_DATE, IGNORED, UP_TO_DATE);
        assertThat(packageManagerNameFilePath).content(StandardCharsets.UTF_8).isEqualTo("npm@10.9.0");
        assertThat(packageManagerExecutablePathFilePath).exists();
    }
}
