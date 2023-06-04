package org.siouan.frontendgradleplugin.test;

import static java.util.Arrays.asList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

/**
 * Helper to prepare and run Gradle builds.
 */
public final class GradleHelper {

    private static final String MINIMAL_GRADLE_VERSION = "6.6";

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
    public static BuildResult runGradle(final Path projectDirectory, final Path nodejsHomePath,
        final String... additionalArguments) {
        return runGradle(projectDirectory, LogLevel.LIFECYCLE, nodejsHomePath, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradle(final Path projectDirectory, final LogLevel loggingLevel,
        final String... additionalArguments) {
        return runGradle(projectDirectory, loggingLevel, null, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradle(final Path projectDirectory, final LogLevel loggingLevel,
        final Path nodejsHomePath, final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, nodejsHomePath, additionalArguments).build();
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
        return runGradleAndExpectFailure(projectDirectory, loggingLevel, null, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradleAndExpectFailure(final Path projectDirectory, final Path nodejsHomePath,
        final String... additionalArguments) {
        return runGradleAndExpectFailure(projectDirectory, LogLevel.LIFECYCLE, nodejsHomePath, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    public static BuildResult runGradleAndExpectFailure(final Path projectDirectory, final LogLevel loggingLevel,
        final Path nodejsHomePath, final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, nodejsHomePath, additionalArguments).buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @param loggingLevel Gradle logging level.
     * @param nodejsHomePath Optional path to set the {@link FrontendGradlePlugin#NODEJS_HOME_ENV_VAR} environment
     * variable.
     * @return The Gradle runner.
     */
    private static GradleRunner createGradleRunner(final Path projectDirectory, final LogLevel loggingLevel,
        final Path nodejsHomePath, final String... additionalArguments) {
        final List<String> arguments = new ArrayList<>();
        arguments.add("-s");
        arguments.add("--warning-mode=all");
        toLoggingLevelProperty(loggingLevel).ifPresent(arguments::add);
        arguments.addAll(asList(additionalArguments));
        final Map<String, String> originalEnvironment = System.getenv();
        final Map<String, String> additionalEnvironment = new HashMap<>();
        if (nodejsHomePath != null) {
            additionalEnvironment.put(FrontendGradlePlugin.NODEJS_HOME_ENV_VAR, nodejsHomePath.toString());
        }
        final GradleRunner gradleRunner = GradleRunner
            .create()
            .withGradleVersion(MINIMAL_GRADLE_VERSION)
            .withProjectDir(projectDirectory.toFile())
            .withPluginClasspath()
            .withArguments(arguments)
            .withDebug(additionalEnvironment.isEmpty())
            .forwardOutput();
        if (!additionalEnvironment.isEmpty()) {
            final Map<String, String> testEnvironment = new HashMap<>(originalEnvironment);
            testEnvironment.putAll(additionalEnvironment);
            gradleRunner.withEnvironment(Map.copyOf(testEnvironment));
        }
        return gradleRunner;
    }

    private static Optional<String> toLoggingLevelProperty(final LogLevel loggingLevel) {
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
