package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.runGradle;
import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.runGradleAndExpectFailure;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.util.FunctionalTestHelper;

/**
 * Functional tests to verify the {@link YarnInstallTask} integration in a Gradle build.
 */
class YarnInstallTaskFuncTest {

    private static final String GRADLE_YARN_INSTALL_TASK = "installYarn";

    @TempDir
    protected File projectDirectory;

    @Test
    public void shouldSkipInstallWhenYarnIsNotEnabled() throws IOException {
        FunctionalTestHelper.createBuildFile(projectDirectory, Collections.emptyMap());

        final BuildResult result = runGradle(projectDirectory, GRADLE_YARN_INSTALL_TASK);

        assertTaskOutcome(result, YarnInstallTask.DEFAULT_NAME, TaskOutcome.SKIPPED);
    }

    @Test
    public void shouldFailInstallingYarnWhenVersionIsNotSet() throws IOException {
        FunctionalTestHelper.createBuildFile(projectDirectory, Collections.singletonMap("yarnEnabled", true));

        final BuildResult result = runGradleAndExpectFailure(projectDirectory, GRADLE_YARN_INSTALL_TASK);

        assertTaskOutcome(result, YarnInstallTask.DEFAULT_NAME, TaskOutcome.FAILED);
    }

    @Test
    public void shouldFailInstallingYarnWhenDistributionCannotBeDownloadedWithUnknownVersion() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "0.56.3");
        FunctionalTestHelper.createBuildFile(projectDirectory, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectory, GRADLE_YARN_INSTALL_TASK);

        assertTaskOutcome(result, YarnInstallTask.DEFAULT_NAME, TaskOutcome.FAILED);
    }

    @Test
    public void shouldFailInstallingYarnWhenDistributionCannotBeDownloadedWithInvalidUrl() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.15.2");
        properties.put("yarnDistributionUrl", "protocol://domain/unknown");
        FunctionalTestHelper.createBuildFile(projectDirectory, properties);

        final BuildResult result = runGradleAndExpectFailure(projectDirectory, GRADLE_YARN_INSTALL_TASK);

        assertTaskOutcome(result, YarnInstallTask.DEFAULT_NAME, TaskOutcome.FAILED);
    }

    @Test
    public void shouldInstallYarnFirstAndNothingMoreSecondly() throws IOException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.15.2");
        properties
            .put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.15.2.tar.gz").toString());
        FunctionalTestHelper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, GRADLE_YARN_INSTALL_TASK);

        assertTaskOutcome(result1, YarnInstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, GRADLE_YARN_INSTALL_TASK);

        assertTaskOutcome(result2, YarnInstallTask.DEFAULT_NAME, TaskOutcome.UP_TO_DATE);
    }
}
