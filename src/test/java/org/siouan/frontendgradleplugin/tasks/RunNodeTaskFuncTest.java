package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.Helper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.util.Helper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.util.Helper.runGradle;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.util.Helper;

/**
 * Functional tests to verify the {@link RunNodeTask} integration in a Gradle build. This functional test relies on a
 * real Node distribution.
 *
 * @since 1.2.0
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
        properties.put("nodeVersion", "12.13.1");
        final String customTaskName = "helloMyFriend";
        final String customTaskDefinition = "tasks.register('"
            + customTaskName
            + "', org.siouan.frontendgradleplugin.tasks.RunNodeTask) {\n"
            + "dependsOn tasks.named('installNode')\n"
            + "script = '"
            + tmpScriptPath.toString().replaceAll("\\\\", "\\\\\\\\")
            + "'\n"
            + "}\n";
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition);

        final BuildResult result1 = runGradle(projectDirectory, customTaskName);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, customTaskName);

        final BuildResult result2 = runGradle(projectDirectory, customTaskName);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, customTaskName);
    }

    private void createScriptFile(final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log('Hello my friend!');\n");
        }
    }
}
