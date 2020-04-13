package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

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
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnDistributionProvided", true);
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSkipped(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenVersionIsNotSet() throws IOException {
        createBuildFile(projectDirectoryPath, singletonMap("yarnEnabled", true));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "0.56.3");
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", "protocol://domain/unknown");
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldSucceedFirstTimeAndBeUpToDateSecondTime() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz"));
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }
}
