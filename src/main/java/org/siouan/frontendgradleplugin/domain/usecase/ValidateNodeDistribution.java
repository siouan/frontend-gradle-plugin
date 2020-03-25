package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.NodeDistributionChecksumNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorProperties;
import org.siouan.frontendgradleplugin.domain.model.DownloadParameters;
import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * Validates a Node distribution by comparing its SHA-256 hash against the one officially published.
 */
public class ValidateNodeDistribution implements DistributionValidator {

    /**
     * Downloader used to get the file containing all checksums for a given distribution.
     */
    private final DownloadResource downloadResource;

    /**
     * Reader that gives the checksum of the distribution from a file.
     */
    private final ReadNodeDistributionChecksum readNodeDistributionChecksum;

    /**
     * Hasher used to check the distribution integrity.
     */
    private final HashFile hashFile;

    private final Logger logger;

    /**
     * Builds a validator of a Node distribution.
     *
     * @param downloadResource Used to get the file containing all checksums for a given distribution.
     * @param readNodeDistributionChecksum Reader of checksum's file.
     * @param hashFile Hasher used to check the distribution integrity.
     * @param logger Logger.
     */
    public ValidateNodeDistribution(final DownloadResource downloadResource,
        final ReadNodeDistributionChecksum readNodeDistributionChecksum, final HashFile hashFile, final Logger logger) {
        this.downloadResource = downloadResource;
        this.readNodeDistributionChecksum = readNodeDistributionChecksum;
        this.hashFile = hashFile;
        this.logger = logger;
    }

    /**
     * Validates the distribution in the given file.
     *
     * @throws DistributionValidatorException If the distribution is invalid.
     */
    @Override
    public void validate(@Nonnull final DistributionValidatorProperties distributionValidatorProperties)
        throws DistributionValidatorException, URISyntaxException {
        final String distributionUrlAsString = distributionValidatorProperties.getDistributionUrl().toString();

        // Resolve the URL to download the checksum file
        final String checksumUrlAsString =
            distributionUrlAsString.substring(0, distributionUrlAsString.lastIndexOf('/') + 1) + "SHASUMS256.txt";
        final Path checksumFile = distributionValidatorProperties
            .getInstallDirectory()
            .resolve(checksumUrlAsString.substring(checksumUrlAsString.lastIndexOf('/') + 1));
        try {
            final URL checksumUrl = URI.create(checksumUrlAsString).toURL();

            // Download the checksum file
            logger.log("Downloading checksums at '" + checksumUrlAsString + "'");
            downloadResource.execute(
                new DownloadParameters(checksumUrl, distributionValidatorProperties.getTemporaryDirectory(),
                    checksumFile));

            // Verify the distribution integrity
            logger.log("Verifying distribution integrity");
            final String expectedHash = readNodeDistributionChecksum.execute(checksumFile,
                distributionValidatorProperties.getDistributionFile().getFileName().toString());
            if (!hashFile.execute(distributionValidatorProperties.getDistributionFile()).equals(expectedHash)) {
                throw new DistributionValidatorException("Distribution corrupted: invalid checksum");
            }
        } catch (final IOException | FrontendIOException | NodeDistributionChecksumNotFoundException e) {
            throw new DistributionValidatorException(e);
        } finally {
            try {
                Files.deleteIfExists(checksumFile);
            } catch (final IOException e) {
                logger.warn("Checksum file could not be deleted: '" + checksumFile + '\'', e);
            }
        }
    }
}
