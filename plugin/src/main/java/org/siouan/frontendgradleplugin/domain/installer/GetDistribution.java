package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;

/**
 * Downloads and optionally validates a distribution file.
 *
 * @since 2.0.0
 */
@RequiredArgsConstructor
public class GetDistribution {

    private static final String FILE_NAME_GROUP = "fileName";

    /**
     * Pattern to resolve the last part in the path fragment of a URL.
     */
    private static final Pattern URL_FILENAME_PATTERN = Pattern.compile("^.*/(?<" + FILE_NAME_GROUP + ">[^/]+)/*$");

    private final ResolveNodeDistributionUrl resolveNodeDistributionUrl;

    private final BuildTemporaryFileName buildTemporaryFileName;

    private final DownloadResource downloadResource;

    private final ValidateNodeDistribution validateNodeDistribution;

    private final Logger logger;

    /**
     * Gets a distribution:
     * <ul>
     * <li>Resolve the URL to download the distribution.</li>
     * <li>Download the distribution.</li>
     * <li>Validate the downloaded distribution.</li>
     * </ul>
     *
     * @param command Command providing parameters to get the distribution file.
     * @return Path to the distribution file.
     * @throws UnsupportedPlatformException If the target platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is invalid.
     * @throws NodeDistributionShasumNotFoundException If no SHASUM is officially published for the distribution.
     * @throws ResourceDownloadException If the distribution download or the shasums file download fails.
     * @throws InvalidNodeDistributionException If the downloaded distribution is corrupted.
     * @throws IOException If an I/O error occurs.
     */
    public Path execute(final GetDistributionCommand command)
        throws IOException, InvalidDistributionUrlException, ResourceDownloadException,
        InvalidNodeDistributionException, NodeDistributionShasumNotFoundException, UnsupportedPlatformException {
        // Resolve the URL to download the distribution
        final ResolveNodeDistributionUrlCommand resolveNodeDistributionUrlCommand = new ResolveNodeDistributionUrlCommand(
            command.getPlatform(), command.getVersion(), command.getDistributionUrlRoot(),
            command.getDistributionUrlPathPattern());
        final URL distributionUrl = resolveNodeDistributionUrl.execute(resolveNodeDistributionUrlCommand);

        // Download the distribution
        logger.info("Downloading distribution at '{}'", distributionUrl);
        final Path distributionFilePath = command
            .getTemporaryDirectoryPath()
            .resolve(resolveDistributionFileName(distributionUrl));
        final Path temporaryFilePath = command
            .getTemporaryDirectoryPath()
            .resolve(buildTemporaryFileName.execute(distributionFilePath.getFileName().toString()));
        downloadResource.execute(DownloadResourceCommand
            .builder()
            .resourceUrl(distributionUrl)
            .serverCredentials(command.getDistributionServerCredentials())
            .proxySettings(command.getProxySettings())
            .temporaryFilePath(temporaryFilePath)
            .destinationFilePath(distributionFilePath)
            .build());

        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            command.getTemporaryDirectoryPath(), distributionUrl, command.getDistributionServerCredentials(),
            command.getProxySettings(), distributionFilePath);
        validateNodeDistribution.execute(validateNodeDistributionCommand);

        return distributionFilePath;
    }

    private String resolveDistributionFileName(final URL distributionUrl) throws InvalidDistributionUrlException {
        final String distributionUrlPath = distributionUrl.getPath();
        final Matcher fileNameMatcher = URL_FILENAME_PATTERN.matcher(distributionUrlPath);
        if (!fileNameMatcher.matches()) {
            throw new InvalidDistributionUrlException(distributionUrl);
        }
        return fileNameMatcher.group(FILE_NAME_GROUP);
    }
}
