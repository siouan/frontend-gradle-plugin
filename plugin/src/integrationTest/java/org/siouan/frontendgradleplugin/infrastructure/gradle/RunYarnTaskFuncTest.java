package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.RUN_YARN_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.FAILED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link RunYarnTask} integration in a Gradle build. Test cases uses a fake Node
 * distribution, to avoid the download overhead. All executables in these distributions simply call the 'node'
 * executable with the same arguments.
 */
class RunYarnTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_fail_task_when_args_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.18.0")
            .nodeDistributionUrl(getResourcePath("node-v20.18.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, RUN_YARN_TASK_NAME);

        assertTaskOutcomes(result, SUCCESS, SKIPPED, SUCCESS, SUCCESS, RUN_YARN_TASK_NAME, FAILED);
    }

    @Test
    void should_run_command() throws IOException {
        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("20.18.0")
            .nodeDistributionUrl(getResourcePath("node-v20.18.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradle(projectDirectoryPath, RUN_YARN_TASK_NAME, "--args=add jest -D");

        assertTaskOutcomes(result, SUCCESS, SKIPPED, SUCCESS, SUCCESS, RUN_YARN_TASK_NAME, SUCCESS);
    }
}
