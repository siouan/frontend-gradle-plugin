package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Gets the path to a Node executable given an install directory and a platform.
 *
 * @since 1.4.2
 */
public class GetNodeExecutablePath {

    private static final String[] WINDOWS_EXECUTABLES = new String[] {"node.exe", "node.cmd"};

    private static final Path LINUX_EXECUTABLE_PATH = Paths.get("bin", "node");

    /**
     * Gets the path of the Node executable.
     *
     * @param nodeInstallDirectory Node install directory.
     * @param platform Execution platform.
     * @return The path, may be {@code null} if it was not found.
     */
    public Optional<Path> execute(@Nonnull final Path nodeInstallDirectory, @Nonnull final Platform platform) {
        final List<Path> possiblePaths = new ArrayList<>();
        if (platform.isWindowsOs()) {
            Arrays.stream(WINDOWS_EXECUTABLES).map(nodeInstallDirectory::resolve).forEach(possiblePaths::add);
        } else {
            possiblePaths.add(nodeInstallDirectory.resolve(LINUX_EXECUTABLE_PATH));
        }

        return possiblePaths.stream().filter(Files::exists).findAny();
    }
}
