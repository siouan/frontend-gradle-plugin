package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.Collections.emptyMap;
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
 * Functional tests to verify the {@link YarnInstallTask} integration in a Gradle build. Test cases uses a fake Yarn
 * distribution, to avoid the download overhead and because the 'yarn' executable is never called.
 */
class YarnInstallTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void shouldBeSkippedWhenYarnIsNotEnabled() throws IOException {
        createBuildFile(projectDirectoryPath, emptyMap());

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSkipped(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldBeSkippedWhenDistributionIsProvided() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .yarnEnabled(true)
            .yarnDistributionProvided(true);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSkipped(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenVersionIsNotSet() throws IOException {
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().yarnEnabled(true).toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().yarnEnabled(true).yarnVersion("0.56.3");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .yarnEnabled(true)
            .yarnVersion("1.22.4")
            .yarnDistributionUrlPattern("protocol://domain/unknown");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldSucceedFirstTimeAndBeUpToDateSecondTime() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .yarnEnabled(true)
            .yarnVersion("1.22.4")
            .yarnDistributionUrlPattern(getResourceUrl("yarn-v1.22.4.tar.gz"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }
}
