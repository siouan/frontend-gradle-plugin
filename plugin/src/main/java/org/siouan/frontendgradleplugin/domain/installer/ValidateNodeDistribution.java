package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;

/**
 * Validates a Node distribution by comparing its SHA-256 hash against the one officially published.
 */
@RequiredArgsConstructor
public class ValidateNodeDistribution {

    public static final String SHASUMS_FILE_NAME = "SHASUMS256.txt";

    private final FileManager fileManager;

    private final BuildTemporaryFileName buildTemporaryFileName;

    private final DownloadResource downloadResource;

    private final ReadNodeDistributionShasum readNodeDistributionShasum;

    private final HashFile hashFile;

    private final Logger logger;

    /**
     * Validates a downloaded distribution: implementation downloads the Node official file providing shasums - one for
     * each supported platform, resolves the expected shasum matching the distribution file name, and verifies the
     * actual shasum of the distribution file matches this expected shasum.
     *
     * @param command Command providing parameters to validate the distribution.
     * @throws URISyntaxException If the distribution URL is not a valid URL.
     * @throws ResourceDownloadException If downloading the file providing shasums fails.
     * @throws InvalidNodeDistributionException If the distribution is invalid.
     * @throws NodeDistributionShasumNotFoundException If validation cannot be done for other reason.
     * @throws IOException If an I/O error occurs.
     */
    public void execute(final ValidateNodeDistributionCommand command)
        throws URISyntaxException, InvalidNodeDistributionException, IOException,
        NodeDistributionShasumNotFoundException, ResourceDownloadException {
        final Path shasumsFilePath = command.getTemporaryDirectoryPath().resolve(SHASUMS_FILE_NAME);
        // Resolve the URL to download the shasum file
        final String expectedShasum;
        try {
            final URL shasumsFileUrl = command.getDistributionUrl().toURI().resolve(SHASUMS_FILE_NAME).toURL();

            // Download the shasum file
            logger.debug("Downloading shasums at '{}'", shasumsFileUrl);
            final Path temporaryFilePath = command
                .getTemporaryDirectoryPath()
                .resolve(buildTemporaryFileName.execute(shasumsFilePath.getFileName().toString()));
            downloadResource.execute(DownloadResourceCommand
                .builder()
                .resourceUrl(shasumsFileUrl)
                .serverCredentials(command.getDistributionServerCredentials())
                .proxySettings(command.getProxySettings())
                .retrySettings(command.getRetrySettings())
                .temporaryFilePath(temporaryFilePath)
                .destinationFilePath(shasumsFilePath)
                .build());

            // Verify the distribution integrity
            logger.info("Verifying distribution integrity");
            final String distributionFileName = command.getDistributionFilePath().getFileName().toString();
            expectedShasum = readNodeDistributionShasum
                .execute(ReadNodeDistributionShasumCommand
                    .builder()
                    .distributionFileName(distributionFileName)
                    .nodeDistributionShasumFilePath(shasumsFilePath)
                    .build())
                .orElseThrow(() -> new NodeDistributionShasumNotFoundException(distributionFileName));
        } finally {
            fileManager.deleteIfExists(shasumsFilePath);
        }

        if (!hashFile.execute(command.getDistributionFilePath()).equals(expectedShasum)) {
            throw new InvalidNodeDistributionException();
        }
    }
}
