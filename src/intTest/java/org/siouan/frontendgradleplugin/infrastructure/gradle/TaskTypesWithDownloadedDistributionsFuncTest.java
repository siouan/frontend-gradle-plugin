package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNodeTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNpmTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNpxTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildYarnTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.createJavascriptFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

/**
 * Functional tests to verify task types {@link RunNode}, {@link RunNpm}, {@link RunNpx}, {@link RunYarn} in a Gradle
 * build, with a downloaded Node.js distribution.
 */
class TaskTypesWithDownloadedDistributionsFuncTest {

    private static final String RUN_NODE_TASK_NAME = "customNodeTask";

    private static final String RUN_NPM_TASK_NAME = "customNpmTask";

    private static final String RUN_NPX_TASK_NAME = "customNpxTask";

    private static final String RUN_YARN_TASK_NAME = "customYarnTask";

    @TempDir
    Path temporaryDirectoryPath;

    private Path projectDirectoryPath;

    @BeforeEach
    void setUp() {
        projectDirectoryPath = temporaryDirectoryPath;
    }

    @Test
    void shouldFailRunningCustomNodeTaskWhenScriptIsUndefined() throws IOException {
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME, null, null);
        createBuildFile(projectDirectoryPath, runNodeTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskFailed(result, RUN_NODE_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomNpmTaskWhenScriptIsUndefined() throws IOException {
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, runNpmTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskFailed(result, RUN_NPM_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomNpxTaskWhenScriptIsUndefined() throws IOException {
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, runNpxTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskFailed(result, RUN_NPX_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomNpmYarnTaskWhenScriptIsUndefined() throws IOException {
        final String customTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskFailed(result, RUN_YARN_TASK_NAME);
    }

    @Test
    void shouldRunCustomTasks() throws IOException {
        final Path packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final Path temporaryScriptPath = createJavascriptFile(temporaryDirectoryPath.resolve("script.js"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.0")
            .nodeInstallDirectory(projectDirectoryPath.resolve("node-dist"))
            .packageJsonDirectory(packageJsonDirectoryPath)
            .verboseModeEnabled(false);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, "run another-script");
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, "--version");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME),
            "run another-script");
        final String additionalContent = String.join("\n", runNodeTaskDefinition, runNpmTaskDefinition,
            runNpxTaskDefinition, runYarnTaskDefinition);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult result1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result1, RUN_YARN_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result2, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result2, RUN_YARN_TASK_NAME);

        final BuildResult result3 = runGradle(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result3, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result3, RUN_NPM_TASK_NAME);
        assertTaskSuccess(result3, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result3, RUN_YARN_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result4, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPM_TASK_NAME);
        assertTaskSuccess(result4, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result4, RUN_YARN_TASK_NAME);

        final BuildResult result5 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskUpToDate(result5, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result5, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result5, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result5, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result5, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result5, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result5, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result5, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result5, RUN_YARN_TASK_NAME);

        final BuildResult result6 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskUpToDate(result6, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result6, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result6, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result6, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result6, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result6, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result6, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result6, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result6, RUN_YARN_TASK_NAME);

        final BuildResult result7 = runGradleAndExpectFailure(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskUpToDate(result7, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result7, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result7, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result7, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result7, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result7, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result7, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result7, RUN_NPX_TASK_NAME);
        assertTaskFailed(result7, RUN_YARN_TASK_NAME);

        Files.deleteIfExists(packageJsonDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder.yarnEnabled(true).yarnVersion("3.0.0").verboseModeEnabled(false);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult result8 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskUpToDate(result8, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskIgnored(result8, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result8, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result8, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskIgnored(result8, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result8, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result8, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result8, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result8, RUN_YARN_TASK_NAME);

        final BuildResult result9 = runGradle(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskUpToDate(result9, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskIgnored(result9, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result9, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result9, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskIgnored(result9, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result9, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result9, RUN_NPM_TASK_NAME);
        assertTaskSuccess(result9, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result9, RUN_YARN_TASK_NAME);

        final BuildResult result10 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskUpToDate(result10, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSuccess(result10, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result10, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result10, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result10, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result10, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result10, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result10, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result10, RUN_YARN_TASK_NAME);

        final BuildResult result11 = runGradle(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskUpToDate(result11, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSuccess(result11, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result11, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result11, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result11, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result11, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result11, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result11, RUN_NPX_TASK_NAME);
        assertTaskSuccess(result11, RUN_YARN_TASK_NAME);
    }
}
