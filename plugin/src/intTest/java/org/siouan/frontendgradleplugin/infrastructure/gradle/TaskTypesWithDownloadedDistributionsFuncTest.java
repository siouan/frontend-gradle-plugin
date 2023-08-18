package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildCorepackTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildNodeTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildNpmTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildPnpmTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildYarnTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.createJavascriptFile;

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
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;

/**
 * Functional tests to verify task types {@link RunNode}, {@link RunNpm}, {@link RunPnpm},  {@link RunYarn} in a Gradle
 * build, with a downloaded Node.js distribution.
 */
class TaskTypesWithDownloadedDistributionsFuncTest {

    private static final String RUN_COREPACK_TASK_NAME = "customCorepackTask";

    private static final String RUN_NODE_TASK_NAME = "customNodeTask";

    private static final String RUN_NPM_TASK_NAME = "customNpmTask";

    private static final String RUN_PNPM_TASK_NAME = "customPnpmTask";

    private static final String RUN_YARN_TASK_NAME = "customYarnTask";

    @TempDir
    Path temporaryDirectoryPath;

    private Path projectDirectoryPath;

    @BeforeEach
    void setUp() {
        projectDirectoryPath = temporaryDirectoryPath;
    }

    @Test
    void should_fail_running_custom_corepack_task_when_script_is_undefined() throws IOException {
        final String runCorepackTaskDefinition = buildCorepackTaskDefinition(RUN_COREPACK_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, runCorepackTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskFailed(result, RUN_COREPACK_TASK_NAME);
    }

    @Test
    void should_fail_running_custom_node_task_when_script_is_undefined() throws IOException {
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME, null, null);
        createBuildFile(projectDirectoryPath, runNodeTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskFailed(result, RUN_NODE_TASK_NAME);
    }

    @Test
    void should_fail_running_custom_npm_task_when_script_is_undefined() throws IOException {
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, runNpmTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskFailed(result, RUN_NPM_TASK_NAME);
    }

    @Test
    void should_fail_running_custom_pnpm_task_when_script_is_undefined() throws IOException {
        final String runPnpmTaskDefinition = buildPnpmTaskDefinition(RUN_PNPM_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, runPnpmTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_PNPM_TASK_NAME);

        assertTaskFailed(result, RUN_PNPM_TASK_NAME);
    }

    @Test
    void should_fail_running_custom_yarn_task_when_script_is_undefined() throws IOException {
        final String customTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME, null);
        createBuildFile(projectDirectoryPath, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskFailed(result, RUN_YARN_TASK_NAME);
    }

    @Test
    void should_run_custom_tasks() throws IOException {
        final Path packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final Path temporaryScriptPath = createJavascriptFile(temporaryDirectoryPath.resolve("script.js"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeInstallDirectory(projectDirectoryPath.resolve("node-dist"))
            .packageJsonDirectory(packageJsonDirectoryPath)
            .verboseModeEnabled(false);
        final String runCorepackTaskDefinition = buildCorepackTaskDefinition(RUN_COREPACK_TASK_NAME,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, "-v");
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, "run another-script");
        final String runPnpmTaskDefinition = buildPnpmTaskDefinition(RUN_PNPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, "run another-script");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME),
            "run another-script");
        final String additionalContent = String.join("\n", runCorepackTaskDefinition, runNodeTaskDefinition,
            runNpmTaskDefinition, runPnpmTaskDefinition, runYarnTaskDefinition);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult runNodeTaskResult1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult1, PluginTaskOutcome.SUCCESS, RUN_NODE_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runNodeTaskResult2 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult2, PluginTaskOutcome.UP_TO_DATE, RUN_NODE_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runCorepackTaskResult1 = runGradle(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(runCorepackTaskResult1, PluginTaskOutcome.UP_TO_DATE, RUN_COREPACK_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runNpmTaskResult1 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskOutcomes(runNpmTaskResult1, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, RUN_NPM_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult runNpmTaskResult2 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskOutcomes(runNpmTaskResult2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, RUN_NPM_TASK_NAME, PluginTaskOutcome.SUCCESS);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-pnpm.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult runNodeTaskResult3 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult3, PluginTaskOutcome.UP_TO_DATE, RUN_NODE_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runCorepackTaskResult2 = runGradle(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(runCorepackTaskResult2, PluginTaskOutcome.UP_TO_DATE, RUN_COREPACK_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runPnpmTaskResult3 = runGradle(projectDirectoryPath, RUN_PNPM_TASK_NAME);

        assertTaskOutcomes(runPnpmTaskResult3, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, RUN_PNPM_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult runPnpmTaskResult4 = runGradle(projectDirectoryPath, RUN_PNPM_TASK_NAME);

        assertTaskOutcomes(runPnpmTaskResult4, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, RUN_PNPM_TASK_NAME, PluginTaskOutcome.SUCCESS);

        Files.deleteIfExists(packageJsonDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder.verboseModeEnabled(false);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult runNodeTaskResult5 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult5, PluginTaskOutcome.UP_TO_DATE, RUN_NODE_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runNodeTaskResult6 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult6, PluginTaskOutcome.UP_TO_DATE, RUN_NODE_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runCorepackTaskResult3 = runGradle(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(runCorepackTaskResult3, PluginTaskOutcome.UP_TO_DATE, RUN_COREPACK_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult runYarnTaskResult1 = runGradle(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(runYarnTaskResult1, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, RUN_YARN_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult runYarnTaskResult2 = runGradle(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(runYarnTaskResult2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, RUN_YARN_TASK_NAME, PluginTaskOutcome.SUCCESS);
    }
}
