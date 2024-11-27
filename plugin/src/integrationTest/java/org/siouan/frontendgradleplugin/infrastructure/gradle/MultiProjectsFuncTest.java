package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.GRADLE_CLEAN_TASK_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcomes;
import static org.siouan.frontendgradleplugin.test.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.GradleSettingsFiles.createSettingsFile;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.IGNORED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SKIPPED;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.SUCCESS;
import static org.siouan.frontendgradleplugin.test.PluginTaskOutcome.UP_TO_DATE;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;

/**
 * This test suite verifies task execution in a multi-projects build, where Node is downloaded and installed by one
 * sub-project, and used as a provided distribution by a other sub-projects. The project layout is:
 * <pre>
 * node/                        Node install directory, shall be created by sub-project-1
 * yarn-project-1/              Yarn sub-project
 *      build.gradle            Installs Node and Yarn distributions
 * npm-project-2/               NPM sub-project
 *      build.gradle            Uses distributions provided by sub-project-1
 * pnpm-project-3/              PNPM sub-project
 *      build.gradle            Uses distributions provided by sub-project-1
 * build.gradle                 Declares plugin, but doesn't apply it
 * settings.gradle
 * </pre>
 */
class MultiProjectsFuncTest {

    private static final String YARN_SUB_PROJECT_NAME = "yarn-project-1";

    private static final String NPM_SUB_PROJECT_NAME = "npm-project-2";

    private static final String PNPM_SUB_PROJECT_NAME = "pnpm-project-3";

    @TempDir
    Path temporaryDirectorypath;

    @Test
    void should_run_tasks_in_sub_projects() throws IOException {
        // Root project
        final Path projectDirectoryPath = temporaryDirectorypath;
        createSettingsFile(projectDirectoryPath, "multi-projects-test", YARN_SUB_PROJECT_NAME, NPM_SUB_PROJECT_NAME,
            PNPM_SUB_PROJECT_NAME);
        createBuildFile(projectDirectoryPath, true, false);
        final Path nodeInstallDirectory = Paths.get("${rootProject.projectDir}/node");

        // Sub-project 1
        final Path yarnSubProjectPath = Files.createDirectory(projectDirectoryPath.resolve(YARN_SUB_PROJECT_NAME));
        Files.copy(getResourcePath("package-yarn.json"), yarnSubProjectPath.resolve("package.json"));
        final FrontendMapBuilder yarnSubProjectFrontendProperties = new FrontendMapBuilder()
            .nodeVersion("22.11.0")
            .nodeInstallDirectory(nodeInstallDirectory)
            .nodeDistributionUrl(getResourcePath("node-v22.11.0.zip"))
            .installScript("run install")
            .cleanScript("run clean")
            .assembleScript("run assemble")
            .checkScript("run check")
            .publishScript("run publish");
        createBuildFile(yarnSubProjectPath, yarnSubProjectFrontendProperties.toMap());

        // Sub-project 2
        final Path npmSubProjectPath = Files.createDirectory(projectDirectoryPath.resolve(NPM_SUB_PROJECT_NAME));
        Files.copy(getResourcePath("package-npm.json"), npmSubProjectPath.resolve("package.json"));
        final FrontendMapBuilder npmSubProjectFrontendProperties = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(nodeInstallDirectory)
            .installScript("run install")
            .cleanScript("run clean")
            .assembleScript("run assemble")
            .checkScript("run check")
            .publishScript("run publish");
        createBuildFile(npmSubProjectPath, npmSubProjectFrontendProperties.toMap(),
            "tasks.named('installPackageManager').configure { dependsOn(':" + YARN_SUB_PROJECT_NAME + ":installNode') }");

        // Sub-project 3
        final Path pnpmSubProjectPath = Files.createDirectory(projectDirectoryPath.resolve(PNPM_SUB_PROJECT_NAME));
        Files.copy(getResourcePath("package-pnpm.json"), pnpmSubProjectPath.resolve("package.json"));
        final FrontendMapBuilder pnpmSubProjectFrontendProperties = new FrontendMapBuilder()
            .nodeDistributionProvided(true)
            .nodeInstallDirectory(nodeInstallDirectory)
            .installScript("run install")
            .cleanScript("run clean")
            .assembleScript("run assemble")
            .checkScript("run check")
            .publishScript("run publish");
        createBuildFile(pnpmSubProjectPath, pnpmSubProjectFrontendProperties.toMap(),
            "tasks.named('installPackageManager').configure { dependsOn(':" + YARN_SUB_PROJECT_NAME + ":installNode') }");

        final BuildResult result1 = runGradle(projectDirectoryPath, GRADLE_CLEAN_TASK_NAME,
            LifecycleBasePlugin.BUILD_TASK_NAME, GRADLE_PUBLISH_TASK_NAME);

        assertTaskOutcomes(result1, YARN_SUB_PROJECT_NAME, SUCCESS, SKIPPED, SUCCESS, SUCCESS, SUCCESS, SUCCESS,
            SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result1, NPM_SUB_PROJECT_NAME, SKIPPED, SKIPPED, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS,
            SUCCESS, SUCCESS, UP_TO_DATE, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result1, PNPM_SUB_PROJECT_NAME, SKIPPED, SKIPPED, SUCCESS, SUCCESS, SUCCESS, SUCCESS,
            SUCCESS, SUCCESS, SUCCESS, UP_TO_DATE, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result1, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED,
            IGNORED, IGNORED, IGNORED, IGNORED);

        final BuildResult result2 = runGradle(projectDirectoryPath, LifecycleBasePlugin.BUILD_TASK_NAME,
            GRADLE_PUBLISH_TASK_NAME);

        assertTaskOutcomes(result2, YARN_SUB_PROJECT_NAME, UP_TO_DATE, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS,
            IGNORED, SUCCESS, SUCCESS, SUCCESS, IGNORED, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result2, NPM_SUB_PROJECT_NAME, SKIPPED, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, IGNORED,
            SUCCESS, SUCCESS, SUCCESS, IGNORED, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result2, PNPM_SUB_PROJECT_NAME, SKIPPED, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, IGNORED,
            SUCCESS, SUCCESS, SUCCESS, IGNORED, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result2, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED,
            IGNORED, IGNORED, IGNORED, IGNORED);

        final BuildResult result3 = runGradle(projectDirectoryPath, LifecycleBasePlugin.BUILD_TASK_NAME,
            GRADLE_PUBLISH_TASK_NAME);

        assertTaskOutcomes(result3, YARN_SUB_PROJECT_NAME, UP_TO_DATE, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS,
            IGNORED, SUCCESS, SUCCESS, SUCCESS, IGNORED, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result3, NPM_SUB_PROJECT_NAME, SKIPPED, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, IGNORED,
            SUCCESS, SUCCESS, SUCCESS, IGNORED, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result3, PNPM_SUB_PROJECT_NAME, SKIPPED, SKIPPED, UP_TO_DATE, UP_TO_DATE, SUCCESS, IGNORED,
            SUCCESS, SUCCESS, SUCCESS, IGNORED, SUCCESS, SUCCESS, SUCCESS);

        assertTaskOutcomes(result3, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED, IGNORED,
            IGNORED, IGNORED, IGNORED, IGNORED);
    }
}
