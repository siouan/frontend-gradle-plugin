package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.Helper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.Helper.runGradleAndExpectFailure;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.Helper;

/**
 * Functional tests to verify the {@link YarnInstallTask} integration in a Gradle build. Test cases uses a fake Yarn
 * distribution, to avoid the download overhead and because the 'yarn' executable is never called.
 */
class YarnInstallTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void shouldSkipInstallWhenYarnIsNotEnabled() throws IOException {
        Helper.createBuildFile(projectDirectoryPath, Collections.emptyMap());

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSkipped(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenVersionIsNotSet() throws IOException {
        Helper.createBuildFile(projectDirectoryPath, Collections.singletonMap("yarnEnabled", true));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "0.56.3");
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", "protocol://domain/unknown");
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldInstallYarnFirstAndNothingMoreSecondly() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }
}
