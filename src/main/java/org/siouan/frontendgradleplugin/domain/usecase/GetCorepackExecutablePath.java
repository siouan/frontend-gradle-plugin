package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * Gets the path to a Corepack executable given an install directory and a platform.
 *
 * @since 7.0.0
 */
public class GetCorepackExecutablePath extends AbstractGetExecutablePath {

    /**
     * Relative executable path on Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_FILE_NAME = Paths.get("corepack.cmd");

    /**
     * Relative executable path on Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_FILE_PATH = WINDOWS_EXECUTABLE_FILE_NAME;

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_FILE_NAME = Paths.get("corepack");

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = Paths
        .get("bin")
        .resolve(NON_WINDOWS_EXECUTABLE_FILE_NAME);

    public GetCorepackExecutablePath(final Logger logger) {
        super(logger);
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
    protected Path getWindowsExecutableFileName() {
        return WINDOWS_EXECUTABLE_FILE_NAME;
    }

    @Nonnull
    @Override
    protected Path getNonWindowsExecutableFileName() {
        return NON_WINDOWS_EXECUTABLE_FILE_NAME;
    }
}