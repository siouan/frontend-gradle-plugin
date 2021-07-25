package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskUpToDate;
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

/**
 * Functional tests to verify the {@link NodeInstallTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead and because the 'node' and 'npm' executables are never called.
 */
class NodeInstallTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void shouldBeSkippedWhenDistributionIsProvided() throws IOException {
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().nodeDistributionProvided(true).toMap());

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSkipped(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingNodeWhenVersionIsNotSet() throws IOException {
        createBuildFile(projectDirectoryPath, emptyMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        createBuildFile(projectDirectoryPath, singletonMap("nodeVersion", "0.76.34"));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.3")
            .nodeDistributionUrlRoot("protocol://domain/unknown");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldSucceedFirstTimeAndBeUpToDateSecondTime() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.3")
            .nodeDistributionUrl(getResourceUrl("node-v14.17.3.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }
}
