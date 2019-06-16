package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.Helper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.Helper.runGradle;
import static org.siouan.frontendgradleplugin.util.Helper.runGradleAndExpectFailure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.util.Helper;

/**
 * Functional tests to verify the {@link NodeInstallTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead and because the 'node' and 'npm' executables are never called.
 */
class NodeInstallTaskFuncTest {

    @TempDir
    File tmpDirectory;

    private Path projectDirectory;

    @BeforeEach
    void setUp() {
        projectDirectory = tmpDirectory.toPath();
    }

    @Test
    void shouldFailInstallingNodeWhenVersionIsNotSet() throws IOException {
        Helper.createBuildFile(projectDirectory, Collections.emptyMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectory,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.FAILED);
    }

    @Test
    void shouldFailInstallingNodeWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        Helper.createBuildFile(projectDirectory, Collections.singletonMap("nodeVersion", "0.76.34"));

        final BuildResult result = runGradleAndExpectFailure(projectDirectory,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.FAILED);
    }

    @Test
    void shouldFailInstallingNodeWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", "protocol://domain/unknown");
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectory,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.FAILED);
    }

    @Test
    void shouldInstallNodeFirstAndNothingMoreSecondly() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip").toString());
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskOutcome(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
    }
}
