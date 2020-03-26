package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.Helper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.Helper.runGradle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.Helper;

/**
 * Functional tests to verify the {@link CheckTask} integration in a Gradle build. Test cases uses fake Node/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 */
class CheckTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    @Test
    void shouldDoNothingWhenScriptIsNotDefined() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            projectDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.CHECK_TASK_NAME);

        assertTaskIgnored(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSkipped(result, FrontendGradlePlugin.CHECK_TASK_NAME);
    }

    @Test
    void shouldCheckWithoutFrontendTasks() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            projectDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        assertTaskIgnored(result, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSkipped(result, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskUpToDate(result, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);
    }

    @Test
    void shouldCheckFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            projectDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("checkScript", "run check");
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(new File(getClass().getClassLoader().getResource("package-yarn.json").toURI()).toPath(),
            projectDirectoryPath.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz"));
        Helper.createBuildFile(projectDirectoryPath, properties);

        final BuildResult result3 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME);
    }
}
