package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_NODE_TASK_NAME;
import static org.siouan.frontendgradleplugin.infrastructure.gradle.InstallCorepackTask.LATEST_VERSION_ARGUMENT;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildCorepackTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildNodeTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildNpmTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildPnpmTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.buildYarnTaskDefinition;
import static org.siouan.frontendgradleplugin.test.TaskTypes.createJavascriptFileLoggingProcessTitle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify task types {@link RunNodeTaskType}, {@link RunNpmTaskType}, {@link RunPnpmTaskType},  {@link RunYarnTaskType} in a Gradle
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
    void should_run_custom_tasks_and_forward_environment_variables() throws IOException {
        final Path packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final Path temporaryScriptPath = createJavascriptFileLoggingProcessTitle(
            temporaryDirectoryPath.resolve("script.js"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.18.0")
            .nodeInstallDirectory(projectDirectoryPath.resolve("node-dist"))
            .corepackVersion(LATEST_VERSION_ARGUMENT)
            .packageJsonDirectory(packageJsonDirectoryPath);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"),
            Map.of("NODE_OPTIONS", "--title=\\\"Run custom node task\\\""));
        final String runCorepackTaskDefinition = buildCorepackTaskDefinition(RUN_COREPACK_TASK_NAME, "-v");
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME, INSTALL_FRONTEND_TASK_NAME,
            "run another-script");
        final String runPnpmTaskDefinition = buildPnpmTaskDefinition(RUN_PNPM_TASK_NAME, INSTALL_FRONTEND_TASK_NAME,
            "run another-script");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            Set.of(INSTALL_NODE_TASK_NAME, INSTALL_FRONTEND_TASK_NAME), "run another-script");
        final String additionalContent = String.join("\n", runCorepackTaskDefinition, runNodeTaskDefinition,
            runNpmTaskDefinition, runPnpmTaskDefinition, runYarnTaskDefinition);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult runNodeTaskResult1 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult1, SUCCESS, RUN_NODE_TASK_NAME, SUCCESS);
        assertThat(runNodeTaskResult1.getOutput()).containsIgnoringNewLines(
            "> Task :customNodeTask\nRun custom node task\n");

        final BuildResult runNodeTaskResult2 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult2, UP_TO_DATE, RUN_NODE_TASK_NAME, SUCCESS);

        final BuildResult runCorepackTaskResult1 = runGradle(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(runCorepackTaskResult1, UP_TO_DATE, SUCCESS, RUN_COREPACK_TASK_NAME, SUCCESS);

        final BuildResult runNpmTaskResult1 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskOutcomes(runNpmTaskResult1, UP_TO_DATE, UP_TO_DATE, SUCCESS, SUCCESS, SUCCESS, RUN_NPM_TASK_NAME,
            SUCCESS);

        final BuildResult runNpmTaskResult2 = runGradle(projectDirectoryPath, RUN_NPM_TASK_NAME);

        assertTaskOutcomes(runNpmTaskResult2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, SUCCESS,
            RUN_NPM_TASK_NAME, SUCCESS);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-pnpm.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult runNodeTaskResult3 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult3, UP_TO_DATE, RUN_NODE_TASK_NAME, SUCCESS);

        final BuildResult runCorepackTaskResult2 = runGradle(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(runCorepackTaskResult2, UP_TO_DATE, UP_TO_DATE, RUN_COREPACK_TASK_NAME, SUCCESS);

        final BuildResult runPnpmTaskResult3 = runGradle(projectDirectoryPath, RUN_PNPM_TASK_NAME);

        assertTaskOutcomes(runPnpmTaskResult3, UP_TO_DATE, UP_TO_DATE, SUCCESS, SUCCESS, SUCCESS, RUN_PNPM_TASK_NAME,
            SUCCESS);

        final BuildResult runPnpmTaskResult4 = runGradle(projectDirectoryPath, RUN_PNPM_TASK_NAME);

        assertTaskOutcomes(runPnpmTaskResult4, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, SUCCESS,
            RUN_PNPM_TASK_NAME, SUCCESS);

        Files.deleteIfExists(packageJsonDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder.verboseModeEnabled(false);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);

        final BuildResult runNodeTaskResult4 = runGradle(projectDirectoryPath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(runNodeTaskResult4, UP_TO_DATE, RUN_NODE_TASK_NAME, SUCCESS);

        final BuildResult runCorepackTaskResult3 = runGradle(projectDirectoryPath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(runCorepackTaskResult3, UP_TO_DATE, UP_TO_DATE, RUN_COREPACK_TASK_NAME, SUCCESS);

        final BuildResult runYarnTaskResult1 = runGradle(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(runYarnTaskResult1, UP_TO_DATE, UP_TO_DATE, SUCCESS, SUCCESS, SUCCESS, RUN_YARN_TASK_NAME,
            SUCCESS);

        final BuildResult runYarnTaskResult2 = runGradle(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(runYarnTaskResult2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, SUCCESS,
            RUN_YARN_TASK_NAME, SUCCESS);
    }
}
