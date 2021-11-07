package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNodeTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNpmTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildNpxTaskDefinition;
import static org.siouan.frontendgradleplugin.test.util.TaskTypes.buildYarnTaskDefinition;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.domain.usecase.GetNodeExecutablePath;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

/**
 * Functional tests to verify task types {@link RunNode}, {@link RunNpm}, {@link RunNpx}, {@link RunYarn} in a Gradle
 * build, with a Node.js distribution resolved with the {@code PATH} environment variable. Test cases uses a fake
 * Node.js distribution, to avoid the download overhead. The 'npm' and 'npx' executables in these distributions simply
 * call the 'node' executable with the same arguments.
 */
class TaskTypesWithDistributionsInPathFuncTest {

    private static final String RUN_NODE_TASK_NAME = "customNodeTask";

    private static final String RUN_NPM_TASK_NAME = "customNpmTask";

    private static final String RUN_NPX_TASK_NAME = "customNpxTask";

    private static final String RUN_YARN_TASK_NAME = "customYarnTask";

    @TempDir
    Path temporaryDirectoryPath;

    private Path projectDirectoryPath;

    private Path temporaryScriptPath;

    @BeforeEach
    void setUp() {
        projectDirectoryPath = temporaryDirectoryPath;
        temporaryScriptPath = temporaryDirectoryPath.resolve("script.js");
    }

