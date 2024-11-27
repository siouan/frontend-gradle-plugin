package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gets the path to a Corepack executable given an install directory and a platform.
 *
 * @since 7.0.0
 */
public class ResolveCorepackExecutablePath extends AbstractResolveExecutablePath {

    /**
     * Relative executable path on Windows O/S.
     */
    public static final String WINDOWS_EXECUTABLE_FILE_NAME = "corepack.cmd";

    /**
     * Relative executable path on Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_FILE_PATH = Paths.get(WINDOWS_EXECUTABLE_FILE_NAME);

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final String NON_WINDOWS_EXECUTABLE_FILE_NAME = "corepack";

    /**
     * Relative executable path on non-Windows O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_FILE_PATH = Paths
        .get("bin")
        .resolve(NON_WINDOWS_EXECUTABLE_FILE_NAME);

    public ResolveCorepackExecutablePath(final Logger logger) {
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
