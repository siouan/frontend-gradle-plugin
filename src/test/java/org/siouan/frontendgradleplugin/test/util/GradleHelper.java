package org.siouan.frontendgradleplugin.test.util;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

/**
 * Helper to prepare and run Gradle builds.
 */
public final class GradleHelper {

    private static final String BUILD_FILE_NAME = "build.gradle";

    private static final String SETTINGS_FILE_NAME = "settings.gradle";

    private static final String MINIMAL_GRADLE_VERSION = "5.1";

    private GradleHelper() {
    }

    /**
     * Gets the task ID for future reference with Gradle API.
     *
     * @param projectName Project name.
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(@Nullable final String projectName, @Nonnull final String taskName) {
        final String taskId = getTaskId(taskName);
        return (projectName == null) ? taskId : ':' + projectName + taskId;
    }

    /**
     * Gets the task ID for future reference with Gradle API.
     *
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(@Nonnull final String taskName) {
        return ':' + taskName;
    }

    /**
     * Asserts a task was part of a build result, and was executed successfully.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskSuccess(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.SUCCESS);
    }

    /**
     * Asserts a task was part of a build result, and was executed successfully.
     *
     * @param result Build result.
     * @param projectName Project name.
     * @param taskName Task name.
     */
    public static void assertTaskSuccess(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName) {
        assertTaskOutcome(result, projectName, taskName, TaskOutcome.SUCCESS);
    }

    /**
     * Asserts a task was part of a build result, and was not executed as it is already up-to-date.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskUpToDate(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.UP_TO_DATE);
    }

    /**
     * Asserts a task was part of a build result, and was not executed as it is already up-to-date.
     *
     * @param result Build result.
     * @param projectName Project name.
     * @param taskName Task name.
     */
    public static void assertTaskUpToDate(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName) {
        assertTaskOutcome(result, projectName, taskName, TaskOutcome.UP_TO_DATE);
    }

    /**
     * Asserts a task was part of a build result, and was skipped.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskSkipped(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.SKIPPED);
    }

    /**
     * Asserts a task was part of a build result, and was skipped.
     *
     * @param result Build result.
     * @param projectName Project name.
     * @param taskName Task name.
     */
    public static void assertTaskSkipped(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName) {
        assertTaskOutcome(result, projectName, taskName, TaskOutcome.SKIPPED);
    }

