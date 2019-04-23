package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.runGradle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.util.FunctionalTestHelper;

/**
 * Functional tests to verify the {@link CleanTask} integration in a Gradle build.
 */
public class CleanTaskFuncTest {

    private static final String GRADLE_CLEAN_TASK = "clean";

    @TempDir
    protected File projectDirectory;

    @Test
    public void shouldCleanFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(new File(getClass().getClassLoader().getResource("package-npm.json").toURI()).toPath(),
            projectDirectory.toPath().resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.15.3");
        properties.put("cleanScript", "run clean");
        FunctionalTestHelper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, GRADLE_CLEAN_TASK);

        assertTaskOutcome(result1, NodeInstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, YarnInstallTask.DEFAULT_NAME, TaskOutcome.SKIPPED);
        assertTaskOutcome(result1, InstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, GRADLE_CLEAN_TASK, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, GRADLE_CLEAN_TASK);

        assertTaskOutcome(result2, NodeInstallTask.DEFAULT_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result2, YarnInstallTask.DEFAULT_NAME, TaskOutcome.SKIPPED);
        assertTaskOutcome(result2, InstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result2, GRADLE_CLEAN_TASK, TaskOutcome.UP_TO_DATE);

        Files.delete(projectDirectory.toPath().resolve("package-lock.json"));
        Files.copy(new File(getClass().getClassLoader().getResource("package-yarn.json").toURI()).toPath(),
            projectDirectory.toPath().resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.15.2");
        FunctionalTestHelper.createBuildFile(projectDirectory, properties);

        final BuildResult result3 = runGradle(projectDirectory, GRADLE_CLEAN_TASK);

        assertTaskOutcome(result3, NodeInstallTask.DEFAULT_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result3, YarnInstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, InstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, GRADLE_CLEAN_TASK, TaskOutcome.SUCCESS);

        final BuildResult result4 = runGradle(projectDirectory, GRADLE_CLEAN_TASK);

        assertTaskOutcome(result4, NodeInstallTask.DEFAULT_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, YarnInstallTask.DEFAULT_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, InstallTask.DEFAULT_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result4, GRADLE_CLEAN_TASK, TaskOutcome.UP_TO_DATE);
    }
}
