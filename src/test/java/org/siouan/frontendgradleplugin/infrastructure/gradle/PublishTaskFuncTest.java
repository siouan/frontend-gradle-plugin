package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

/**
 * Functional tests to verify the {@link PublishTask} integration in a Gradle build. Test cases uses fake Node/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 *
 * @since 1.4.0
 */
class PublishTaskFuncTest {

    @TempDir
    Path projectDirectoryPath;

    private Path packageJsonDirectoryPath;

    @BeforeEach
    void setUp() throws IOException {
        packageJsonDirectoryPath = Files.createDirectory(projectDirectoryPath.resolve("frontend"));
    }

    @Test
    void shouldBeSkippedWhenPublishScriptIsNotDefined() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("assembleScript", "run assemble");
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        assertTaskIgnored(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskIgnored(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskUpToDate(result2, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);
    }

    @Test
    void shouldBeSkippedWhenAssembleScriptIsNotDefined() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("publishScript", "run publish");
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        assertTaskIgnored(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.PUBLISH_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskIgnored(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskUpToDate(result2, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);
    }

    @Test
    void shouldPublishFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip"));
        properties.put("assembleScript", "run assemble");
        properties.put("publishScript", "run publish");
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result1 = runGradle(projectDirectoryPath, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result1, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        final BuildResult result2 = runGradle(projectDirectoryPath, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result2, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        Files.deleteIfExists(projectDirectoryPath.resolve("package-lock.json"));
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-yarn.json").toURI()),
            packageJsonDirectoryPath.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties.put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz"));
        createBuildFile(projectDirectoryPath, properties);

        final BuildResult result3 = runGradle(projectDirectoryPath, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result3, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        final BuildResult result4 = runGradle(projectDirectoryPath, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.PUBLISH_TASK_NAME);
        assertTaskSuccess(result4, PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME);
    }
}
