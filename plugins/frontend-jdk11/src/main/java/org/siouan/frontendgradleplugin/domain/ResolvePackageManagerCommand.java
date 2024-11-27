package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;

/**
 * Parameters to resolve the package manager in a project.
 *
 * @since 7.0.0
 */
@Builder
@Getter
public class ResolvePackageManagerCommand {

    /**
     * Path to the metadata file (i.e. {@code package.json} file).
     */
    private final Path packageJsonFilePath;

    /**
     * Path to a Node.js install directory.
     */
    private final Path nodeInstallDirectoryPath;

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Path to the file providing the name of the package manager.
     */
    private final Path packageManagerSpecificationFilePath;

    /**
     * Path to the file providing the path to the package manager executable.
     */
    private final Path packageManagerExecutablePathFilePath;
}
