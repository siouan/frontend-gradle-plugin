package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Query parameters to resolve the package manager in a project.
 *
 * @since 7.0.0
 */
public class ResolvePackageManagerQuery {

    private final Path metadataFilePath;

    private final Path nodeInstallDirectoryPath;

    private final Platform platform;

    private final Path packageManagerDescriptorFilePath;

    private final Path packageManagerExecutablePathFilePath;

    public ResolvePackageManagerQuery(@Nonnull final Path metadataFilePath,
        @Nonnull final Path nodeInstallDirectoryPath, @Nonnull final Platform platform,
        @Nonnull final Path packageManagerDescriptorFilePath,
        @Nonnull final Path packageManagerExecutablePathFilePath) {
        this.metadataFilePath = metadataFilePath;
        this.nodeInstallDirectoryPath = nodeInstallDirectoryPath;
        this.platform = platform;
        this.packageManagerDescriptorFilePath = packageManagerDescriptorFilePath;
        this.packageManagerExecutablePathFilePath = packageManagerExecutablePathFilePath;
    }

    @Nonnull
    public Path getMetadataFilePath() {
        return metadataFilePath;
    }

    @Nonnull
    public Path getNodeInstallDirectoryPath() {
        return nodeInstallDirectoryPath;
    }

    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    @Nonnull
    public Path getPackageManagerNameFilePath() {
        return packageManagerDescriptorFilePath;
    }

    @Nonnull
    public Path getPackageManagerExecutablePathFilePath() {
        return packageManagerExecutablePathFilePath;
    }
}
