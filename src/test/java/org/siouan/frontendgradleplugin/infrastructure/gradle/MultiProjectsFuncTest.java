package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.createSettingsFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.logging.LogLevel;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

/**
 * This test suite verifies task execution in a multi-projects build, where Node and Yarn distributions are downloaded
 * and installed by one sub-project, and used as provided distributions by a second sub-project. The project layout is:
 * <pre>
 * node/                        Node install directory, shall be created by sub-project-1
 * sub-project-1/
 *      yarn/                   Yarn install directory, shall be created by sub-project-1
 *      build.gradle            Installs Node and Yarn distributions
 * sub-project-2/
 *      build.gradle            Uses distributions provided by sub-project-1
 * build.gradle                 Declares plugin, but doesn't apply it
 * settings.gradle
 * </pre>
 */
class MultiProjectsFuncTest {

    private static final String SUB_PROJECT_1_NAME = "sub-project-1";

    private static final String SUB_PROJECT_2_NAME = "sub-project-2";

    @TempDir
    Path temporaryDirectorypath;

    @Test
    void shouldRunTasksInSubProjects() throws IOException, URISyntaxException {
        // Root project
        final Path projectDirectoryPath = temporaryDirectorypath;
        createSettingsFile(projectDirectoryPath, "multi-projects-test", SUB_PROJECT_1_NAME, SUB_PROJECT_2_NAME);
        createBuildFile(projectDirectoryPath, true, false);
        final Path packageJsonFilePath = Paths.get(
            getClass().getClassLoader().getResource("package-yarn.json").toURI());

        // Sub-project 1
        final Path nodeInstallDirectory = Paths.get("${rootProject.projectDir}/node");
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeInstallDirectory", nodeInstallDirectory);
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz"));
        properties.put("installScript", "run install1");
        properties.put("cleanScript", "run clean1");
        properties.put("assembleScript", "run assemble1");
        properties.put("checkScript", "run check1");
        properties.put("publishScript", "run publish1");
        final Path subProject1Path = Files.createDirectory(projectDirectoryPath.resolve(SUB_PROJECT_1_NAME));
        Files.copy(packageJsonFilePath, subProject1Path.resolve("package.json"));
        createBuildFile(subProject1Path, properties);

        // Sub-project 2
        final Path subProject2Path = Files.createDirectory(projectDirectoryPath.resolve(SUB_PROJECT_2_NAME));
        properties.clear();
        properties.put("nodeDistributionProvided", true);
        properties.put("nodeInstallDirectory", nodeInstallDirectory);
        properties.put("yarnEnabled", true);
        properties.put("yarnDistributionProvided", true);
        properties.put("yarnInstallDirectory", subProject1Path.resolve("yarn"));
        properties.put("installScript", "run install2");
        properties.put("cleanScript", "run clean2");
        properties.put("assembleScript", "run assemble2");
        properties.put("checkScript", "run check2");
        properties.put("publishScript", "run publish2");
        Files.copy(packageJsonFilePath, subProject2Path.resolve("package.json"));
        createBuildFile(subProject2Path, properties,
            "tasks.named('installFrontend').configure {" + "dependsOn project(':" + SUB_PROJECT_1_NAME
                + "').installNode\ndependsOn project(':" + SUB_PROJECT_1_NAME + "').installYarn\n}");

        final BuildResult result1 = runGradle(projectDirectoryPath, LogLevel.LIFECYCLE, BasePlugin.CLEAN_TASK_NAME,
            LifecycleBasePlugin.BUILD_TASK_NAME, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskIgnored(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.CLEAN_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.CLEAN_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, BasePlugin.CLEAN_TASK_NAME);
        assertTaskSkipped(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.CLEAN_TASK_NAME);
        assertTaskUpToDate(result1, SUB_PROJECT_2_NAME, BasePlugin.CLEAN_TASK_NAME);

        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, BasePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, LifecycleBasePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, LifecycleBasePlugin.BUILD_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, BasePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, LifecycleBasePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, LifecycleBasePlugin.BUILD_TASK_NAME);

        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_1_NAME, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result1, SUB_PROJECT_2_NAME, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, LogLevel.INFO, BasePlugin.CLEAN_TASK_NAME,
            LifecycleBasePlugin.BUILD_TASK_NAME, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskIgnored(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.CLEAN_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        assertTaskUpToDate(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.CLEAN_TASK_NAME);
        assertTaskUpToDate(result2, SUB_PROJECT_1_NAME, BasePlugin.CLEAN_TASK_NAME);
        assertTaskSkipped(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.CLEAN_TASK_NAME);
        assertTaskUpToDate(result2, SUB_PROJECT_2_NAME, BasePlugin.CLEAN_TASK_NAME);

        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, BasePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, LifecycleBasePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, LifecycleBasePlugin.BUILD_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, BasePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, LifecycleBasePlugin.CHECK_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, LifecycleBasePlugin.BUILD_TASK_NAME);

        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_1_NAME, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result2, SUB_PROJECT_2_NAME, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);
    }
}
