package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionIdException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidator;
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

    private final GetDistributionUrlResolver getDistributionUrlResolver;

    private final DownloadResource downloadResource;

    private final GetDistributionValidator getDistributionValidator;

    private final Logger logger;

    public GetDistribution(final GetDistributionUrlResolver getDistributionUrlResolver,
        final DownloadResource downloadResource, final GetDistributionValidator getDistributionValidator,
        final Logger logger) {
        this.getDistributionUrlResolver = getDistributionUrlResolver;
        this.downloadResource = downloadResource;
        this.getDistributionValidator = getDistributionValidator;
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
     * @throws UnsupportedDistributionIdException If the type of distribution to install is not supported.
     * @throws UnsupportedPlatformException If the target platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is invalid.
     * @throws DistributionValidatorException If the downloaded distribution file is invalid.
     * @throws IOException If an I/O error occurs.
     */
    @Nonnull
    public Path execute(@Nonnull final GetDistributionSettings getDistributionSettings)
        throws UnsupportedDistributionIdException, UnsupportedPlatformException, InvalidDistributionUrlException,
        DistributionValidatorException, IOException {
        // Resolve the URL to download the distribution
        final DistributionUrlResolver distributionUrlResolver = getDistributionUrlResolver
            .execute(getDistributionSettings.getDistributionId())
            .orElseThrow(() -> new UnsupportedDistributionIdException(getDistributionSettings.getDistributionId()));
        final DistributionDefinition distributionDefinition = new DistributionDefinition(
            getDistributionSettings.getPlatform(), getDistributionSettings.getVersion(),
            getDistributionSettings.getDistributionUrl());
        final URL distributionUrl = distributionUrlResolver.execute(distributionDefinition);

        // Download the distribution
        logger.log("Downloading distribution at '{}'", distributionUrl);
        final Path distributionFilePath = getDistributionSettings
            .getTemporaryDirectoryPath()
            .resolve(resolveDistributionFileName(distributionUrl));
        downloadResource.execute(
            new DownloadSettings(distributionUrl, getDistributionSettings.getTemporaryDirectoryPath(),
                distributionFilePath));

        final Optional<DistributionValidator> distributionValidator = getDistributionValidator.execute(
            getDistributionSettings.getDistributionId());
        if (distributionValidator.isPresent()) {
            final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
                getDistributionSettings.getTemporaryDirectoryPath(), distributionUrl, distributionFilePath);
            distributionValidator.get().execute(distributionValidatorSettings);
        }
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
