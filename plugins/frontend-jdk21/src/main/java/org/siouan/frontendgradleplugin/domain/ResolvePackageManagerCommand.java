package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

import lombok.Builder;

/**
 * Parameters to resolve the package manager in a project.
 *
 * @param packageJsonFilePath Optional path to the metadata file (i.e. {@code package.json} file).
 * @param nodeInstallDirectoryPath Path to a Node.js install directory.
 * @param platform Underlying platform.
 * @param packageManagerSpecificationFilePath Path to the file providing the name of the package manager.
 * @param packageManagerExecutablePathFilePath Path to the file providing the path to the package manager executable.
 * @since 7.0.0
 */
@Builder
public record ResolvePackageManagerCommand(Path packageJsonFilePath, Path nodeInstallDirectoryPath, Platform platform,
    Path packageManagerSpecificationFilePath, Path packageManagerExecutablePathFilePath) {}
