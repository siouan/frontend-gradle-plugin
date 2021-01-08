package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.Arrays.asList;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link RunNode} task type, the {@link RunNpx} task type, the {@link RunNpmYarn} task
 * type in a Gradle build, with preinstalled distributions of Node.js and Yarn. Test cases uses fake Node.js/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 */
class TaskTypesWithProvidedDistributionsFuncTest {

    private static final String RUN_NODE_TASK_NAME = "myRunNodeTask";

    private static final String RUN_NPM_YARN_TASK_NAME = "myRunNpmYarnTask";

    private static final String RUN_NPX_TASK_NAME = "myRunNpxTask";

    @TempDir
    Path temporaryDirectoryPath;

    private Path temporaryScriptPath;

    private Path projectDirectoryPath;

    @BeforeEach
    void setUp() {
        projectDirectoryPath = temporaryDirectoryPath;
        temporaryScriptPath = temporaryDirectoryPath.resolve("script.js");
    }

    @Test
    void shouldFailRunningCustomTasksWhenNodeExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-without-node"));
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "--version");
        final String runNpmYarnTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run another-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result1, RUN_NODE_TASK_NAME);

        final BuildResult result2 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result2, RUN_NPX_TASK_NAME);

        final BuildResult result3 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result3, RUN_NPM_YARN_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomTasksWhenNpxExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-without-npx"));
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "--version");
        final String runNpmYarnTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run another-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);

        final BuildResult result2 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result2, RUN_NPX_TASK_NAME);

        final BuildResult result3 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, RUN_NPM_YARN_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomTasksWhenNpmExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-without-npm"));
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "--version");
        final String runNpmYarnTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "run another-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);

        final BuildResult result2 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        // Failure because npx requires npm.
        assertTaskFailed(result2, RUN_NPX_TASK_NAME);

        final BuildResult result3 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result3, RUN_NPM_YARN_TASK_NAME);
    }

    @Test
    void shouldFailRunningCustomTasksWhenYarnExecutableDoesNotExist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("12.18.3")
            .nodeDistributionUrl(getResourceUrl("node-v12.18.3.zip"))
            .yarnEnabled(true)
            .yarnDistributionProvided(true)
            .yarnInstallDirectory(Files.createDirectory(projectDirectoryPath.resolve("yarn-dist-provided")));
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmYarnTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME,
            asList(FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME),
            "run another-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);

        final BuildResult result2 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskFailed(result2, RUN_NPM_YARN_TASK_NAME);
    }

    @Test
    void shouldRunCustomTasks() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(getResourcePath("node-dist-provided"));
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpxTaskDefinition = buildNpxTaskDefinition(RUN_NPX_TASK_NAME,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, "--version");
        final String runNpmYarnTaskDefinition = buildNpmYarnTaskDefinition(RUN_NPM_YARN_TASK_NAME,
            FrontendGradlePlugin.INSTALL_TASK_NAME, "run another-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, RUN_NODE_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, RUN_NPX_TASK_NAME);

        final BuildResult result3 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskSkipped(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result3, RUN_NPM_YARN_TASK_NAME);

        frontendMapBuilder
            .yarnEnabled(true)
            .yarnDistributionProvided(true)
            .yarnInstallDirectory(getResourcePath("yarn-dist-provided"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(),
            String.join("\n", runNodeTaskDefinition, runNpxTaskDefinition, runNpmYarnTaskDefinition));

        final BuildResult result4 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskSkipped(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, RUN_NODE_TASK_NAME);

        final BuildResult result5 = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPX_TASK_NAME);

        assertTaskSkipped(result5, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskFailed(result5, RUN_NPX_TASK_NAME);

        final BuildResult result6 = runGradle(projectDirectoryPath, RUN_NPM_YARN_TASK_NAME);

        assertTaskSkipped(result6, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result6, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result6, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result6, RUN_NPM_YARN_TASK_NAME);
    }
}
