package org.siouan.frontendgradleplugin.test;

import static java.util.Arrays.asList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;

/**
 * Helper to prepare and run Gradle builds.
 */
public final class GradleHelper {

    private static final String MINIMAL_GRADLE_VERSION = "8.5";

    private GradleHelper() {
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradle(final Path projectDirectory, final String... additionalArguments) {
        return runGradle(projectDirectory, LogLevel.LIFECYCLE, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradle(final Path projectDirectory, final LogLevel loggingLevel,
        final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, additionalArguments).build();
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradleAndExpectFailure(final Path projectDirectory,
        final String... additionalArguments) {
        return runGradleAndExpectFailure(projectDirectory, LogLevel.LIFECYCLE, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradleAndExpectFailure(final Path projectDirectory, final LogLevel loggingLevel,
        final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, additionalArguments).buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @param loggingLevel Gradle logging level.
     * @return The Gradle runner.
     */
    private static GradleRunner createGradleRunner(final Path projectDirectory, final LogLevel loggingLevel,
        final String... additionalArguments) {
        final List<String> arguments = new ArrayList<>();
        arguments.add("-s");
        arguments.add("--warning-mode=all");
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

    private static Optional<String> toLoggingLevelProperty(final LogLevel loggingLevel) {
        final String property = switch (loggingLevel) {
            case DEBUG -> "-d";
            case INFO -> "-i";
            case WARN -> "-w";
            case ERROR -> "-e";
            case QUIET -> "-q";
            default -> null;
        };
        return Optional.ofNullable(property);
    }
}
