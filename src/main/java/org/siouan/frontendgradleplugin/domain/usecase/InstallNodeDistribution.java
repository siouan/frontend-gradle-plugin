package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DeploymentSettings;
import org.siouan.frontendgradleplugin.domain.model.GetDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Base class that installs a distribution.
 *
 * @since 2.0.0
 */
public class InstallNodeDistribution {

    public static final String EXTRACT_DIRECTORY_NAME = "extract";

    private final FileManager fileManager;

    private final GetDistribution getDistribution;

    private final DeployDistribution deployDistribution;

    private final Logger logger;

    public InstallNodeDistribution(final FileManager fileManager, final GetDistribution getDistribution,
        final DeployDistribution deployDistribution, final Logger logger) {
        this.fileManager = fileManager;
        this.getDistribution = getDistribution;
        this.deployDistribution = deployDistribution;
        this.logger = logger;
    }

    /**
     * Installs a distribution:
     * <ul>
     * <li>Empty the install directory.</li>
     * <li>Download and validate the distribution.</li>
     * <li>Deploy the distribution in the install directory.</li>
     * <li>Delete the distribution archive.</li>
     * </ul>
     *
     * @param installSettings Settings to install the distribution.
     * @throws UnsupportedDistributionArchiveException If the distribution file type is not supported.
     * @throws UnsupportedPlatformException If the underlying platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is not valid.
     * @throws DistributionValidatorException If the downloaded distribution file is not valid.
     * @throws ArchiverException If an error occurs in the archiver exploding the distribution.
     * @throws IOException If an I/O error occurs.
     * @throws ResourceDownloadException If the distribution download failed.
     */
    public void execute(@Nonnull final InstallSettings installSettings) throws FrontendException, IOException {
        logger.info("Removing install directory '{}'", installSettings.getInstallDirectoryPath());
        fileManager.deleteFileTree(installSettings.getInstallDirectoryPath(), true);

        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            installSettings.getPlatform(), installSettings.getVersion(), installSettings.getDistributionUrlRoot(),
            installSettings.getDistributionUrlPathPattern(), installSettings.getDistributionServerCredentials(),
            installSettings.getProxySettings(), installSettings.getTemporaryDirectoryPath());
        final Path distributionFilePath = getDistribution.execute(getDistributionSettings);

        // Deploys the distribution
        deployDistribution.execute(new DeploymentSettings(installSettings.getPlatform(),
            installSettings.getTemporaryDirectoryPath().resolve(EXTRACT_DIRECTORY_NAME),
            installSettings.getInstallDirectoryPath(), distributionFilePath));

        logger.info("Removing distribution file '{}'", distributionFilePath);
        fileManager.delete(distributionFilePath);

        logger.info("Distribution installed in '{}'", installSettings.getInstallDirectoryPath());
    }
}
