package org.siouan.frontendgradleplugin.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

/**
 * Helper to prepare tasks tests.
 */
public final class Helper {

    private static final String BUILD_FILE_NAME = "build.gradle";

    private static final String MINIMAL_GRADLE_VERSION = "5.1";

    private Helper() {
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
        assertThat(Optional.ofNullable(result.task(':' + taskName)).map(BuildTask::getOutcome)
            .orElseThrow(() -> new RuntimeException("Task not found: " + taskName))).isEqualTo(expectedOutcome);
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Map<String, ?> properties)
        throws IOException {
        createBuildFile(projectDirectory, properties, null);
    }

    /**
     * Gets the size of a directory.
     *
     * @param directory Directory
     * @return Number of files in the directory.
     * @throws IOException If an I/O error occurs.
     */
    public static int getDirectorySize(final Path directory) throws IOException {
        int size = 0;
        try (final DirectoryStream<Path> childFileStream = Files.newDirectoryStream(directory)) {
            final Iterator<Path> childFileIterator = childFileStream.iterator();
            while (childFileIterator.hasNext()) {
                size++;
                childFileIterator.next();
            }
        }
        return size;
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Map<String, ?> properties,
        final String additionalContent) throws IOException {
        final Path buildFile = projectDirectory.resolve(BUILD_FILE_NAME);
        try (final Writer buildFileWriter = Files.newBufferedWriter(buildFile)) {
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
    public static BuildResult runGradle(final Path projectDirectory, final String taskName) {
        return createGradleRunner(projectDirectory, taskName).build();
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @param taskName Task name.
     * @return The build result.
     */
    public static BuildResult runGradleAndExpectFailure(final Path projectDirectory, final String taskName) {
        return createGradleRunner(projectDirectory, taskName).buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @param taskName Task name.
     * @return The Gradle runner.
     */
    private static GradleRunner createGradleRunner(final Path projectDirectory, final String taskName) {
        return GradleRunner.create().withGradleVersion(MINIMAL_GRADLE_VERSION).withProjectDir(projectDirectory.toFile())
            .withPluginClasspath().withArguments(taskName, "-s").withDebug(true).forwardOutput();
    }

    private static void writeProperty(final Writer buildFileWriter, final String property, final Object value)
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
