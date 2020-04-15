package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
        final List<Path> relativeExecutablePaths = new ArrayList<>();
        if (platform.isWindowsOs()) {
            relativeExecutablePaths.addAll(getWindowsRelativeExecutablePaths());
        } else {
            relativeExecutablePaths.addAll(getNonWindowsRelativeExecutablePaths());
        }

        return relativeExecutablePaths.stream().map(installDirectory::resolve).filter(fileManager::exists).findAny();
    }

    /**
     * Gets the list of relative paths on Windows O/S, from the installation directory, to get an executable. The list
     * allows to return multiple supported executables, ordered by priority, to try other alternatives in case one of
     * the executables is not found.
     *
     * @return List of relative paths to an executable.
     */
    @Nonnull
    protected abstract List<Path> getWindowsRelativeExecutablePaths();

    /**
     * Gets the list of relative paths on non-Windows O/S, from the installation directory, to get an executable. The
     * list allows to return multiple supported executables, ordered by priority, to try other alternatives in case one
     * of the executables is not found.
     *
     * @return List of relative paths to an executable.
     */
    @Nonnull
    protected abstract List<Path> getNonWindowsRelativeExecutablePaths();
}
