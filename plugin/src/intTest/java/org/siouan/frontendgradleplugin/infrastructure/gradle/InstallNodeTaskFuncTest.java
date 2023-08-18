package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;

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

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.SKIPPED);
    }

    @Test
    void should_fail_when_node_version_is_not_set() throws IOException {
        createBuildFile(projectDirectoryPath, Map.of());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_fail_when_distribution_cannot_be_downloaded_due_to_an_unknown_version() throws IOException {
        createBuildFile(projectDirectoryPath, Map.of("nodeVersion", "0.76.34"));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_fail_when_distribution_download_url_is_invalid() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrlRoot("protocol://domain/unknown");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_succeed_first_time_and_be_up_to_date_next_time() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result1, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result2, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.UP_TO_DATE);
    }
}
