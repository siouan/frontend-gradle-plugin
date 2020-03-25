package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Gets the path to a NPM executable given an install directory and a platform.
 *
 * @since 1.4.2
 */
public class GetNpmExecutablePath {

    private static final String WINDOWS_EXECUTABLE = "npm.cmd";

    private static final Path LINUX_EXECUTABLE_PATH = Paths.get("bin", "npm");

    /**
     * Gets the path of the NPM executable.
     *
     * @param nodeInstallDirectory Node install directory.
     * @param platform Execution platform
     * @return The path, may be {@code null} if it was not found.
     */
    public Optional<Path> execute(@Nonnull final Path nodeInstallDirectory, @Nonnull final Platform platform) {
        final List<Path> possiblePaths = new ArrayList<>();
        if (platform.isWindowsOs()) {
            possiblePaths.add(nodeInstallDirectory.resolve(WINDOWS_EXECUTABLE));
        } else {
            possiblePaths.add(nodeInstallDirectory.resolve(LINUX_EXECUTABLE_PATH));
        }

        return possiblePaths.stream().filter(Files::exists).findAny();
    }
}
