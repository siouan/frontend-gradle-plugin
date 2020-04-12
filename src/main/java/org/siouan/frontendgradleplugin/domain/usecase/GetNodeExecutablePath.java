package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Gets the path to a Node executable given an install directory and a platform.
 *
 * @since 2.0.0
 */
public class GetNodeExecutablePath {

    /**
     * Supported executables on a Windows O/S.
     */
    public static final List<Path> WINDOWS_EXECUTABLE_PATHS = unmodifiableList(
        asList(Paths.get("node.exe"), Paths.get("node.cmd")));

    /**
     * Supported executable on other O/S.
     */
    public static final Path NON_WINDOWS_EXECUTABLE_PATH = Paths.get("bin", "node");

    private final FileManager fileManager;

    public GetNodeExecutablePath(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Gets the path of the Node executable.
     *
     * @param nodeInstallDirectory Node install directory.
     * @param platform Execution platform.
     * @return The path.
     */
    @Nonnull
    public Optional<Path> execute(@Nonnull final Path nodeInstallDirectory, @Nonnull final Platform platform) {
        final List<Path> possiblePaths = new ArrayList<>();
        if (platform.isWindowsOs()) {
            possiblePaths.addAll(WINDOWS_EXECUTABLE_PATHS);
        } else {
            possiblePaths.add(nodeInstallDirectory.resolve(NON_WINDOWS_EXECUTABLE_PATH));
        }

        return possiblePaths.stream().map(nodeInstallDirectory::resolve).filter(fileManager::exists).findAny();
    }
}
