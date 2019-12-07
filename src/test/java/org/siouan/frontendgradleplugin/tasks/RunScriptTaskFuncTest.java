package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.Helper.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.util.Helper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.Helper.runGradle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
 * Functional tests to verify the {@link RunScriptTask} integration in a Gradle build. This functional test relies on
 * real Node/Yarn distributions.
 */
class RunScriptTaskFuncTest {

    @TempDir
    File tmpDirectory;

    private Path projectDirectory;

    @BeforeEach
    void setUp() {
        projectDirectory = tmpDirectory.toPath();
    }

    @Test
    void shouldRunScriptFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(new File(getClass().getClassLoader().getResource("package-npm.json").toURI()).toPath(),
            projectDirectory.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "12.13.1");
        final String customTaskName = "e2e";
        final StringBuilder customTaskDefinition = new StringBuilder("tasks.register('");
        customTaskDefinition.append(customTaskName);
        customTaskDefinition.append("', org.siouan.frontendgradleplugin.tasks.RunScriptTask) {\n");
        customTaskDefinition.append("dependsOn tasks.named('installFrontend')\n");
        customTaskDefinition.append("script = 'run another-script'\n");
        customTaskDefinition.append("}\n");
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result1 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskOutcome(result1, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, customTaskName, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskOutcome(result2, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result2, customTaskName, TaskOutcome.SUCCESS);

        Files.deleteIfExists(projectDirectory.resolve("package-lock.json"));
        Files.copy(new File(getClass().getClassLoader().getResource("package-yarn.json").toURI()).toPath(),
            projectDirectory.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.19.2");
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result3 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, customTaskName, TaskOutcome.SUCCESS);

        final BuildResult result4 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result4, customTaskName, TaskOutcome.SUCCESS);
    }
}
