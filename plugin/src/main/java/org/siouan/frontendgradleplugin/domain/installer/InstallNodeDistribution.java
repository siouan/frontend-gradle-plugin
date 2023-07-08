package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.nio.file.Path;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverException;

/**
 * Base class that installs a distribution.
 *
 * @since 2.0.0
 */
@RequiredArgsConstructor
public class InstallNodeDistribution {

    public static final String EXTRACT_DIRECTORY_NAME = "extract";

    private final FileManager fileManager;

    private final GetDistribution getDistribution;

    private final DeployDistribution deployDistribution;

    private final Logger logger;

    /**
     * Installs a Node.js distribution:
     * <ul>
     * <li>Empty the install directory.</li>
     * <li>Download and validate the distribution.</li>
     * <li>Deploy the distribution in the install directory.</li>
     * <li>Delete the distribution archive.</li>
     * </ul>
     *
     * @param command Settings to install the distribution.
     * @throws UnsupportedPlatformException If the underlying platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is not valid.
     * @throws ResourceDownloadException If the distribution download failed.
     * @throws UnsupportedDistributionArchiveException If the distribution file type is not supported.
     * @throws NodeDistributionShasumNotFoundException If no SHASUM is officially published for the distribution.
     * @throws InvalidNodeDistributionException If the downloaded distribution is corrupted.
     * @throws ArchiverException If an error occurs in the archiver exploding the distribution.
     * @throws IOException If an I/O error occurs.
     */
    public void execute(final InstallNodeDistributionCommand command)
        throws IOException, InvalidNodeDistributionException, NodeDistributionShasumNotFoundException,
        UnsupportedPlatformException, InvalidDistributionUrlException, ResourceDownloadException, ArchiverException,
        UnsupportedDistributionArchiveException {
        logger.info("Removing install directory '{}'", command.installDirectoryPath());
        fileManager.deleteFileTree(command.installDirectoryPath(), true);

        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(command.platform(),
            command.version(), command.distributionUrlRoot(), command.distributionUrlPathPattern(),
            command.distributionServerCredentials(), command.proxySettings(), command.temporaryDirectoryPath());
        final Path distributionFilePath = getDistribution.execute(getDistributionCommand);

        // Deploys the distribution
        deployDistribution.execute(new DeployDistributionCommand(command.platform(),
            command.temporaryDirectoryPath().resolve(EXTRACT_DIRECTORY_NAME), command.installDirectoryPath(),
            distributionFilePath));

        logger.info("Removing distribution file '{}'", distributionFilePath);
        fileManager.delete(distributionFilePath);

        logger.info("Distribution installed in '{}'", command.installDirectoryPath());
    }
}
