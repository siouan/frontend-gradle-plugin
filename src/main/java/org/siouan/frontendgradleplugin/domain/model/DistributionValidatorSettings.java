package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings for distribution validation.
 *
 * @since 2.0.0
 */
public class DistributionValidatorSettings {

    private final Path temporaryDirectoryPath;

    private final URL distributionUrl;

    private final Path distributionFilePath;

    private final Proxy proxy;

    /**
     * Builds validator settings.
     *
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param distributionUrl URL used to download the distribution.
     * @param distributionFilePath Path to the distribution archive.
     * @param proxy Proxy used for any connections.
     */
    public DistributionValidatorSettings(@Nonnull final Path temporaryDirectoryPath, @Nonnull final URL distributionUrl,
        @Nonnull final Path distributionFilePath, @Nonnull final Proxy proxy) {
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.distributionUrl = distributionUrl;
        this.distributionFilePath = distributionFilePath;
        this.proxy = proxy;
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

    /**
     * Gets the proxy used for the connection.
     *
     * @return Proxy.
     */
    @Nonnull
    public Proxy getProxy() {
        return proxy;
    }
}
