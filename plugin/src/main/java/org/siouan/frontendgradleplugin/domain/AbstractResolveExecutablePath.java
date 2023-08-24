package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Resolves the path of an executable installed globally within a Node.js install directory.
 *
 * @since 2.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractResolveExecutablePath {

    private final Logger logger;

    /**
     * Gets the executable path.
     *
     * @param command Command providing resolution parameters.
     * @return The path, which may be a single file name if it must be resolved using the PATH environment variable.
     */
    public Path execute(final ResolveExecutablePathCommand command) {
        final Path relativeExecutablePath;
        if (command.getPlatform().isWindowsOs()) {
            relativeExecutablePath = getWindowsRelativeExecutablePath();
        } else {
            relativeExecutablePath = getNonWindowsRelativeExecutablePath();
        }

        final Path executablePath = command.getNodeInstallDirectoryPath().resolve(relativeExecutablePath);
        logger.debug("Resolved executable path: '{}'", executablePath);
        return executablePath;
    }

    /**
     * Gets the executable path on Windows O/S, relative to the installation directory.
     *
     * @return Relative path.
     */
    protected abstract Path getWindowsRelativeExecutablePath();

    /**
     * Gets the executable path on non-Windows O/S, relative to the installation directory.
     *
     * @return Relative path.
     */
    protected abstract Path getNonWindowsRelativeExecutablePath();

    /**
     * Gets the executable file name on Windows O/S.
     *
     * @return File name.
     */
    protected abstract String getWindowsExecutableFileName();

    /**
     * Gets the executable file name on non-Windows O/S.
     *
     * @return File name.
     */
    protected abstract String getNonWindowsExecutableFileName();
}
