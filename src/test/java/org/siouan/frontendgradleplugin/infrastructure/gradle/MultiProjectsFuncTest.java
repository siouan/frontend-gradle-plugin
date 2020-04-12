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
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.Helper;

class MultiProjectsFuncTest {

    private static final String SUB_PROJECT_1_NAME = "sub-project-1";

    private static final String SUB_PROJECT_2_NAME = "sub-project-2";

    @TempDir
    Path temporaryDirectorypath;

    @Test
    void shouldRunTasksInSubProjects() throws IOException, URISyntaxException {
        final Path projectDirectoryPath = temporaryDirectorypath;
        Helper.createSettingsFile(projectDirectoryPath, "multi-projects-test", SUB_PROJECT_1_NAME, SUB_PROJECT_2_NAME);
        Helper.createBuildFile(projectDirectoryPath, true, false);
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeInstallDirectory", Paths.get("${rootProject.projectDir}/node"));
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        final Path subProject1Path = Files.createDirectory(projectDirectoryPath.resolve(SUB_PROJECT_1_NAME));
        final Path packageJsonFilePath = Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI());
        Files.copy(packageJsonFilePath, subProject1Path.resolve("package.json"));
        Helper.createBuildFile(subProject1Path, properties);
        final Path subProject2Path = Files.createDirectory(projectDirectoryPath.resolve(SUB_PROJECT_2_NAME));
        Files.copy(packageJsonFilePath, subProject2Path.resolve("package.json"));
        Helper.createBuildFile(subProject2Path, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_TASK_NAME);

        assertTaskIgnored(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);

        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);

        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, FrontendGradlePlugin.INSTALL_TASK_NAME);

        assertTaskIgnored(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);

        assertTaskUpToDate(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);
    }
}
