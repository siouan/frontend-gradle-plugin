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

    private final ParsePackageManagerFromPackageJsonFile parsePackageManagerFromPackageJsonFile;

    /**
     * Resolves and saves the package manager applicable to the project (name, version, and path to the executable).
     *
     * @param command Command providing parameters.
     * @throws IOException If an I/O error occurs.
     * @throws InvalidJsonFileException If the {@code package.json} file does not contain valid JSON.
     * @throws MalformedPackageManagerSpecification If value of the package manager key cannot be parsed.
     * @throws UnsupportedPackageManagerException If the package manager is not supported.
     */
    public void execute(final ResolvePackageManagerCommand command)
        throws InvalidJsonFileException, UnsupportedPackageManagerException, IOException,
        MalformedPackageManagerSpecification {
        if (command.getPackageJsonFilePath() == null) {
            fileManager.deleteIfExists(command.getPackageManagerSpecificationFilePath());
            fileManager.deleteIfExists(command.getPackageManagerExecutablePathFilePath());
        } else {
            final PackageManager packageManager = parsePackageManagerFromPackageJsonFile.execute(
                command.getPackageJsonFilePath());
            fileManager.writeString(command.getPackageManagerSpecificationFilePath(),
                packageManager.getType().getPackageManagerName() + '@' + packageManager.getVersion(),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            fileManager.writeString(command.getPackageManagerExecutablePathFilePath(), getExecutablePath
                .execute(GetExecutablePathCommand
                    .builder()
                    .executableType(packageManager.getType().getExecutableType())
                    .nodeInstallDirectoryPath(command.getNodeInstallDirectoryPath())
                    .platform(command.getPlatform())
                    .build())
                .toString(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
