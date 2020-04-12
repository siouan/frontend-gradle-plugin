package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Gets the path to a Yarn executable given an install directory and a platform.
 *
 * @since 2.0.0
 */
public class GetYarnExecutablePath {

    /**
     * Supported executable on a Windows O/S.
     */
    public static final Path WINDOWS_EXECUTABLE_PATH = Paths.get("bin", "yarn.cmd");

    /**
     * Supported executable on other O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_PATH = Paths.get("bin", "yarn");

    private final FileManager fileManager;

    public GetYarnExecutablePath(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Gets the path of Yarn executable.
     *
     * @param yarnInstallDirectory Yarn install directory.
     * @param platform Execution platform.
     * @return The path.
     */
    @Nonnull
    public Optional<Path> execute(@Nonnull final Path yarnInstallDirectory, @Nonnull final Platform platform) {
        final Path possiblePath;
        if (platform.isWindowsOs()) {
            possiblePath = WINDOWS_EXECUTABLE_PATH;
        } else {
            possiblePath = NON_WINDOWS_EXECUTABLE_PATH;
        }

        return Optional.of(possiblePath).map(yarnInstallDirectory::resolve).filter(fileManager::exists);
    }
}
