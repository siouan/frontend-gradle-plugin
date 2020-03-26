package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskFailed;
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
 * Functional tests to verify the {@link NodeInstallTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead and because the 'node' and 'npm' executables are never called.
 */
class NodeInstallTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void shouldFailInstallingNodeWhenVersionIsNotSet() throws IOException {
        Helper.createBuildFile(projectDirectoryPath, Collections.emptyMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingNodeWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        Helper.createBuildFile(projectDirectoryPath, Collections.singletonMap("nodeVersion", "0.76.34"));

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingNodeWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", "protocol://domain/unknown");
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldInstallNodeFirstAndNothingMoreSecondly() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }
}