    /**
     * Asserts a task was part of a build result, and failed.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskFailed(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.FAILED);
    }

    /**
     * Asserts a task was not part of a build.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskIgnored(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertThat(getBuildResultTask(result, getTaskId(taskName))).isEmpty();
    }

    /**
     * Asserts a task was part of a build result, and finishes with a given outcome.
     *
     * @param result Build result.
     * @param taskName Task name.
     * @param expectedOutcome Expected outcome.
     */
    private static void assertTaskOutcome(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName, @Nonnull final TaskOutcome expectedOutcome) {
        assertThat(getBuildResultTask(result, getTaskId(projectName, taskName))
            .map(BuildTask::getOutcome)
            .orElseThrow(() -> new RuntimeException("Task not found: " + taskName))).isEqualTo(expectedOutcome);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, @Nonnull final Map<String, ?> properties)
        throws IOException {
        createBuildFile(projectDirectory, true, true, emptySet(), properties, null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, @Nullable final String additionalContent)
        throws IOException {
        createBuildFile(projectDirectory, true, true, emptySet(), emptyMap(), additionalContent);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param pluginEnabled Whether this plugin must be added in the 'plugins' block.
     * @param pluginApplied Whether this plugin must be applied (only relevant if the {@code pluginEnabled} parameter is
     * {@code true}.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, final boolean pluginEnabled,
        final boolean pluginApplied) throws IOException {
        createBuildFile(projectDirectory, pluginEnabled, pluginApplied, emptySet(), emptyMap(), null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, @Nonnull final Set<String> plugins,
        @Nonnull final Map<String, ?> properties) throws IOException {
        createBuildFile(projectDirectory, plugins, properties, null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, @Nonnull final Map<String, ?> properties,
        @Nullable final String additionalContent) throws IOException {
        createBuildFile(projectDirectory, true, true, emptySet(), properties, additionalContent);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param pluginEnabled Whether this plugin must be added in the 'plugins' block.
     * @param pluginApplied Whether this plugin must be applied (only relevant if the {@code pluginEnabled} parameter is
     * {@code true}.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, final boolean pluginEnabled,
        final boolean pluginApplied, @Nonnull final Map<String, ?> properties) throws IOException {
        createBuildFile(projectDirectory, pluginEnabled, pluginApplied, emptySet(), properties, null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param pluginEnabled Whether this plugin must be added in the 'plugins' block.
     * @param pluginApplied Whether this plugin must be applied (only relevant if the {@code pluginEnabled} parameter is
     * {@code true}.
     * @param plugins Set of additional plugin definitions.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, final boolean pluginEnabled,
        final boolean pluginApplied, @Nonnull final Set<String> plugins, @Nonnull final Map<String, ?> properties,
        @Nullable final String additionalContent) throws IOException {
        final Set<String> pluginsWithFrontend = new HashSet<>(plugins);
        if (pluginEnabled) {
            pluginsWithFrontend.add("id 'org.siouan.frontend' apply " + pluginApplied);
        }
        createBuildFile(projectDirectory, pluginsWithFrontend, properties, additionalContent);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param plugins Set of plugin definitions.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(@Nonnull final Path projectDirectory, @Nonnull final Set<String> plugins,
        @Nonnull final Map<String, ?> properties, @Nullable final String additionalContent) throws IOException {
        final Path buildFilePath = projectDirectory.resolve(BUILD_FILE_NAME);
        try (final Writer buildFileWriter = Files.newBufferedWriter(buildFilePath)) {
            if (!plugins.isEmpty()) {
                final Map<String, ?> pluginsBlock = new HashMap<>();
                plugins.forEach(p -> pluginsBlock.put(p, null));
                for (final Map.Entry<String, ?> property : singletonMap("plugins", pluginsBlock).entrySet()) {
                    writeProperty(buildFileWriter, property.getKey(), property.getValue());
                }
            }
            if (!properties.isEmpty()) {
                for (final Map.Entry<String, ?> property : singletonMap("frontend", properties).entrySet()) {
                    writeProperty(buildFileWriter, property.getKey(), property.getValue());
                }
            }
            if (additionalContent != null) {
                buildFileWriter.append(additionalContent);
            }
        }
    }

    /**
     * Creates a settings file with the given content.
     *
     * @param projectDirectory Project directory.
     * @param subProjectNames Names of sub-projects.
     */
    public static void createSettingsFile(@Nonnull final Path projectDirectory, @Nonnull final String rootProjectName,
        @Nonnull final String... subProjectNames) throws IOException {
        final Path settingsFilePath = projectDirectory.resolve(SETTINGS_FILE_NAME);
        try (final Writer settingsFileWriter = Files.newBufferedWriter(settingsFilePath)) {
            settingsFileWriter.append("rootProject.name = '");
            settingsFileWriter.append(rootProjectName);
            settingsFileWriter.append("'\n");
            for (final String subProjectName : subProjectNames) {
                settingsFileWriter.append("include '");
                settingsFileWriter.append(subProjectName);
                settingsFileWriter.append("'\n");
            }
        }
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradle(@Nonnull final Path projectDirectory,
        @Nonnull final String... additionalArguments) {
        return runGradle(projectDirectory, LogLevel.LIFECYCLE, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradle(@Nonnull final Path projectDirectory, @Nonnull final LogLevel loggingLevel,
        @Nonnull final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, additionalArguments).build();
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradleAndExpectFailure(@Nonnull final Path projectDirectory,
        @Nonnull final String... additionalArguments) {
        return createGradleRunner(projectDirectory, LogLevel.LIFECYCLE, additionalArguments).buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @return The Gradle runner.
     */
    @Nonnull
    private static GradleRunner createGradleRunner(@Nonnull final Path projectDirectory,
        @Nonnull final LogLevel loggingLevel, @Nonnull final String... additionalArguments) {
        final List<String> arguments = new ArrayList<>();
        arguments.add("-s");
        toLoggingLevelProperty(loggingLevel).ifPresent(arguments::add);
        arguments.addAll(asList(additionalArguments));
        return GradleRunner
            .create()
            .withGradleVersion(MINIMAL_GRADLE_VERSION)
            .withProjectDir(projectDirectory.toFile())
            .withPluginClasspath()
            .withArguments(arguments)
            .withDebug(true)
            .forwardOutput();
    }

    private static void writeProperty(@Nonnull final Writer buildFileWriter, @Nonnull final String property,
        @Nullable final Object value) throws IOException {
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
            buildFileWriter.append(" = file(\"");
            buildFileWriter.append(value.toString().replace('\\', '/'));
            buildFileWriter.append("\")");
        } else if (value instanceof LogLevel) {
            buildFileWriter.append(" = ").append(LogLevel.class.getSimpleName()).append('.').append(value.toString());
        } else if (value != null) {
            buildFileWriter.append(" = '");
            buildFileWriter.append(value.toString());
            buildFileWriter.append('\'');
        }
        buildFileWriter.append('\n');
    }

    @Nonnull
    private static Optional<BuildTask> getBuildResultTask(@Nonnull final BuildResult result,
        @Nonnull final String taskId) {
        return Optional.ofNullable(result.task(taskId));
    }

    @Nonnull
    private static Optional<String> toLoggingLevelProperty(@Nonnull final LogLevel loggingLevel) {
        final String property;
        switch (loggingLevel) {
        case DEBUG:
            property = "-d";
            break;
        case INFO:
            property = "-i";
            break;
        case WARN:
            property = "-w";
            break;
        case ERROR:
            property = "-e";
            break;
        case QUIET:
            property = "-q";
            break;
        case LIFECYCLE:
        default:
            property = null;
            break;
        }
        return Optional.ofNullable(property);
    }
}
