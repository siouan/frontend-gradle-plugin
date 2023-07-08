package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertAssembleTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

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
 * Functional tests to verify the {@link AssembleTask} integration in a Gradle build. Test cases uses fake Node/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 */
class AssembleTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    private Path packageJsonDirectoryPath;

    @BeforeEach
    void setUp() throws IOException {
        packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
    }

    @Test
    void should_skip_plugin_task_when_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v18.16.0.zip"))
            .packageJsonDirectory(packageJsonDirectoryPath);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);

        assertAssembleTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED, null);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);

        assertAssembleTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED, null);
    }

    @Test
    void should_skip_plugin_task_when_running_gradle_task_and_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v18.16.0.zip"))
            .packageJsonDirectory(packageJsonDirectoryPath);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertAssembleTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED,
            PluginTaskOutcome.UP_TO_DATE);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertAssembleTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED,
            PluginTaskOutcome.UP_TO_DATE);
    }

    @Test
    void should_assemble_frontend() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v18.16.0.zip"))
            .packageJsonDirectory(packageJsonDirectoryPath)
            .assembleScript("run assemble");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertAssembleTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertAssembleTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);
    }
}
