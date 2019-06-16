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
 * Functional tests to verify the {@link YarnInstallTask} integration in a Gradle build. Test cases uses a fake Yarn
 * distribution, to avoid the download overhead and because the 'yarn' executable is never called.
 */
class YarnInstallTaskFuncTest {

    @TempDir
    File tmpDirectory;

    private Path projectDirectory;

    @BeforeEach
    void setUp() {
        projectDirectory = tmpDirectory.toPath();
    }

    @Test
    void shouldSkipInstallWhenYarnIsNotEnabled() throws IOException {
        Helper.createBuildFile(projectDirectory, Collections.emptyMap());

        final BuildResult result = runGradle(projectDirectory, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SKIPPED);
    }

    @Test
    void shouldFailInstallingYarnWhenVersionIsNotSet() throws IOException {
        Helper.createBuildFile(projectDirectory, Collections.singletonMap("yarnEnabled", true));

        final BuildResult result = runGradleAndExpectFailure(projectDirectory,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.FAILED);
    }

    @Test
    void shouldFailInstallingYarnWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "0.56.3");
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectory,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.FAILED);
    }

    @Test
    void shouldFailInstallingYarnWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.15.2");
        properties.put("yarnDistributionUrl", "protocol://domain/unknown");
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectory,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.FAILED);
    }

    @Test
    void shouldInstallYarnFirstAndNothingMoreSecondly() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.15.2");
        properties
            .put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.15.2.tar.gz").toString());
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskOutcome(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskOutcome(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
    }
}
