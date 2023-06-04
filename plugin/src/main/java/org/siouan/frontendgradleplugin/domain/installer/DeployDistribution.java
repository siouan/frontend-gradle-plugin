package org.siouan.frontendgradleplugin.domain.installer;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverException;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

/**
 * Deploys a distribution archive in a target directory.
 *
 * @since 2.0.0
 */
@RequiredArgsConstructor
public class DeployDistribution {

    private final FileManager fileManager;

    private final ArchiverProvider archiverProvider;

    private final Logger logger;

    /**
     * Deploys a distribution by exploding the archive and cleaning all unnecessary files.
     *
     * @param command Command providing parameters to deploy the archive content in a target directory.
     * @throws UnsupportedDistributionArchiveException If the distribution file type is not supported.
     * @throws ArchiverException If an error occurs in the archiver exploding the distribution.
     * @throws IOException If an I/O error occurs.
     */
    public void execute(final DeployDistributionCommand command)
        throws UnsupportedDistributionArchiveException, ArchiverException, IOException {
        // Explodes the archive
        final Path temporaryDirectoryPath = fileManager.createDirectory(command.getTemporaryDirectoryPath());
        logger.info("Exploding distribution into '{}'", temporaryDirectoryPath);
        final Path distributionFilePath = command.getDistributionFilePath();
        archiverProvider
            .findByArchiveFilePath(distributionFilePath)
            .orElseThrow(() -> new UnsupportedDistributionArchiveException(distributionFilePath))
            .explode(ExplodeCommand
                .builder()
                .platform(command.getPlatform())
                .archiveFilePath(distributionFilePath)
                .targetDirectoryPath(temporaryDirectoryPath)
                .build());

        final Path installDirectoryPath = command.getInstallDirectoryPath();
        logger.info("Moving distribution into '{}'", installDirectoryPath);
        // Removes the root directory of exploded content, if any.
        final Set<Path> distributionFilePaths;
        try (final Stream<Path> childFilePaths = fileManager.list(temporaryDirectoryPath)) {
            distributionFilePaths = childFilePaths.collect(toSet());
        }
        final Path distributionRootDirectoryPath;
        if (distributionFilePaths.size() == 1) {
            distributionRootDirectoryPath = distributionFilePaths.iterator().next();
        } else {
            distributionRootDirectoryPath = temporaryDirectoryPath;
        }
        fileManager.moveFileTree(distributionRootDirectoryPath, installDirectoryPath);

        logger.info("Removing explode directory '{}'", temporaryDirectoryPath);
        fileManager.deleteIfExists(temporaryDirectoryPath);
    }
}
