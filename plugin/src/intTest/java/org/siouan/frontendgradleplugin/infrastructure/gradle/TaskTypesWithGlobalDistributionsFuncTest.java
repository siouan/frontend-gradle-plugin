package org.siouan.frontendgradleplugin.infrastructure.gradle;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;

/**
 * Functional tests to verify task types {@link RunNode}, {@link RunNpm},  {@link RunYarn} in a Gradle build, with a
 * Node.js distribution resolved with the {@link FrontendGradlePlugin#NODEJS_HOME_ENV_VAR} environment variable. Test
 * cases uses a fake Node.js distribution, to avoid the download overhead. The 'npm' and 'npx' executables in these
 * distributions simply call the 'node' executable with the same arguments.
 */
class TaskTypesWithGlobalDistributionsFuncTest {

    private static final String RUN_COREPACK_TASK_NAME = "customCorepackTask";

    private static final String RUN_NODE_TASK_NAME = "customNodeTask";

    private static final String RUN_NPM_TASK_NAME = "customNpmTask";

    private static final String RUN_PNPM_TASK_NAME = "customPnpmTask";

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
    void should_fail_running_custom_corepack_task_when_node_executable_does_not_exist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .verboseModeEnabled(true);
        final String runCorepackTaskDefinition = buildCorepackTaskDefinition(RUN_COREPACK_TASK_NAME,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, "disable");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), runCorepackTaskDefinition);
        final Path nodejsHomePath = getResourcePath("node-dist-without-node");

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, nodejsHomePath,
            RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, RUN_COREPACK_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_fail_running_custom_node_task_when_node_executable_does_not_exist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().nodeDistributionProvided(true);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), runNodeTaskDefinition);
        final Path nodejsHomePath = getResourcePath("node-dist-without-node");

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, nodejsHomePath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, RUN_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    @Test
    void should_fail_running_custom_npm_task_when_node_executable_does_not_exist() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().nodeDistributionProvided(true);
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME, "run npm-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), runNpmTaskDefinition);
        final Path nodejsHomePath = getResourcePath("node-dist-without-node");

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, nodejsHomePath, RUN_NPM_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.FAILED,
            RUN_NPM_TASK_NAME, PluginTaskOutcome.IGNORED);
    }

    @Test
    void should_fail_running_custom_pnpm_task_when_node_executable_does_not_exist() throws IOException {
        Files.copy(getResourcePath("package-pnpm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().nodeDistributionProvided(true);
        final String runNpmTaskDefinition = buildPnpmTaskDefinition(RUN_PNPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME, "run pnpm-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), runNpmTaskDefinition);
        final Path nodejsHomePath = getResourcePath("node-dist-without-node");

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, nodejsHomePath, RUN_PNPM_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.FAILED,
            RUN_PNPM_TASK_NAME, PluginTaskOutcome.IGNORED);
    }

    @Test
    void should_fail_running_custom_yarn_task_when_node_executable_does_not_exist() throws IOException {
        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().nodeDistributionProvided(true);
        final String runNpmTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME, "run yarn-script");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), runNpmTaskDefinition);
        final Path nodejsHomePath = getResourcePath("node-dist-without-node");

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, nodejsHomePath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(result, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.FAILED,
            RUN_YARN_TASK_NAME, PluginTaskOutcome.IGNORED);
    }

    @Test
    void should_run_custom_tasks() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().nodeDistributionProvided(true);
        final String runNodeTaskDefinition = buildNodeTaskDefinition(RUN_NODE_TASK_NAME,
            temporaryScriptPath.toString().replace("\\", "\\\\"));
        final String runCorepackTaskDefinition = buildCorepackTaskDefinition(RUN_COREPACK_TASK_NAME,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, "disable");
        final String runNpmTaskDefinition = buildNpmTaskDefinition(RUN_NPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, "run npm-script");
        final String runPnpmTaskDefinition = buildPnpmTaskDefinition(RUN_PNPM_TASK_NAME,
            FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, "run pnpm-script");
        final String runYarnTaskDefinition = buildYarnTaskDefinition(RUN_YARN_TASK_NAME,
            FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, "run yarn-script");
        final String additionalContent = String.join("\n", runNodeTaskDefinition, runCorepackTaskDefinition,
            runNpmTaskDefinition, runPnpmTaskDefinition, runYarnTaskDefinition);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap(), additionalContent);
        final Path nodejsHomePath = getResourcePath("node-dist-provided");

        final BuildResult nodeTaskResult = runGradle(projectDirectoryPath, nodejsHomePath, RUN_NODE_TASK_NAME);

        assertTaskOutcomes(nodeTaskResult, PluginTaskOutcome.SKIPPED, RUN_NODE_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult corepackTaskResult = runGradle(projectDirectoryPath, nodejsHomePath, RUN_COREPACK_TASK_NAME);

        assertTaskOutcomes(corepackTaskResult, PluginTaskOutcome.SKIPPED, RUN_COREPACK_TASK_NAME,
            PluginTaskOutcome.SUCCESS);

        final BuildResult npmTaskResult = runGradle(projectDirectoryPath, nodejsHomePath, RUN_NPM_TASK_NAME);

        assertTaskOutcomes(npmTaskResult, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, RUN_NPM_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult pnpmTaskResult = runGradle(projectDirectoryPath, nodejsHomePath, RUN_PNPM_TASK_NAME);

        assertTaskOutcomes(pnpmTaskResult, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, RUN_PNPM_TASK_NAME, PluginTaskOutcome.SUCCESS);

        final BuildResult yarnTaskResult = runGradle(projectDirectoryPath, nodejsHomePath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(yarnTaskResult, PluginTaskOutcome.SKIPPED, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, RUN_YARN_TASK_NAME, PluginTaskOutcome.SUCCESS);
    }
}
