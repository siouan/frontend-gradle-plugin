package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Settings to execute a process.
 *
 * @since 2.0.0
 */
@Builder
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class ExecutionSettings {

    /**
     * Path to the current working directory used at execution.
     */
    @ToString.Include
    private final Path workingDirectoryPath;

    /**
     * Set of directory paths to find additional programs required at execution.
     */
    @ToString.Include
    private final Set<Path> additionalExecutablePaths;

    /**
     * Path to the program to execute.
     */
    @ToString.Include
    private final Path executablePath;

    /**
     * List of arguments.
     */
    @ToString.Include
    private final List<String> arguments;

    /**
     * Builds execution settings.
     *
     * @param workingDirectoryPath Path to the current working directory used at execution.
     * @param additionalExecutablePaths Set of paths of directories to find additional programs required at execution.
     * @param executablePath Path to the program to execute.
     * @param arguments List of arguments.
     */
    public ExecutionSettings(final Path workingDirectoryPath, final Set<Path> additionalExecutablePaths,
        final Path executablePath, final List<String> arguments) {
        this.workingDirectoryPath = workingDirectoryPath;
        this.additionalExecutablePaths = Set.copyOf(additionalExecutablePaths);
        this.executablePath = executablePath;
        this.arguments = List.copyOf(arguments);
    }
}
