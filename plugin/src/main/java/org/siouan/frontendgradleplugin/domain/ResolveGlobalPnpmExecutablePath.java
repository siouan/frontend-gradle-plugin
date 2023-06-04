package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gets the path to a PNPM executable given an install directory and a platform.
 *
 * @since 7.0.0
 */
public class ResolveGlobalPnpmExecutablePath extends AbstractResolveGlobalExecutablePath {

    /**
     * Relative executable path on Windows O/S.
     */
    public static final String WINDOWS_EXECUTABLE_FILE_NAME = "pnpm.cmd";

    /**
     * Relative executable path on Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_FILE_PATH = Paths.get(WINDOWS_EXECUTABLE_FILE_NAME);

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final String NON_WINDOWS_EXECUTABLE_FILE_NAME = "pnpm";

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = Paths
        .get("bin")
        .resolve(NON_WINDOWS_EXECUTABLE_FILE_NAME);

    public ResolveGlobalPnpmExecutablePath(final Logger logger) {
        super(logger);
    }

    @Override
    protected Path getWindowsRelativeExecutablePath() {
        return WINDOWS_EXECUTABLE_FILE_PATH;
    }

    @Override
    protected Path getNonWindowsRelativeExecutablePath() {
        return NON_WINDOWS_EXECUTABLE_FILE_PATH;
    }

    @Override
    protected String getWindowsExecutableFileName() {
        return WINDOWS_EXECUTABLE_FILE_NAME;
    }

    @Override
    protected String getNonWindowsExecutableFileName() {
        return NON_WINDOWS_EXECUTABLE_FILE_NAME;
    }
}
