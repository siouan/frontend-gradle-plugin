package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;
import java.util.Map;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Command providing parameters to resolve execution parameters of a script.
 *
 * @since 7.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResolveExecutionSettingsCommand {

    /**
     * Path to the {@code package.json} file, used as the working directory.
     */
    @EqualsAndHashCode.Include
    private final Path packageJsonDirectoryPath;

    /**
     * Type of executable.
     */
    @EqualsAndHashCode.Include
    private final ExecutableType executableType;

    /**
     * Path to the Node install directory.
     */
    @EqualsAndHashCode.Include
    private final Path nodeInstallDirectoryPath;

    /**
     * Underlying platform.
     */
    @EqualsAndHashCode.Include
    private final Platform platform;

    /**
     * Script.
     */
    @EqualsAndHashCode.Include
    private final String script;

    /**
     * Additional environment variables to pass to the process.
     *
     * @since 8.1.0
     */
    @EqualsAndHashCode.Include
    private final Map<String, String> environmentVariables;
}
