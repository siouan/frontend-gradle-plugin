package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Gets the path to an executable given an install directory and a platform.
 *
 * @since 2.0.0
 */
public abstract class AbstractGetExecutablePath {

    private final FileManager fileManager;

    private final Logger logger;

    public AbstractGetExecutablePath(final FileManager fileManager, final Logger logger) {
        this.fileManager = fileManager;
        this.logger = logger;
    }

    /**
     * Gets the executable path.
     *
     * @param installDirectory Install directory.
     * @param platform Underlying platform.
     * @return The path. If the path is a single file name, it means it must be resolved using the PATH environment
     * variable.
     * @throws ExecutableNotFoundException If the executable is not found in the given install directory, or in the
     * install directory provided by the environment.
     */
    @Nonnull
    public Path execute(@Nullable final Path installDirectory, @Nonnull final Platform platform)
        throws ExecutableNotFoundException {
        final Optional<Path> resolvedInstallDirectory;
        if (installDirectory == null) {
            resolvedInstallDirectory = getInstallDirectoryFromEnvironment(platform);
            logger.info("Install directory resolved from environment variable: {}", resolvedInstallDirectory);
        } else {
            logger.info("Install directory resolved from extension: {}", installDirectory);
            resolvedInstallDirectory = Optional.of(installDirectory);
        }

        final Path relativeExecutablePath;
        if (platform.isWindowsOs()) {
            relativeExecutablePath = getWindowsRelativeExecutablePath();
        } else {
            relativeExecutablePath = getNonWindowsRelativeExecutablePath();
        }

        final Path executablePath;
        if (resolvedInstallDirectory.isPresent()) {
            executablePath = resolvedInstallDirectory.get().resolve(relativeExecutablePath);
            if (fileManager.exists(executablePath)) {
                logger.info("Executable '{}' resolved from install directory", executablePath);
                return executablePath;
            }

            throw new ExecutableNotFoundException(executablePath);
        }

        executablePath = getExecutableFileName(platform);
        logger.info("Executable '{}' resolved from PATH environment variable", executablePath);
        return executablePath;
    }

    /**
     * Gets the executable file name.
     *
     * @return File name.
     */
    @Nonnull
    private Path getExecutableFileName(@Nonnull final Platform platform) {
        return platform.isWindowsOs() ? getWindowsExecutableFileName() : getNonWindowsExecutableFileName();
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

    /**
     * Gets the install directory from the environment.
     *
     * @param platform Underlying platform.
     * @return Path provided by the environment.
     */
    @Nonnull
    protected abstract Optional<Path> getInstallDirectoryFromEnvironment(@Nonnull final Platform platform);
}
