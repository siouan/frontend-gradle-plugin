package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourceUrl;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNodeTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNpmYarnTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNpxTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.createJavascriptFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link RunNode} task type, the {@link RunNpx} task type, the {@link RunNpmYarn} task
 * type in a Gradle build. This functional test relies on real Node.js and Yarn distributions.
 */
class TaskTypesWithDownloadedDistributionsFuncTest {

    private static final String RUN_NODE_TASK_NAME = "myRunNodeTask";

    private static final String RUN_NPM_YARN_TASK_NAME = "myRunNpmYarnTask";

    private static final String RUN_NPX_TASK_NAME = "myRunNpxTask";

    @TempDir
    Path temporaryDirectoryPath;

    private Path projectDirectoryPath;

    @BeforeEach
    void setUp() throws IOException {
        projectDirectoryPath = Files.createDirectory(temporaryDirectoryPath.resolve("project directory with spaces"));
    }

    @Test
    void shouldFailRunningCustomNodeTaskWhenScriptIsUndefined() throws IOException {
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME, null, null);
        createBuildFile(projectDirectoryPath, runNodeTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskFailed(result, RUN_NODE_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomNpxTaskWhenScriptIsUndefined() throws IOException {
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, runNpxTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskFailed(result, RUN_NPX_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomNpxTaskWhenYarnIsEnabled() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("12.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v14.16.1.zip"))
            .yarnEnabled(true);
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "--version");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), runNpxTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskFailed(result, RUN_NPX_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomNpmYarnTaskWhenScriptIsUndefined() throws IOException {
        final String customTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskFailed(result, RUN_NPM_YARN_TASK_NAME);
    }

    @Test
    void shouldRunCustomTasks() throws IOException {
        final Path packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final Path temporaryScriptPath = createJavascriptFile(temporaryDirectoryPath.resolve("script.js"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.16.1")
            .nodeInstallDirectory(projectDirectoryPath.resolve("node-dist"))
            .packageJsonDirectory(packageJsonDirectoryPath);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "--version");
        final String runNpmYarnTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME,
            FrontendGradlePlugin.INSTALL_TASK_NAME, "run another-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result1 = runGradle(projectDirectoryPath, LogLevel.LIFECYCLE, RUN_NODE_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, LogLevel.LIFECYCLE, RUN_NODE_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, RUN_NODE_TASK_NAME);

        final BuildResult result3 = runGradle(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, RUN_NPX_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, RUN_NPX_TASK_NAME);

        final BuildResult result5 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskUpToDate(result5, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result5, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result5, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result5, RUN_NPM_YARN_TASK_NAME);

        final BuildResult result6 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskUpToDate(result6, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result6, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result6, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result6, RUN_NPM_YARN_TASK_NAME);

        Files.deleteIfExists(packageJsonDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder
            .yarnEnabled(true)
            .yarnVersion("1.22.10")
            .yarnInstallDirectory(projectDirectoryPath.resolve("yarn-dist"))
            .verboseModeEnabled(false);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result7 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskUpToDate(result7, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result7, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result7, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result7, RUN_NPM_YARN_TASK_NAME);

        final BuildResult result8 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskUpToDate(result8, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result8, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result8, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result8, RUN_NPM_YARN_TASK_NAME);
    }
}
