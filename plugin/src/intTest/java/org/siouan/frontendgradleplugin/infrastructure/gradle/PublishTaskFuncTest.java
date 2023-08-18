package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertPublishTaskOutcomes;
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
 * Functional tests to verify the {@link PublishTask} integration in a Gradle build. Test cases uses fake Node/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 *
 * @since 1.4.0
 */
class PublishTaskFuncTest {

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
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"))
            .packageJsonDirectory(packageJsonDirectoryPath);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        assertPublishTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED, null);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        assertPublishTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED, null);
    }

    @Test
    void should_skip_plugin_task_when_running_gradle_task_and_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"))
            .packageJsonDirectory(packageJsonDirectoryPath);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME);

        assertPublishTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED,
            PluginTaskOutcome.UP_TO_DATE);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME);

        assertPublishTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SKIPPED,
            PluginTaskOutcome.UP_TO_DATE);
    }

    @Test
    void should_publish_frontend() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"))
            .packageJsonDirectory(packageJsonDirectoryPath)
            .assembleScript("run assemble")
            .publishScript("run publish");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME);

        assertPublishTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME);

        assertPublishTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);
    }
}
