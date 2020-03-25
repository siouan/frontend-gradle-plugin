package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Gets the path to a Yarn executable given an install directory and a platform.
 *
 * @since 1.4.2
 */
public class GetYarnExecutablePath {

    private static final String BIN_DIRECTORY_NAME = "bin";

    private static final String WINDOWS_EXECUTABLE = "yarn.cmd";

    private static final String LINUX_EXECUTABLE = "yarn";

    /**
     * Gets the path of Yarn executable.
     *
     * @param yarnInstallDirectory Yarn install directory.
     * @param platform Execution platform.
     * @return The path, may be {@code null} if it was not found.
     */
    public Optional<Path> execute(@Nonnull final Path yarnInstallDirectory, @Nonnull final Platform platform) {
        final List<Path> possiblePaths = new ArrayList<>();
        final Path binDirectory = yarnInstallDirectory.resolve(BIN_DIRECTORY_NAME);
        if (platform.isWindowsOs()) {
            possiblePaths.add(binDirectory.resolve(WINDOWS_EXECUTABLE));
        } else {
            possiblePaths.add(binDirectory.resolve(LINUX_EXECUTABLE));
        }

        return possiblePaths.stream().filter(Files::exists).findAny();
    }
}
