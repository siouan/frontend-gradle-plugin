package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_NODE_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.FAILED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link InstallNodeTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead and because the 'node' and 'npm' executables are never called.
 */
class InstallNodeTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_be_skipped_when_distribution_is_provided() throws IOException {
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().nodeDistributionProvided(true).toMap());

        final BuildResult result = runGradle(projectDirectoryPath, INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, INSTALL_NODE_TASK_NAME, SKIPPED);
    }

    @Test
    void should_fail_when_node_version_is_not_set() throws IOException {
        createBuildFile(projectDirectoryPath, Map.of());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, INSTALL_NODE_TASK_NAME, FAILED);
    }

    @Test
    void should_fail_when_distribution_cannot_be_downloaded_due_to_an_unknown_version() throws IOException {
        createBuildFile(projectDirectoryPath, Map.of("nodeVersion", "0.76.34"));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, INSTALL_NODE_TASK_NAME, FAILED);
    }

    @Test
    void should_fail_when_distribution_download_url_is_invalid() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrlRoot("protocol://domain/unknown");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, INSTALL_NODE_TASK_NAME, FAILED);
    }

    @Test
    void should_succeed_first_time_and_be_up_to_date_next_time() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.14.0")
            .nodeDistributionUrl(getResourcePath("node-v20.14.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result1, INSTALL_NODE_TASK_NAME, SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result2, INSTALL_NODE_TASK_NAME, UP_TO_DATE);
    }
}
