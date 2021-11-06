package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorSettings;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.GetDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * Downloads and optionally validates a distribution file.
 *
 * @since 2.0.0
 */
public class GetDistribution {

    /**
     * Pattern to resolve the last part in the path fragment of a URL.
     */
    private static final Pattern URL_FILENAME_PATTERN = Pattern.compile("^.*/([^/]+)/*$");

    private final ResolveNodeDistributionUrl resolveNodeDistributionUrl;

    private final BuildTemporaryFileName buildTemporaryFileName;

    private final DownloadResource downloadResource;

    private final ValidateNodeDistribution validateNodeDistribution;

    private final Logger logger;

    public GetDistribution(final ResolveNodeDistributionUrl resolveNodeDistributionUrl,
        final BuildTemporaryFileName buildTemporaryFileName, final DownloadResource downloadResource,
        final ValidateNodeDistribution validateNodeDistribution, final Logger logger) {
        this.resolveNodeDistributionUrl = resolveNodeDistributionUrl;
        this.buildTemporaryFileName = buildTemporaryFileName;
        this.downloadResource = downloadResource;
        this.validateNodeDistribution = validateNodeDistribution;
        this.logger = logger;
    }

    /**
     * Gets a distribution:
     * <ul>
     * <li>Resolve the URL to download the distribution.</li>
     * <li>Download the distribution.</li>
     * <li>Validate the downloaded distribution.</li>
     * </ul>
     *
     * @param getDistributionSettings Settings to get the distribution file.
     * @return Path to the distribution file.
     * @throws UnsupportedPlatformException If the target platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is invalid.
     * @throws DistributionValidatorException If the downloaded distribution file is invalid.
     * @throws IOException If an I/O error occurs.
     * @throws ResourceDownloadException If the distribution download failed.
     */
    @Nonnull
    public Path execute(@Nonnull final GetDistributionSettings getDistributionSettings)
        throws FrontendException, IOException {
        // Resolve the URL to download the distribution
        final DistributionDefinition distributionDefinition = new DistributionDefinition(
            getDistributionSettings.getPlatform(), getDistributionSettings.getVersion(),
            getDistributionSettings.getDistributionUrlRoot(), getDistributionSettings.getDistributionUrlPathPattern());
        final URL distributionUrl = resolveNodeDistributionUrl.execute(distributionDefinition);

        // Download the distribution
        logger.info("Downloading distribution at '{}'", distributionUrl);
        final Path distributionFilePath = getDistributionSettings
            .getTemporaryDirectoryPath()
            .resolve(resolveDistributionFileName(distributionUrl));
        final Path temporaryFilePath = getDistributionSettings
            .getTemporaryDirectoryPath()
            .resolve(buildTemporaryFileName.execute(distributionFilePath.getFileName().toString()));
        downloadResource.execute(
            new DownloadSettings(distributionUrl, getDistributionSettings.getDistributionServerCredentials(),
                getDistributionSettings.getProxySettings(), temporaryFilePath, distributionFilePath));

        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            getDistributionSettings.getTemporaryDirectoryPath(), distributionUrl,
            getDistributionSettings.getDistributionServerCredentials(), getDistributionSettings.getProxySettings(),
            distributionFilePath);
        validateNodeDistribution.execute(distributionValidatorSettings);

        return distributionFilePath;
    }

    @Nonnull
    private String resolveDistributionFileName(@Nonnull final URL distributionUrl)
        throws InvalidDistributionUrlException {
        final String distributionUrlPath = distributionUrl.getPath();
        final Matcher fileNameMatcher = URL_FILENAME_PATTERN.matcher(distributionUrlPath);
        if (!fileNameMatcher.matches()) {
            throw new InvalidDistributionUrlException(distributionUrl);
        }
        return fileNameMatcher.group(1);
    }
}
