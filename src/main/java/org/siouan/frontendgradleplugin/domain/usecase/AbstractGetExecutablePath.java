package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Gets the path to an executable given an install directory and a platform.
 *
 * @since 2.0.0
 */
public abstract class AbstractGetExecutablePath {

    private final FileManager fileManager;

    public AbstractGetExecutablePath(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Gets the executable path.
     *
     * @param installDirectory Install directory.
     * @param platform Execution platform.
     * @return The path.
     */
    @Nonnull
    public Optional<Path> execute(@Nonnull final Path installDirectory, @Nonnull final Platform platform) {
        final Path relativeExecutablePath;
        if (platform.isWindowsOs()) {
            relativeExecutablePath = getWindowsRelativeExecutablePath();
        } else {
            relativeExecutablePath = getNonWindowsRelativeExecutablePath();
        }

        return Optional.of(relativeExecutablePath).map(installDirectory::resolve).filter(fileManager::exists);
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
}
