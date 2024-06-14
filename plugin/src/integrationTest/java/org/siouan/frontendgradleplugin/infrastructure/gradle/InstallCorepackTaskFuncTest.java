package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_COREPACK_TASK_NAME;
import static org.siouan.frontendgradleplugin.infrastructure.gradle.InstallCorepackTask.LATEST_VERSION_ARGUMENT;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.FAILED;
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
 * Functional tests to verify the {@link InstallCorepackTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead. All executables in these distributions simply call the 'node'
 * executable with the same arguments.
 */
class InstallCorepackTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_skip_task_when_corepack_version_property_is_null() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradle(projectDirectoryPath, INSTALL_COREPACK_TASK_NAME);

        assertTaskOutcomes(result, SUCCESS, SKIPPED);
    }

    @Test
    void should_fail_when_node_install_directory_is_not_a_directory() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath,
            new FrontendMapBuilder().nodeDistributionProvided(true).corepackVersion(LATEST_VERSION_ARGUMENT).toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, INSTALL_COREPACK_TASK_NAME);

        assertTaskOutcomes(result, SKIPPED, FAILED);
    }

    @Test
    void should_install_corepack() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .corepackVersion(LATEST_VERSION_ARGUMENT)
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, INSTALL_COREPACK_TASK_NAME);

        assertTaskOutcomes(result1, SUCCESS, SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, INSTALL_COREPACK_TASK_NAME);

        assertTaskOutcomes(result2, UP_TO_DATE, UP_TO_DATE);
    }
}
