package org.siouan.frontendgradleplugin.util;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.gradle.api.logging.LogLevel;
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
     * Gets the task ID for future reference with Gradle API.
     *
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(final String taskName) {
        return ':' + taskName;
    }

    /**
     * Asserts a task was part of a build result, and was executed successfully.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskSuccess(final BuildResult result, final String taskName) {
        assertTaskOutcome(result, taskName, TaskOutcome.SUCCESS);
    }

    /**
     * Asserts a task was part of a build result, and was not executed as it is already up-to-date.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskUpToDate(final BuildResult result, final String taskName) {
        assertTaskOutcome(result, taskName, TaskOutcome.UP_TO_DATE);
    }

    /**
     * Asserts a task was part of a build result, and was skipped.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskSkipped(final BuildResult result, final String taskName) {
        assertTaskOutcome(result, taskName, TaskOutcome.SKIPPED);
    }

    /**
     * Asserts a task was part of a build result, and failed.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskFailed(final BuildResult result, final String taskName) {
        assertTaskOutcome(result, taskName, TaskOutcome.FAILED);
    }

    /**
     * Asserts a task was not part of a build.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskIgnored(final BuildResult result, final String taskName) {
        assertThat(getBuildResultTask(result, taskName)).isEmpty();
    }

    /**
     * Asserts a task was part of a build result, and finishes with a given outcome.
     *
     * @param result Build result.
     * @param taskName Task name.
     * @param expectedOutcome Expected outcome.
     */
    private static void assertTaskOutcome(final BuildResult result, final String taskName,
        final TaskOutcome expectedOutcome) {
        assertThat(getBuildResultTask(result, taskName)
            .map(BuildTask::getOutcome)
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
        createBuildFile(projectDirectory, emptySet(), properties, null);
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Set<String> plugins,
        final Map<String, ?> properties) throws IOException {
        createBuildFile(projectDirectory, plugins, properties, null);
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory,
        final Map<String, ?> properties, final String additionalContent) throws IOException {
        createBuildFile(projectDirectory, emptySet(), properties, additionalContent);
    }

    /**
     * Creates a build file to test this plugin, with the given properties.
     *
     * @param projectDirectory Project directory.
     * @param plugins Set of plugin definitions.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Set<String> plugins,
        final Map<String, ?> properties, final String additionalContent) throws IOException {
        final Path buildFile = projectDirectory.resolve(BUILD_FILE_NAME);
        try (final Writer buildFileWriter = Files.newBufferedWriter(buildFile)) {
            final Map<String, ?> pluginsBlock = new HashMap<>();
            pluginsBlock.put("id 'org.siouan.frontend'", null);
            plugins.forEach(p -> pluginsBlock.put(p, null));
            for (final Map.Entry<String, ?> property : singletonMap("plugins", pluginsBlock).entrySet()) {
                writeProperty(buildFileWriter, property.getKey(), property.getValue());
            }
            for (final Map.Entry<String, ?> property : singletonMap("frontend", properties).entrySet()) {
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
        return GradleRunner
            .create()
            .withGradleVersion(MINIMAL_GRADLE_VERSION)
            .withProjectDir(projectDirectory.toFile())
            .withPluginClasspath()
            .withArguments(taskName, "-s")
            .withDebug(true)
            .forwardOutput();
    }

    private static void writeProperty(final Writer buildFileWriter, final String property, final Object value)
        throws IOException {
        buildFileWriter.append(property);
        if (value instanceof Map) {
            buildFileWriter.append(" {\n");
            for (final Object key : ((Map<?, ?>) value).keySet()) {
                writeProperty(buildFileWriter, key.toString(), ((Map<?, ?>) value).get(key));
            }
            buildFileWriter.append("}");
        } else if ((value instanceof Boolean) || (value instanceof Number)) {
            buildFileWriter.append(" = ");
            buildFileWriter.append(value.toString());
        } else if (value instanceof Path) {
            buildFileWriter.append(" = file('");
            buildFileWriter.append(value.toString().replace('\\', '/'));
            buildFileWriter.append("')");
        } else if (value instanceof LogLevel) {
            buildFileWriter.append(" = ").append(LogLevel.class.getSimpleName()).append('.').append(value.toString());
        } else if (value != null) {
            buildFileWriter.append(" = '");
            buildFileWriter.append(value.toString());
            buildFileWriter.append('\'');
        }
        buildFileWriter.append('\n');
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
            for (final Path childFile : childFileStream) {
                size++;
            }
        }
        return size;
    }

    private static Optional<BuildTask> getBuildResultTask(final BuildResult result, final String taskName) {
        return Optional.ofNullable(result.task(getTaskId(taskName)));
    }
}
