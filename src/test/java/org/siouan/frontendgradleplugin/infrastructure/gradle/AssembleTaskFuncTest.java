package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.Helper.runGradle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.plugins.BasePlugin;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.Helper;

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
    void shouldBeSkippedWhenScriptIsNotDefined() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);

        assertTaskIgnored(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSkipped(result, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
    }

    @Test
    void shouldAssembleAndSkipFrontendAssemblingTask() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskIgnored(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSkipped(result, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskUpToDate(result, BasePlugin.ASSEMBLE_TASK_NAME);
    }

    @Test
    void shouldAssembleFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("assembleScript", "run assemble");
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, BasePlugin.ASSEMBLE_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, BasePlugin.ASSEMBLE_TASK_NAME);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-yarn.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result3 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result3, BasePlugin.ASSEMBLE_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, BasePlugin.ASSEMBLE_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result4, BasePlugin.ASSEMBLE_TASK_NAME);
    }
}
