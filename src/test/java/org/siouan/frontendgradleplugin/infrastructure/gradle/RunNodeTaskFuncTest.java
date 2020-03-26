package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.Helper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.Helper.runGradleAndExpectFailure;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.Helper;

/**
 * Functional tests to verify the {@link RunNodeTask} integration in a Gradle build. This functional test relies on a
 * real Node distribution.
 *
 * @since 1.2.0
 */
class RunNodeTaskFuncTest {

    @TempDir
    Path temporaryDirectoryPath;

    private Path projectDirectoryPath;

    @BeforeEach
    void setUp() {
        projectDirectoryPath = temporaryDirectoryPath;
    }

    @Test
    void shouldFailRunningNodeScriptWhenScriptIsUndefined() throws IOException {
        final String customTaskName = "helloMyFriend";
        final String customTaskDefinition =
            "tasks.register('" + customTaskName + "', " + RunNodeTask.class.getName() + ") {\n" + "}\n";
        Helper.createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, customTaskName);

        assertTaskFailed(result, customTaskName);
    }

    @Test
    void shouldRunScriptFrontendWithNode() throws IOException {
        final Path tmpScriptPath = temporaryDirectoryPath.resolve("script.js");
        createScriptFile(tmpScriptPath);
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "12.16.1");
        final String customTaskName = "helloMyFriend";
        final String customTaskDefinition =
            "tasks.register('" + customTaskName + "', " + RunNodeTask.class.getName() + ") {\n"
                + "dependsOn tasks.named('installNode')\n" + "script = '" + tmpScriptPath
                .toString()
                .replaceAll("\\\\", "\\\\\\\\") + "'\n" + "}\n";
        Helper.createBuildFile(projectDirectoryPath, properties, customTaskDefinition);

        final BuildResult result1 = runGradle(projectDirectoryPath, customTaskName, LogLevel.INFO);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, customTaskName);

        final BuildResult result2 = runGradle(projectDirectoryPath, customTaskName, LogLevel.INFO);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, customTaskName);
    }

    private void createScriptFile(final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log('Hello my friend!');\n");
        }
    }
}
