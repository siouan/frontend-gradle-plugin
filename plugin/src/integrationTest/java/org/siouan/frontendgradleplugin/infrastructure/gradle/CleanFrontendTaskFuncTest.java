package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.CLEAN_TASK_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.GRADLE_CLEAN_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertCleanTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link CleanTask} integration in a Gradle build. Test cases uses fake Node/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 */
class CleanFrontendTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_skip_task_when_package_json_file_does_not_exist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"))
            .cleanScript("run clean");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result1, SUCCESS, SKIPPED, SUCCESS, SKIPPED, SKIPPED, SKIPPED, null);

        final BuildResult result2 = runGradle(projectDirectoryPath, CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result2, UP_TO_DATE, SKIPPED, UP_TO_DATE, SKIPPED, SKIPPED, SKIPPED, null);
    }

    @Test
    void should_skip_task_when_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result1, SUCCESS, SKIPPED, SUCCESS, SUCCESS, SUCCESS, SKIPPED, null);

        final BuildResult result2 = runGradle(projectDirectoryPath, CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result2, UP_TO_DATE, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, SKIPPED, null);
    }

    @Test
    void should_skip_task_when_running_gradle_task_and_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, GRADLE_CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result1, SUCCESS, SKIPPED, SUCCESS, SUCCESS, SUCCESS, SKIPPED, SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, GRADLE_CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result2, UP_TO_DATE, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, SKIPPED, UP_TO_DATE);
    }

    @Test
    void should_clean_frontend() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"))
            .cleanScript("run clean");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, GRADLE_CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result1, SUCCESS, SKIPPED, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, GRADLE_CLEAN_TASK_NAME);

        assertCleanTaskOutcomes(result2, UP_TO_DATE, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, SUCCESS, UP_TO_DATE);
    }
}
