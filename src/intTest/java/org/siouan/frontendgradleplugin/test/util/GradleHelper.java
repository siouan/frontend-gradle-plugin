package org.siouan.frontendgradleplugin.test.util;

import static java.util.Arrays.asList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

/**
 * Helper to prepare and run Gradle builds.
 */
public final class GradleHelper {

    private static final String MINIMAL_GRADLE_VERSION = "6.1";

    private GradleHelper() {
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
    public static BuildResult runGradle(@Nonnull final Path projectDirectory, @Nullable final Path nodejsHomePath,
        @Nullable final Path nodejsEnvPath, @Nonnull final String... additionalArguments) {
        return runGradle(projectDirectory, LogLevel.LIFECYCLE, nodejsHomePath, nodejsEnvPath, additionalArguments);
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
        return runGradle(projectDirectory, loggingLevel, null, null, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a success.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradle(@Nonnull final Path projectDirectory, @Nonnull final LogLevel loggingLevel,
        @Nullable final Path nodejsHomePath, @Nullable final Path nodejsEnvPath,
        @Nonnull final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, nodejsHomePath, nodejsEnvPath, additionalArguments)
            .build();
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
        return runGradleAndExpectFailure(projectDirectory, LogLevel.LIFECYCLE, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradleAndExpectFailure(@Nonnull final Path projectDirectory,
        @Nonnull final LogLevel loggingLevel, @Nonnull final String... additionalArguments) {
        return runGradleAndExpectFailure(projectDirectory, loggingLevel, null, null, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradleAndExpectFailure(@Nonnull final Path projectDirectory,
        @Nullable final Path nodejsHomePath, @Nullable final Path nodejsEnvPath,
        @Nonnull final String... additionalArguments) {
        return runGradleAndExpectFailure(projectDirectory, LogLevel.LIFECYCLE, nodejsHomePath, nodejsEnvPath,
            additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradleAndExpectFailure(@Nonnull final Path projectDirectory,
        @Nonnull final LogLevel loggingLevel, @Nullable final Path nodejsHomePath, @Nullable final Path nodejsEnvPath,
        @Nonnull final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, nodejsHomePath, nodejsEnvPath, additionalArguments)
            .buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @param loggingLevel Gradle logging level.
     * @param nodejsHomePath Optional path to set the {@link FrontendGradlePlugin#NODEJS_HOME_ENV_VAR} environment
     * variable.
     * @param nodejsEnvPath Optional path to preprend to the {@code PATH} environment variable.
     * @return The Gradle runner.
     */
    @Nonnull
    private static GradleRunner createGradleRunner(@Nonnull final Path projectDirectory,
        @Nonnull final LogLevel loggingLevel, @Nullable final Path nodejsHomePath, @Nullable final Path nodejsEnvPath,
        @Nonnull final String... additionalArguments) {
        final List<String> arguments = new ArrayList<>();
        arguments.add("-s");
        toLoggingLevelProperty(loggingLevel).ifPresent(arguments::add);
        arguments.addAll(asList(additionalArguments));
        final Map<String, String> originalEnvironment = System.getenv();
        final Map<String, String> additionalEnvironment = new HashMap<>();
        if (nodejsHomePath != null) {
            if (Files.isDirectory(nodejsHomePath)) {
                additionalEnvironment.put(FrontendGradlePlugin.NODEJS_HOME_ENV_VAR, nodejsHomePath.toString());
            } else if (Files.isRegularFile(nodejsHomePath)) {
                additionalEnvironment.put(FrontendGradlePlugin.NODEJS_HOME_ENV_VAR,
                    nodejsHomePath.getParent().toString());
            }
        }
        if (nodejsEnvPath != null) {
            final String pathEnvironmentVariable = originalEnvironment.get("PATH");
            if (Files.isDirectory(nodejsEnvPath)) {
                additionalEnvironment.put("PATH", nodejsEnvPath + File.pathSeparator + pathEnvironmentVariable);
            } else if (Files.isRegularFile(nodejsEnvPath)) {
                additionalEnvironment.put("PATH",
                    nodejsEnvPath.getParent() + File.pathSeparator + pathEnvironmentVariable);
            }
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
            gradleRunner.withEnvironment(testEnvironment);
        }
        return gradleRunner;
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
