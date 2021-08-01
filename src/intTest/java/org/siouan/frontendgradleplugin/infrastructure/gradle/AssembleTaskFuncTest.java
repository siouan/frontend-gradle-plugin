package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskIgnored;
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

import org.gradle.api.plugins.BasePlugin;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

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
    void should_be_skipped_when_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.3")
            .nodeDistributionUrl(getResourceUrl("node-v14.17.3.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);

        assertTaskIgnored(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSkipped(result, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
    }

    @Test
    void should_be_skipped_when_assembling_with_gradle_and_script_is_not_defined() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.3")
            .nodeDistributionUrl(getResourceUrl("node-v14.17.3.zip"));
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskIgnored(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSkipped(result, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskUpToDate(result, BasePlugin.ASSEMBLE_TASK_NAME);
    }

    @Test
    void should_assemble_frontend_with_npm_or_yarn() throws IOException {
        Files.copy(getResourcePath("package-npm.json"), packageJsonDirectoryPath.resolve("package.json"));
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder()
            .nodeVersion("14.17.3")
            .nodeDistributionUrl(getResourceUrl("node-v14.17.3.zip"))
            .assembleScript("run assemble");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, BasePlugin.ASSEMBLE_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, BasePlugin.ASSEMBLE_TASK_NAME);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(getResourcePath("package-yarn.json"), packageJsonDirectoryPath.resolve("package.json"),
            StandardCopyOption.REPLACE_EXISTING);
        frontendMapBuilder.yarnEnabled(true).yarnVersion("3.0.0");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result3 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result3, BasePlugin.ASSEMBLE_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_YARN_GLOBALLY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ENABLE_YARN_BERRY_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result4, BasePlugin.ASSEMBLE_TASK_NAME);
    }
}
