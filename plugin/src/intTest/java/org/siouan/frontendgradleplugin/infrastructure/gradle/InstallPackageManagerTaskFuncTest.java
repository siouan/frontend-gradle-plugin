package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradleAndExpectFailure;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.FAILED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link InstallPackageManagerTask} integration in a Gradle build. Test cases uses a
 * fake Node distribution, to avoid the download overhead. All executables in these distributions simply call the 'node'
 * executable with the same arguments.
 */
class InstallPackageManagerTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_skip_task_when_package_json_file_does_not_exist() throws IOException {
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result1, SUCCESS, SUCCESS, SKIPPED);

        final BuildResult result2 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result2, UP_TO_DATE, UP_TO_DATE, SKIPPED);
    }

    @Test
    void should_fail_when_node_install_directory_is_not_a_directory() throws IOException {
        Files.copy(getResourcePath("package-any-manager.json"), projectDirectoryPath.resolve("package.json"));
        createBuildFile(projectDirectoryPath, new FrontendMapBuilder().nodeDistributionProvided(true).toMap());

        final BuildResult result = runGradleAndExpectFailure(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(result, SKIPPED, SUCCESS, FAILED);
    }

    @Test
    void should_install_package_managers() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult installNpmResult1 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installNpmResult1, SUCCESS, SUCCESS, SUCCESS);

        final BuildResult installNpmResult2 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installNpmResult2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE);

        Files.copy(getResourcePath("package-pnpm.json"), projectDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult installPnpmResult1 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installPnpmResult1, UP_TO_DATE, SUCCESS, SUCCESS);

        final BuildResult installPnpmResult2 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installPnpmResult2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE);

        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult installYarnResult1 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installYarnResult1, UP_TO_DATE, SUCCESS, SUCCESS);

        final BuildResult installYarnResult2 = runGradle(projectDirectoryPath, INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installYarnResult2, UP_TO_DATE, UP_TO_DATE, UP_TO_DATE);
    }
}
