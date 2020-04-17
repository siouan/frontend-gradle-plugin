package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings to get a distribution.
 *
 * @since 1.1.3
 */
public class GetDistributionSettings {

    private final DistributionId distributionId;

    private final String version;

    private final Platform platform;

    private final Path temporaryDirectoryPath;

    private final URL distributionUrl;

    private final Proxy proxy;

    /**
     * Builds settings to get a distribution.
     *
     * @param distributionId Distribution ID.
     * @param platform Underlying platform.
     * @param version Version.
     * @param distributionUrl URL to download the distribution.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param proxy Proxy used for the connection.
     */
    public GetDistributionSettings(@Nonnull DistributionId distributionId, @Nonnull final Platform platform,
        @Nonnull final String version, @Nullable final URL distributionUrl, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Proxy proxy) {
        this.distributionId = distributionId;
        this.platform = platform;
        this.version = version;
        this.distributionUrl = distributionUrl;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.proxy = proxy;
    }

    /**
     * Gets the distribution ID.
     *
     * @return ID.
     */
    @Nonnull
    public DistributionId getDistributionId() {
        return distributionId;
    }

    /**
     * Gets the version of the distribution.
     *
     * @return Version.
     */
    @Nonnull
    public String getVersion() {
        return version;
    }

    /**
     * Gets the underlying platform.
     *
     * @return Platform.
     */
    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the URL to download the distribution.
     *
     * @return URL, may be {@code null} if the URL must be resolved automatically or with another method.
     */
    @Nullable
    public URL getDistributionUrl() {
        return distributionUrl;
    }

    /**
     * Gets the path to the temporary directory.
     *
     * @return Path.
     */
    @Nonnull
    public Path getTemporaryDirectoryPath() {
        return temporaryDirectoryPath;
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
