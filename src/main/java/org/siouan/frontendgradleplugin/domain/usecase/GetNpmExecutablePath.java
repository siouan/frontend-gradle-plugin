package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.Collections.singletonList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Gets the path to a npm executable given an install directory and a platform.
 *
 * @since 2.0.0
 */
public class GetNpmExecutablePath extends AbstractGetExecutablePath {

    /**
     * Supported executable on a Windows O/S.
     */
    public static final List<Path> WINDOWS_EXECUTABLE_PATHS = singletonList(Paths.get("npm.cmd"));

    /**
     * Supported executable on other O/S.
     */
    public static final List<Path> NON_WINDOWS_EXECUTABLE_PATHS = singletonList(Paths.get("bin", "npm"));

    public GetNpmExecutablePath(final FileManager fileManager) {
        super(fileManager);
    }

    @Override
    @Nonnull
    protected List<Path> getWindowsRelativeExecutablePaths() {
        return WINDOWS_EXECUTABLE_PATHS;
    }

    @Override
    @Nonnull
    protected List<Path> getNonWindowsRelativeExecutablePaths() {
        return NON_WINDOWS_EXECUTABLE_PATHS;
    }
}
