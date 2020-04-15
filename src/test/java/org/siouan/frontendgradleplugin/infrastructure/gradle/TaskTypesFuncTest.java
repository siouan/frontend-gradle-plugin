package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;

import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

/**
 * Functional tests to verify the {@link RunNode} task type, the {@link RunNpx} task type, the {@link RunNpmYarn} task
 * type in a Gradle build. This functional test relies on a real Node distribution.
 */
class TaskTypesFuncTest {

    @TempDir
    Path temporaryDirectoryPath;

    private Path projectDirectoryPath;

    @BeforeEach
    void setUp() {
        projectDirectoryPath = temporaryDirectoryPath;
    }

    @Test
    void shouldFailRunningCustomNodeTaskWhenScriptIsUndefined() throws IOException {
        final String customTaskName = "myNodeTask";
        final String customTaskDefinition =
            "tasks.register('" + customTaskName + "', " + RunNode.class.getName() + ") {\n" + "}\n";
        createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, customTaskName);

        assertTaskFailed(result, customTaskName);
    }

    @Test
    void shouldFailRunningCustomNpxTaskWhenScriptIsUndefined() throws IOException {
        final String customTaskName = "myNpxTask";
        final String customTaskDefinition =
            "tasks.register('" + customTaskName + "', " + RunNpx.class.getName() + ") {\n" + "}\n";
        createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, customTaskName);

        assertTaskFailed(result, customTaskName);
    }

    @Test
    void shouldFailRunningCustomNpxTaskWhenYarnIsEnabled() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            projectDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "12.16.1");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("yarnEnabled", true);
        final String customTaskName = "myNpxTask";
        final String customTaskDefinition =
            "tasks.register('" + customTaskName + "', " + RunNpx.class.getName() + ") {\n"
                + "dependsOn tasks.named('installNode')\n" + "script = '--version'\n}\n";
        createBuildFile(projectDirectoryPath, properties, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, customTaskName);

        assertTaskFailed(result, customTaskName);
    }

    @Test
    void shouldFailRunningCustomNpmYarnTaskWhenScriptIsUndefined() throws IOException {
        final String customTaskName = "myNpmYarnTask";
        String customTaskDefinition = "import " + RunNpmYarn.class.getName() + '\n';
        customTaskDefinition +=
            "tasks.register('" + customTaskName + "', " + RunNpmYarn.class.getSimpleName() + ") {}\n";
        createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, customTaskName);

        assertTaskFailed(result, customTaskName);
    }

    @Test
    void shouldRunCustomTasks() throws IOException, URISyntaxException {
        final Path packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Path tmpScriptPath = temporaryDirectoryPath.resolve("script.js");
        createScriptFile(tmpScriptPath);
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "12.16.1");
        properties.put("nodeInstallDirectory", projectDirectoryPath.resolve("node-dist"));
        properties.put("packageJsonDirectory", packageJsonDirectoryPath);
        properties.put("loggingLevel", LogLevel.INFO);
        final String runNodeTaskName = "myNodeTask";
        final String runNodeTaskDefinition =
            "tasks.register('" + runNodeTaskName + "', " + RunNode.class.getName() + ") {\n"
                + "dependsOn tasks.named('installNode')\n" + "script = '" + tmpScriptPath
                .toString()
                .replace("\\", "\\\\") + "'\n}\n";
        final String runNpxTaskName = "myNpxTask";
        final String runNpxTaskDefinition =
            "tasks.register('" + runNpxTaskName + "', " + RunNpx.class.getName() + ") {\n"
                + "dependsOn tasks.named('installNode')\n" + "script = '--version'\n}\n";
        final String runNpmYarnTaskName = "myNpmYarnTask";
        final String runNpmYarnTaskDefinition =
            "tasks.register('" + runNpmYarnTaskName + "', " + RunNpmYarn.class.getName() + ") {\n"
                + "dependsOn tasks.named('installFrontend')\n" + "script = 'run another-script'\n" + "}\n";
        final String additionalContent =
            runNodeTaskDefinition + '\n' + runNpxTaskDefinition + '\n' + runNpmYarnTaskDefinition;
        createBuildFile(projectDirectoryPath, properties, additionalContent);

        final BuildResult result1 = runGradle(projectDirectoryPath, LogLevel.INFO, runNodeTaskName);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, runNodeTaskName);

        final BuildResult result2 = runGradle(projectDirectoryPath, LogLevel.INFO, runNodeTaskName);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, runNodeTaskName);

        final BuildResult result3 = runGradle(projectDirectoryPath, runNpxTaskName);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, runNpxTaskName);

        final BuildResult result4 = runGradle(projectDirectoryPath, runNpxTaskName);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, runNpxTaskName);

        final BuildResult result5 = runGradle(projectDirectoryPath, runNpmYarnTaskName);

        assertTaskUpToDate(result5, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result5, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result5, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result5, runNpmYarnTaskName);

        final BuildResult result6 = runGradle(projectDirectoryPath, runNpmYarnTaskName);

        assertTaskUpToDate(result6, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result6, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result6, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result6, runNpmYarnTaskName);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-yarn.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.22.4");
        properties.put("yarnInstallDirectory", projectDirectoryPath.resolve("yarn-dist"));
        createBuildFile(projectDirectoryPath, properties, additionalContent);

        final BuildResult result7 = runGradle(projectDirectoryPath, runNpmYarnTaskName);

        assertTaskUpToDate(result7, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result7, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result7, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result7, runNpmYarnTaskName);

        final BuildResult result8 = runGradle(projectDirectoryPath, runNpmYarnTaskName);

        assertTaskUpToDate(result8, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result8, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result8, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result8, runNpmYarnTaskName);
    }

    private void createScriptFile(final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log('Hello my friend!');\n");
        }
    }
}
