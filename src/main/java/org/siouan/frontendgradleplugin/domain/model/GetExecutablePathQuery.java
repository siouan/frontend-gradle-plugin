package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Query containing parameters to get the path to an executable.
 *
 * @since 7.0.0
 */
public class GetExecutablePathQuery {

    private final ExecutableType executableType;

    private final Path nodeInstallDirectoryPath;

    private final Platform platform;

    public GetExecutablePathQuery(@Nonnull final ExecutableType executableType,
        @Nonnull final Path nodeInstallDirectoryPath, @Nonnull final Platform platform) {
        this.executableType = executableType;
        this.nodeInstallDirectoryPath = nodeInstallDirectoryPath;
        this.platform = platform;
    }

    @Nonnull
    public ExecutableType getExecutableType() {
        return executableType;
    }

    @Nonnull
    public Path getNodeInstallDirectoryPath() {
        return nodeInstallDirectoryPath;
    }

    @Nonnull
    public Platform getPlatform() {
        return platform;
    }
}
