package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Gets the path to an executable given a Node.js install directory and a platform.
 *
 * @since 2.0.0
 */
public abstract class AbstractGetExecutablePath {

    private final Logger logger;

    protected AbstractGetExecutablePath(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Gets the executable path.
     *
     * @param nodeInstallDirectoryPath Path to the Node.js install directory.
     * @param platform Underlying platform.
     * @return The path. If the path is a single file name, it means it must be resolved using the PATH environment
     * variable.
     */
    @Nonnull
    public Path execute(@Nonnull final Path nodeInstallDirectoryPath, @Nonnull final Platform platform) {
        final Path relativeExecutablePath;
        if (platform.isWindowsOs()) {
            relativeExecutablePath = getWindowsRelativeExecutablePath();
        } else {
            relativeExecutablePath = getNonWindowsRelativeExecutablePath();
        }

        final Path executablePath = nodeInstallDirectoryPath.resolve(relativeExecutablePath);
        logger.info("Resolved executable path: '{}'", executablePath);
        return executablePath;
    }

    /**
     * Gets the executable path on Windows O/S, relative to the installation directory.
     *
     * @return Relative path.
     */
    @Nonnull
    protected abstract Path getWindowsRelativeExecutablePath();

    /**
     * Gets the executable path on non-Windows O/S, relative to the installation directory.
     *
     * @return Relative path.
     */
    @Nonnull
    protected abstract Path getNonWindowsRelativeExecutablePath();

    /**
     * Gets the executable file name on Windows O/S.
     *
     * @return File name.
     */
    @Nonnull
    protected abstract Path getWindowsExecutableFileName();

    /**
     * Gets the executable file name on non-Windows O/S.
     *
     * @return File name.
     */
    @Nonnull
    protected abstract Path getNonWindowsExecutableFileName();
}
