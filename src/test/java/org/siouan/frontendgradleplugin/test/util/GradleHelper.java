package org.siouan.frontendgradleplugin.test.util;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

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

    private static final String MINIMAL_GRADLE_VERSION = "5.1";

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
    public static BuildResult runGradle(@Nonnull final Path projectDirectory, @Nullable final Path nodejsPath,
        @Nullable final Path yarnPath, @Nonnull final String... additionalArguments) {
        return runGradle(projectDirectory, LogLevel.LIFECYCLE, nodejsPath, yarnPath, additionalArguments);
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
        @Nullable final Path nodejsPath, @Nullable final Path yarnPath, @Nonnull final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, nodejsPath, yarnPath, additionalArguments).build();
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
        return runGradleAndExpectFailure(projectDirectory, LogLevel.LIFECYCLE, null, null, additionalArguments);
    }

    /**
     * Runs a Gradle task in the given project directory, and expects a failure.
     *
     * @param projectDirectory Project directory.
     * @return The build result.
     */
    @Nonnull
    public static BuildResult runGradleAndExpectFailure(@Nonnull final Path projectDirectory,
        @Nullable final Path nodejsPath, @Nullable final Path yarnPath, @Nonnull final String... additionalArguments) {
        return runGradleAndExpectFailure(projectDirectory, LogLevel.LIFECYCLE, nodejsPath, yarnPath,
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
        @Nonnull final LogLevel loggingLevel, @Nullable final Path nodejsPath, @Nullable final Path yarnPath,
        @Nonnull final String... additionalArguments) {
        return createGradleRunner(projectDirectory, loggingLevel, nodejsPath, yarnPath,
            additionalArguments).buildAndFail();
    }

    /**
     * Creates a Gradle build that will run a task in the given project directory.
     *
     * @param projectDirectory Project directory.
     * @return The Gradle runner.
     */
    @Nonnull
    private static GradleRunner createGradleRunner(@Nonnull final Path projectDirectory,
        @Nonnull final LogLevel loggingLevel, @Nullable final Path nodejsPath, @Nullable final Path yarnPath,
        @Nonnull final String... additionalArguments) {
        final List<String> arguments = new ArrayList<>();
        arguments.add("-s");
        toLoggingLevelProperty(loggingLevel).ifPresent(arguments::add);
        arguments.addAll(asList(additionalArguments));
        final Map<String, String> environment = new HashMap<>();
        final List<Path> paths = new ArrayList<>();
        if (nodejsPath != null) {
            if (Files.isDirectory(nodejsPath)) {
                environment.put(FrontendGradlePlugin.NODEJS_HOME_ENV_VAR, nodejsPath.toString());
            } else {
                paths.add(nodejsPath.getParent());
            }
        }
        if (yarnPath != null) {
            if (Files.isDirectory(yarnPath)) {
                environment.put(FrontendGradlePlugin.YARN_HOME_ENV_VAR, yarnPath.toString());
            } else {
                paths.add(yarnPath.getParent());
            }
        }
        if (!paths.isEmpty()) {
            environment.put("PATH",
                paths.stream().map(Path::toString).collect(joining(File.pathSeparator)) + File.pathSeparator
                    + System.getenv("PATH"));
        }
        final GradleRunner gradleRunner = GradleRunner.create()
            .withGradleVersion(MINIMAL_GRADLE_VERSION)
            .withProjectDir(projectDirectory.toFile())
            .withPluginClasspath()
            .withArguments(arguments)
            .withDebug(environment.isEmpty())
            .forwardOutput();
        if (!environment.isEmpty()) {
            gradleRunner.withEnvironment(environment);
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
