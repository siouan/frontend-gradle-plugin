package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

/**
 * Functional tests to verify the {@link InstallDependenciesTask} integration in a Gradle build. Test cases uses fake
 * Node/Yarn distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions
 * simply call the 'node' executable with the same arguments.
 */
class InstallDependenciesTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void should_install_frontend_dependencies_with_npm_or_yarn_and_default_script() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.0")
            .nodeDistributionUrl(getResourceUrl("node-v14.17.0.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder.yarnEnabled(true).yarnVersion("3.0.0");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result3 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
    }

    @Test
    void should_install_frontend_with_npm_or_yarn_and_custom_script() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), projectDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.0")
            .nodeDistributionUrl(getResourceUrl("node-v14.17.0.zip"))
            .installScript("ci");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), projectDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder.yarnEnabled(true).yarnVersion("3.0.0");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result3 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
    }
}
