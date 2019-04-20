package org.siouan.frontendgradleplugin.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

/**
 * Helper to prepare functional tests.
 */
public final class FunctionalTestHelper {

    private static final String BUILD_FILE_NAME = "build.gradle";

    private static final String MINIMAL_GRADLE_VERSION = "5.1";

    private FunctionalTestHelper() {
    }

    /**
     * Asserts a task was part of a build result, and finishes with a given outcome.
     *
     * @param result Build result.
     * @param taskName Task name.
     * @param expectedOutcome Expected outcome.
     */
    public static void assertTaskOutcome(final BuildResult result, final String taskName,
        final TaskOutcome expectedOutcome) {
        assertThat(result.task(':' + taskName).getOutcome()).isEqualTo(expectedOutcome);
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(final File projectDirectory, final Map<String, ?> properties)
        throws IOException {
        createBuildFile(projectDirectory, properties, null);
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final File projectDirectory, final Map<String, ?> properties,
        final String additionalContent) throws IOException {
        final File buildFile = new File(projectDirectory, BUILD_FILE_NAME);
        try (final FileWriter buildFileWriter = new FileWriter(buildFile)) {
            buildFileWriter.append("plugins {\nid 'org.siouan.frontend'\n}\n");
            for (final Map.Entry<String, ?> property : Collections.singletonMap("frontend", properties).entrySet()) {
                writeProperty(buildFileWriter, property.getKey(), property.getValue());
            }
            if (additionalContent != null) {
                buildFileWriter.append(additionalContent);
            }
        }
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @param taskName Task name.
     * @return The build result.
     */
    public static BuildResult runGradle(final File projectDirectory, final String taskName) {
        return createGradleRunner(projectDirectory, taskName).build();
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @param taskName Task name.
     * @return The build result.
     */
    public static BuildResult runGradleAndExpectFailure(final File projectDirectory, final String taskName) {
        return createGradleRunner(projectDirectory, taskName).buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @param taskName Task name.
     * @return The Gradle runner.
     */
    private static GradleRunner createGradleRunner(final File projectDirectory, final String taskName) {
        return GradleRunner.create().withGradleVersion(MINIMAL_GRADLE_VERSION).withProjectDir(projectDirectory)
            .withPluginClasspath().withArguments(taskName, "-s").withDebug(true).forwardOutput();
    }

    private static void writeProperty(final FileWriter buildFileWriter, final String property, final Object value)
        throws IOException {
        buildFileWriter.append(property);
        if (value instanceof Map) {
            buildFileWriter.append(" {\n");
            for (final Object key : ((Map) value).keySet()) {
                writeProperty(buildFileWriter, key.toString(), ((Map) value).get(key));
            }
            buildFileWriter.append("}");
        } else if ((value instanceof Boolean) || (value instanceof Number)) {
            buildFileWriter.append(" = ");
            buildFileWriter.append(value.toString());
        } else {
            buildFileWriter.append(" = '");
            buildFileWriter.append(value.toString());
            buildFileWriter.append('\'');
        }
        buildFileWriter.append('\n');
    }
}
