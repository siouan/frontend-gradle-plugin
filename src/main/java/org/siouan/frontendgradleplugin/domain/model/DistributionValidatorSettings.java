package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings for distribution validation.
 *
 * @since 2.0.0
 */
public class DistributionValidatorSettings {

    /**
     * Path to a temporary directory.
     */
    private final Path temporaryDirectoryPath;

    /**
     * URL used to download the distribution.
     */
    private final URL distributionUrl;

    /**
     * Path to the distribution archive.
     */
    private final Path distributionFilePath;

    /**
     * Builds validator settings.
     *
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param distributionUrl URL used to download the distribution.
     * @param distributionFilePath Path to the distribution archive.
     */
    public DistributionValidatorSettings(@Nonnull final Path temporaryDirectoryPath, @Nonnull final URL distributionUrl,
        @Nonnull final Path distributionFilePath) {
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.distributionUrl = distributionUrl;
        this.distributionFilePath = distributionFilePath;
    }

    /**
     * Gets the path to a temporary directory.
     *
     * @return Path.
     */
    @Nonnull
    public Path getTemporaryDirectoryPath() {
        return temporaryDirectoryPath;
    }

    /**
     * Gets the URL used to download the distribution.
     *
     * @return URL.
     */
    @Nonnull
    public URL getDistributionUrl() {
        return distributionUrl;
    }

    /**
     * Gets the path to the distribution archive.
     *
     * @return Path;
     */
    @Nonnull
    public Path getDistributionFilePath() {
        return distributionFilePath;
    }
}
