package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.model.DeploymentSettings;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Deploys a distribution archive in a target directory.
 *
 * @since 2.0.0
 */
public class DeployDistribution {

    private final FileManager fileManager;

    private final ArchiverProvider archiverProvider;

    private final Logger logger;

    public DeployDistribution(final FileManager fileManager, final ArchiverProvider archiverProvider,
        final Logger logger) {
        this.fileManager = fileManager;
        this.archiverProvider = archiverProvider;
        this.logger = logger;
    }

    /**
     * Deploys a distribution by exploding the archive and cleaning all unnecessary files.
     *
     * @param deploymentSettings Settings to deploy the archive content in a target directory.
     */
    public void execute(@Nonnull final DeploymentSettings deploymentSettings)
        throws UnsupportedDistributionArchiveException, ArchiverException, IOException {
        final Path distributionFilePath = deploymentSettings.getDistributionFilePath();

        // Explodes the archive
        final Path extractDirectoryPath = fileManager.createDirectory(deploymentSettings.getExtractDirectoryPath());

        logger.log("Exploding distribution into '{}'", extractDirectoryPath);
        final ExplodeSettings explodeSettings = new ExplodeSettings(deploymentSettings.getPlatform(),
            distributionFilePath, extractDirectoryPath);
        archiverProvider
            .findByArchiveFilePath(distributionFilePath)
            .orElseThrow(() -> new UnsupportedDistributionArchiveException(distributionFilePath))
            .explode(explodeSettings);

        logger.log("Moving distribution into '{}'", deploymentSettings.getInstallDirectoryPath());
        // Removes the root directory of exploded content, if any.
        final Set<Path> distributionFilePaths;
        try (final Stream<Path> childFilePaths = fileManager.list(extractDirectoryPath)) {
            distributionFilePaths = childFilePaths
                .filter(childFilePath -> !childFilePath.getFileName().equals(distributionFilePath.getFileName()))
                .collect(toSet());
        }
        final Path distributionRootDirectoryPath;
        if (distributionFilePaths.size() == 1) {
            distributionRootDirectoryPath = distributionFilePaths.iterator().next();
        } else {
            distributionRootDirectoryPath = extractDirectoryPath;
        }
        fileManager.moveFileTree(distributionRootDirectoryPath, deploymentSettings.getInstallDirectoryPath());

        logger.log("Removing explode directory '{}'", extractDirectoryPath);
        fileManager.deleteIfExists(extractDirectoryPath);
    }
}
