package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.util.PluginTaskOutcome;

/**
 * Functional tests to verify the {@link NodeInstallTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead and because the 'node' and 'npm' executables are never called.
 */
class NodeInstallTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_be_skipped_when_distribution_is_provided() throws IOException {
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().nodeDistributionProvided(true).toMap());

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, PluginTaskOutcome.SKIPPED);
    }

    @Test
    void should_fail_when_node_version_is_not_set() throws IOException {
        createBuildFile(projectDirectoryPath, emptyMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_fail_when_distribution_cannot_be_downloaded_due_to_an_unknown_version() throws IOException {
        createBuildFile(projectDirectoryPath, singletonMap("nodeVersion", "0.76.34"));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_fail_when_distribution_download_url_is_invalid() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrlRoot("protocol://domain/unknown");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_succeed_first_time_and_be_up_to_date_next_time() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v18.16.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, PluginTaskOutcome.UP_TO_DATE);
    }
}
