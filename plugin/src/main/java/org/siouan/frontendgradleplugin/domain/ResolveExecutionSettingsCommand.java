package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

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
}
