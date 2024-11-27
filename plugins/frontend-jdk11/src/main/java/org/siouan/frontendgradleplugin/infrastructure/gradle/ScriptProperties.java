package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Properties to run a script.
 *
 * @since 2.0.0
 */
@Builder
@Getter
public class ScriptProperties {

    /**
     * Gradle exec operations.
     */
    private final ExecOperations execOperations;

    /**
     * Path to the directory containing the {@code package.json} file.
     */
    private final Path packageJsonDirectoryPath;

    /**
     * Executable use to run the script.
     */
    private final ExecutableType executableType;

    /**
     * Directory where the Node distribution is installed.
     */
    private final Path nodeInstallDirectoryPath;

    /**
     * Arguments to be appended to the executable name on the command line.
     */
    private final String executableArgs;

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Additional environment variables to pass to the process.
     *
     * @since 8.1.0
     */
    private final Map<String, String> environmentVariables;

    ScriptProperties(final ExecOperations execOperations, final Path packageJsonDirectoryPath,
        final ExecutableType executableType, final Path nodeInstallDirectoryPath, final String executableArgs,
        final Platform platform, final Map<String, String> environmentVariables) {
        this.execOperations = execOperations;
        this.packageJsonDirectoryPath = packageJsonDirectoryPath;
        this.executableType = executableType;
        this.nodeInstallDirectoryPath = nodeInstallDirectoryPath;
        this.executableArgs = executableArgs;
        this.platform = platform;
        this.environmentVariables = Map.copyOf(environmentVariables);
    }
}
