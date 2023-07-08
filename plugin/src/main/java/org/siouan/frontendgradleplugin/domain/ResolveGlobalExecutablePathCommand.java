package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Command providing parameters to get the path to an executable.
 *
 * @since 7.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResolveGlobalExecutablePathCommand {

    /**
     * Path to the Node.js install directory.
     */
    @EqualsAndHashCode.Include
    private final Path nodeInstallDirectoryPath;

    /**
     * Underlying platform.
     */
    @EqualsAndHashCode.Include
    private final Platform platform;
}
