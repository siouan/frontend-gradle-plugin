package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;

/**
 * Functional tests to verify the {@link InstallPackageManagerTask} integration in a Gradle build. Test cases uses a
 * fake Node distribution, to avoid the download overhead. All executables in these distributions simply call the 'node'
 * executable with the same arguments.
 */
class InstallPackageManagerTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_install_package_managers() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("18.17.1")
            .nodeDistributionUrl(getResourceUrl("node-v18.17.1.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult installNpmResult1 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installNpmResult1, PluginTaskOutcome.SUCCESS, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);

        final BuildResult installNpmResult2 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installNpmResult2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE);

        Files.copy(getResourcePath("package-pnpm.json"), projectDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult installPnpmResult1 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installPnpmResult1, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);

        final BuildResult installPnpmResult2 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installPnpmResult2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE);

        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult installYarnResult1 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installYarnResult1, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.SUCCESS,
            PluginTaskOutcome.SUCCESS);

        final BuildResult installYarnResult2 = runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME);

        assertTaskOutcomes(installYarnResult2, PluginTaskOutcome.UP_TO_DATE, PluginTaskOutcome.UP_TO_DATE,
            PluginTaskOutcome.UP_TO_DATE);
    }
}
