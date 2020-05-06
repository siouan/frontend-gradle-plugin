package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Gets the path to a npm executable given an install directory and a platform.
 *
 * @since 2.0.0
 */
public class GetNpmExecutablePath extends AbstractGetExecutablePath {

    /**
     * Relative executable path on Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_FILE_NAME = Paths.get("npm.cmd");

    /**
     * Relative executable path on Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_FILE_PATH = WINDOWS_EXECUTABLE_FILE_NAME;

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_FILE_NAME = Paths.get("npm");

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = Paths
        .get("bin")
        .resolve(NON_WINDOWS_EXECUTABLE_FILE_NAME);

    public GetNpmExecutablePath(final FileManager fileManager, final Logger logger) {
        super(fileManager, logger);
    }

    @Override
    @Nonnull
    protected Path getWindowsRelativeExecutablePath() {
        return WINDOWS_EXECUTABLE_FILE_PATH;
    }

    @Override
    @Nonnull
    protected Path getNonWindowsRelativeExecutablePath() {
        return NON_WINDOWS_EXECUTABLE_FILE_PATH;
    }

    @Nonnull
    @Override
    protected Optional<Path> getInstallDirectoryFromEnvironment(@Nonnull final Platform platform) {
        return Optional.ofNullable(platform.getEnvironment().getNodeInstallDirectoryPath());
    }

    @Nonnull
    @Override
    protected Path getWindowsExecutableFileName() {
        return WINDOWS_EXECUTABLE_FILE_NAME;
    }

    @Nonnull
    @Override
    protected Path getNonWindowsExecutableFileName() {
        return NON_WINDOWS_EXECUTABLE_FILE_NAME;
    }
}