    @Test
    void shouldFailRunningCustomTasksWhenNodeExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .yarnEnabled(true)
            .yarnVersion("3.0.0")
            .verboseModeEnabled(false);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run npm-script");
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "npx-library");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME),
            "run yarn-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmTaskDefinition,
                runYarnTaskDefinition));
        final Path nodeExecutablePath = getResourcePath("node-dist-without-node")
            .resolve(PlatformFixture.LOCAL_PLATFORM.isWindowsOs() ? GetNodeExecutablePath.WINDOWS_EXECUTABLE_FILE_PATH
                : GetNodeExecutablePath.NON_WINDOWS_EXECUTABLE_FILE_PATH);

        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskFailed(result1, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result1, RUN_YARN_TASK_NAME);

        final BuildResult result2 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_NPM_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, RUN_NODE_TASK_NAME);
        assertTaskFailed(result2, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result2, RUN_YARN_TASK_NAME);

        final BuildResult result3 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_NPX_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result3, RUN_NPM_TASK_NAME);
        assertTaskFailed(result3, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result3, RUN_YARN_TASK_NAME);

        final BuildResult result4 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_YARN_TASK_NAME);

        assertTaskSkipped(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result4, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result4, RUN_YARN_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomTasksWhenNpmExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .yarnEnabled(true)
            .yarnVersion("3.0.0")
            .verboseModeEnabled(true);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run npm-script");
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "npx-library");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME),
            "run yarn-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmTaskDefinition,
                runYarnTaskDefinition));
        final Path nodeExecutablePath = getResourcePath("node-dist-without-npm")
            .resolve(PlatformFixture.LOCAL_PLATFORM.isWindowsOs() ? GetNodeExecutablePath.WINDOWS_EXECUTABLE_FILE_PATH
                : GetNodeExecutablePath.NON_WINDOWS_EXECUTABLE_FILE_PATH);

        final BuildResult result1 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result1, RUN_YARN_TASK_NAME);

        final BuildResult result2 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_NPM_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, RUN_NODE_TASK_NAME);
        assertTaskFailed(result2, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result2, RUN_YARN_TASK_NAME);

        final BuildResult result3 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_NPX_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result3, RUN_NPM_TASK_NAME);
        assertTaskFailed(result3, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result3, RUN_YARN_TASK_NAME);

        final BuildResult result4 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_YARN_TASK_NAME);

        assertTaskSkipped(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result4, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result4, RUN_YARN_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomTasksWhenNpxExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .yarnEnabled(true)
            .yarnVersion("3.0.0")
            .verboseModeEnabled(false);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run npm-script");
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "npx-library");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME),
            "run yarn-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmTaskDefinition,
                runYarnTaskDefinition));
        final Path nodeExecutablePath = getResourcePath("node-dist-without-npx")
            .resolve(PlatformFixture.LOCAL_PLATFORM.isWindowsOs() ? GetNodeExecutablePath.WINDOWS_EXECUTABLE_FILE_PATH
                : GetNodeExecutablePath.NON_WINDOWS_EXECUTABLE_FILE_PATH);

        final BuildResult result1 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result1, RUN_YARN_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPM_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result2, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result2, RUN_YARN_TASK_NAME);

        final BuildResult result3 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_NPX_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result3, RUN_NPM_TASK_NAME);
        assertTaskFailed(result3, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result3, RUN_YARN_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_YARN_TASK_NAME);

        assertTaskSkipped(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result4, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPX_TASK_NAME);
        assertTaskSuccess(result4, RUN_YARN_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomTasksWhenYarnExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .yarnEnabled(true)
            .yarnVersion("3.0.0")
            .verboseModeEnabled(false);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run npm-script");
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "npx-library");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME),
            "run yarn-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmTaskDefinition,
                runYarnTaskDefinition));
        final Path nodeExecutablePath = getResourcePath("node-dist-without-yarn")
            .resolve(PlatformFixture.LOCAL_PLATFORM.isWindowsOs() ? GetNodeExecutablePath.WINDOWS_EXECUTABLE_FILE_PATH
                : GetNodeExecutablePath.NON_WINDOWS_EXECUTABLE_FILE_PATH);

        final BuildResult result1 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result1, RUN_YARN_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPM_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result2, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result2, RUN_YARN_TASK_NAME);

        final BuildResult result3 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result3, RUN_NPM_TASK_NAME);
        assertTaskSuccess(result3, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result3, RUN_YARN_TASK_NAME);

        final BuildResult result4 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_YARN_TASK_NAME);

        assertTaskSkipped(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskFailed(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result4, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result4, RUN_YARN_TASK_NAME);
    }

    @Test
    void shouldRunCustomTasks() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .verboseModeEnabled(false);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run npm-script");
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "npx-library");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME),
            "run yarn-script");
        final String additionalContent = String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition,
            runNpmTaskDefinition, runYarnTaskDefinition);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);
        final Path nodeExecutablePath = getResourcePath("node-dist-provided")
            .resolve(PlatformFixture.LOCAL_PLATFORM.isWindowsOs() ? GetNodeExecutablePath.WINDOWS_EXECUTABLE_FILE_PATH
                : GetNodeExecutablePath.NON_WINDOWS_EXECUTABLE_FILE_PATH);

        final BuildResult result1 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result1, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result1, RUN_YARN_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result2, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result2, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result2, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result2, RUN_YARN_TASK_NAME);

        final BuildResult result3 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPM_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result3, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result3, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result3, RUN_NPX_TASK_NAME);
        assertTaskSuccess(result3, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result3, RUN_YARN_TASK_NAME);

        final BuildResult result4 = runGradleAndExpectFailure(projectDirectoryPath, null, nodeExecutablePath,
            RUN_YARN_TASK_NAME);

        assertTaskSkipped(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result4, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result4, RUN_NPM_TASK_NAME);
        assertTaskFailed(result4, RUN_YARN_TASK_NAME);

        frontendMapBuilder.yarnEnabled(true).yarnVersion("3.0.0");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult result5 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result5, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result5, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result5, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result5, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result5, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result5, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result5, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result5, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result5, RUN_YARN_TASK_NAME);

        final BuildResult result6 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result6, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result6, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result6, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result6, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result6, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result6, RUN_NODE_TASK_NAME);
        assertTaskSuccess(result6, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result6, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result6, RUN_YARN_TASK_NAME);

        final BuildResult result7 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_NPM_TASK_NAME);

        assertTaskSkipped(result7, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result7, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result7, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result7, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result7, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result7, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result7, RUN_NPX_TASK_NAME);
        assertTaskSuccess(result7, RUN_NPM_TASK_NAME);
        assertTaskIgnored(result7, RUN_YARN_TASK_NAME);

        final BuildResult result8 = runGradle(projectDirectoryPath, null, nodeExecutablePath, RUN_YARN_TASK_NAME);

        assertTaskSkipped(result8, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result8, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result8, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result8, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result8, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskIgnored(result8, RUN_NODE_TASK_NAME);
        assertTaskIgnored(result8, RUN_NPX_TASK_NAME);
        assertTaskIgnored(result8, RUN_NPM_TASK_NAME);
        assertTaskSuccess(result8, RUN_YARN_TASK_NAME);
    }
}
