package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.Helper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.Helper.runGradle;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * Functional tests to verify the {@link RunNodeTask} integration in a Gradle build. This functional test relies on a
 * real Node distribution.
 */
class RunNodeTaskFuncTest {

    @TempDir
    File tmpDirectory;

    private Path projectDirectory;

    @BeforeEach
    void setUp() {
        projectDirectory = tmpDirectory.toPath();
    }

    @Test
    void shouldRunScriptFrontendWithNode() throws IOException {
        final Path tmpScriptPath = tmpDirectory.toPath().resolve("script.js");
        createScriptFile(tmpScriptPath);
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        final String customTaskName = "helloMyFriend";
        final StringBuilder customTaskDefinition = new StringBuilder("tasks.register('");
        customTaskDefinition.append(customTaskName);
        customTaskDefinition.append("', org.siouan.frontendgradleplugin.tasks.RunNodeTask) {\n");
        customTaskDefinition.append("dependsOn tasks.named('installNode')\n");
        customTaskDefinition.append("script = '");
        customTaskDefinition.append(tmpScriptPath.toString().replaceAll("\\\\", "\\\\\\\\"));
        customTaskDefinition.append("'\n");
        customTaskDefinition.append("}\n");
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result1 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, customTaskName, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result2, customTaskName, TaskOutcome.SUCCESS);
    }

    private void createScriptFile(final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log('Hello my friend!');\n");
        }
    }
}
