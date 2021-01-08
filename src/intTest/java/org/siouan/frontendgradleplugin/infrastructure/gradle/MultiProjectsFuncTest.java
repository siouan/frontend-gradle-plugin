package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleSettingsFiles.createSettingsFile;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourceUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.api.logging.LogLevel;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;

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
    void shouldRunTasksInSubProjects() throws IOException {
        // Root project
        final Path projectDirectoryPath = temporaryDirectorypath;
        createSettingsFile(projectDirectoryPath, "multi-projects-test", SUB_PROJECT_1_NAME, SUB_PROJECT_2_NAME);
        createBuildFile(projectDirectoryPath, true, false);
        final Path packageJsonFilePath = getResourcePath("package-yarn.json");

        // Sub-project 1
        final Path nodeInstallDirectory = Paths.get("${rootProject.projectDir}/node");
        final FrontendMapBuilder frontendMapBuilder1 = new FrontendMapBuilder()
            .nodeVersion("12.18.3")
            .nodeInstallDirectory(nodeInstallDirectory)
            .nodeDistributionUrl(getResourceUrl("node-v12.18.3.zip"))
            .yarnEnabled(true)
            .yarnVersion("1.22.4")
            .yarnDistributionUrl(getResourceUrl("yarn-v1.22.4.tar.gz"))
            .installScript("run install1")
            .cleanScript("run clean1")
            .assembleScript("run assemble1")
            .checkScript("run check1")
            .publishScript("run publish1");
        final Path subProject1Path = Files.createDirectory(projectDirectoryPath.resolve(SUB_PROJECT_1_NAME));
        Files.copy(packageJsonFilePath, subProject1Path.resolve("package.json"));
        createBuildFile(subProject1Path, frontendMapBuilder1.toMap());

        // Sub-project 2
        final Path subProject2Path = Files.createDirectory(projectDirectoryPath.resolve(SUB_PROJECT_2_NAME));
        final FrontendMapBuilder frontendMapBuilder2 = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(nodeInstallDirectory)
            .yarnEnabled(true)
            .yarnDistributionProvided(true)
            .yarnInstallDirectory(subProject1Path.resolve("yarn"))
            .installScript("run install2")
            .cleanScript("run clean2")
            .assembleScript("run assemble2")
            .checkScript("run check2")
            .publishScript("run publish2");
        Files.copy(packageJsonFilePath, subProject2Path.resolve("package.json"));
        createBuildFile(subProject2Path, frontendMapBuilder2.toMap(),
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
