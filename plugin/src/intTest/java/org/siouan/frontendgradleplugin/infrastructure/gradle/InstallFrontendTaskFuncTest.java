package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link InstallFrontendTask} integration in a Gradle build. Test cases uses fake
 * Node/NPM/PNPM/Yarn distributions, to avoid the download overhead. The 'npm', 'pnpm', 'yarn' executables in these
 * distributions simply call the 'node' executable with the same arguments.
 */
class InstallFrontendTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_skip_task_when_package_json_file_does_not_exist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result1, SUCCESS, SUCCESS, SKIPPED, SKIPPED);

        final BuildResult result2 = runGradle(projectDirectoryPath, INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result2, UP_TO_DATE, UP_TO_DATE, SKIPPED, SKIPPED);
    }

    @Test
    void should_succeed_with_default_script() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result1, SUCCESS, SUCCESS, SUCCESS, SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, SUCCESS);
    }

    @Test
    void should_succeed_with_custom_script() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"))
            .installScript("ci");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result1, SUCCESS, SUCCESS, SUCCESS, SUCCESS);

        final BuildResult result2 = runGradle(projectDirectoryPath, INSTALL_FRONTEND_TASK_NAME);

        assertTaskOutcomes(result2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE, SUCCESS);
    }
}
