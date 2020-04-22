package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Nonnull;

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
    public static final Path WINDOWS_EXECUTABLE_PATH = Paths.get("npm.cmd");

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_PATH = Paths.get("bin", "npm");

    public GetNpmExecutablePath(final FileManager fileManager) {
        super(fileManager);
    }

    @Override
    @Nonnull
    protected Path getWindowsRelativeExecutablePath() {
        return WINDOWS_EXECUTABLE_PATH;
    }

    @Override
    @Nonnull
    protected Path getNonWindowsRelativeExecutablePath() {
        return NON_WINDOWS_EXECUTABLE_PATH;
    }
}
