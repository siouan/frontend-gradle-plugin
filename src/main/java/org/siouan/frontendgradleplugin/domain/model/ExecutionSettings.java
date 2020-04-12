package org.siouan.frontendgradleplugin.domain.model;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * Settings to execute a process.
 *
 * @since 2.0.0
 */
public class ExecutionSettings {

    /**
     * Path to the current working directory used at execution.
     */
    private final Path workingDirectoryPath;

    /**
     * Set of directory paths to find additional programs required at execution.
     */
    private final Set<Path> additionalExecutablePaths;

    /**
     * Path to the program to execute.
     */
    private final Path executablePath;

    /**
     * List of arguments.
     */
    private final List<String> arguments;

    /**
     * Builds execution settings.
     *
     * @param workingDirectoryPath Path to the current working directory used at execution.
     * @param additionalExecutablePaths Set of paths of directories to find additional programs required at execution.
     * @param executablePath Path to the program to execute.
     * @param arguments List of arguments.
     */
    public ExecutionSettings(@Nonnull final Path workingDirectoryPath,
        @Nonnull final Set<Path> additionalExecutablePaths, @Nonnull final Path executablePath,
        @Nonnull final List<String> arguments) {
        this.workingDirectoryPath = workingDirectoryPath;
        this.additionalExecutablePaths = unmodifiableSet(additionalExecutablePaths);
        this.executablePath = executablePath;
        this.arguments = unmodifiableList(arguments);
    }

    /**
     * Gets the path to the current working directory used at execution.
     *
     * @return Path.
     */
    @Nonnull
    public Path getWorkingDirectoryPath() {
        return workingDirectoryPath;
    }

    /**
     * Gets the set of paths of directories to find additional programs required at execution.
     *
     * @return Set of paths.
     */
    @Nonnull
    public Set<Path> getAdditionalExecutablePaths() {
        return additionalExecutablePaths;
    }

    /**
     * Gets the path to the program to execute.
     *
     * @return Path.
     */
    @Nonnull
    public Path getExecutablePath() {
        return executablePath;
    }

    /**
     * Gets the list of arguments.
     *
     * @return List of arguments.
     */
    @Nonnull
    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return ExecutionSettings.class.getSimpleName() + " {workingDirectoryPath=" + workingDirectoryPath
            + ", executablePaths=[" + additionalExecutablePaths.stream().map(Path::toString).collect(joining(", "))
            + "], executable=" + executablePath + ", arguments=[" + String.join(", ", arguments) + "]}";
    }
}
