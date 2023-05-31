package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;

/**
 * Functional tests to verify the {@link InstallDependenciesTask} integration in a Gradle build. Test cases uses fake
 * Node/Yarn distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions
 * simply call the 'node' executable with the same arguments.
 */
class InstallDependenciesTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_succeed_with_default_script() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v18.16.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS);
    }

    @Test
    void should_succeed_with_custom_script() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.16.0")
            .nodeDistributionUrl(getResourceUrl("node-v18.16.0.zip"))
            .installScript("ci");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS);
    }
}
