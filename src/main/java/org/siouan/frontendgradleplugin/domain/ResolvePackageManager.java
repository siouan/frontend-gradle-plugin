package org.siouan.frontendgradleplugin.domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

import lombok.RequiredArgsConstructor;

/**
 * Resolves the package manager applicable in a project.
 *
 * @since 7.0.0
 */
@RequiredArgsConstructor
public class ResolvePackageManager {

    private final FileManager fileManager;

    private final GetExecutablePath getExecutablePath;

    private final ParsePackageManagerType parsePackageManagerType;

    public void execute(final ResolvePackageManagerCommand command)
        throws UnsupportedPackageManagerException, IOException {
        final PackageManagerType packageManagerType = parsePackageManagerType.execute(command.getMetadataFilePath());
        fileManager.writeString(command.getPackageManagerNameFilePath(), packageManagerType.getPackageManagerName(),
            StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        fileManager.writeString(command.getPackageManagerExecutablePathFilePath(), getExecutablePath
            .execute(new GetExecutablePathCommand(packageManagerType.getExecutableType(),
                command.getNodeInstallDirectoryPath(), command.getPlatform()))
            .toString(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
