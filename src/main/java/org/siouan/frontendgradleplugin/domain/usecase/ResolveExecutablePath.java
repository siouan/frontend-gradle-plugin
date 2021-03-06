package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Resolves path to the executable.
 *
 * @since 5.0.1
 */
public class ResolveExecutablePath {

    private final GetNodeExecutablePath getNodeExecutablePath;

    private final GetNpmExecutablePath getNpmExecutablePath;

    private final GetNpxExecutablePath getNpxExecutablePath;

    private final GetYarnExecutablePath getYarnExecutablePath;

    public ResolveExecutablePath(final GetNodeExecutablePath getNodeExecutablePath,
        final GetNpmExecutablePath getNpmExecutablePath, final GetNpxExecutablePath getNpxExecutablePath,
        final GetYarnExecutablePath getYarnExecutablePath) {
        this.getNodeExecutablePath = getNodeExecutablePath;
        this.getNpmExecutablePath = getNpmExecutablePath;
        this.getNpxExecutablePath = getNpxExecutablePath;
        this.getYarnExecutablePath = getYarnExecutablePath;
    }

    /**
     * Resolves the path of the executable.
     *
     * @param executableType Type of executable.
     * @param nodeInstallDirectoryPath Path to the Node install directory.
     * @param yarnInstallDirectoryPath Path to the Yarn install directory.
     * @param platform Underlying platform.
     * @return Executable path.
     * @throws ExecutableNotFoundException If the executable is not found in one of the install directories.
     * @see ExecutableType
     */
    @Nonnull
    public Path execute(@Nonnull final String executableType, @Nullable final Path nodeInstallDirectoryPath,
        @Nullable final Path yarnInstallDirectoryPath, @Nonnull final Platform platform)
        throws ExecutableNotFoundException {
        switch (executableType) {
        case ExecutableType.NODE:
            return getNodeExecutablePath.execute(nodeInstallDirectoryPath, platform);
        case ExecutableType.NPM:
            return getNpmExecutablePath.execute(nodeInstallDirectoryPath, platform);
        case ExecutableType.NPX:
            return getNpxExecutablePath.execute(nodeInstallDirectoryPath, platform);
        case ExecutableType.YARN:
            return getYarnExecutablePath.execute(yarnInstallDirectoryPath, platform);
        default:
            throw new IllegalArgumentException("Unsupported type of execution: " + executableType);
        }
    }
}
