package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings to get a distribution.
 *
 * @since 1.1.3
 */
public class GetDistributionSettings {

    private final String distributionId;

    private final String version;

    private final Platform platform;

    private final Path temporaryDirectoryPath;

    private final String distributionUrlPattern;

    private final Proxy proxy;

    /**
     * Builds settings to get a distribution.
     *
     * @param distributionId Distribution ID.
     * @param platform Underlying platform.
     * @param version Version.
     * @param distributionUrlPattern URL pattern to download the distribution.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param proxy Proxy used for the connection.
     * @see DistributionId
     */
    public GetDistributionSettings(@Nonnull String distributionId, @Nonnull final Platform platform,
        @Nonnull final String version, @Nonnull final String distributionUrlPattern,
        @Nonnull final Path temporaryDirectoryPath, @Nonnull final Proxy proxy) {
        this.distributionId = distributionId;
        this.platform = platform;
        this.version = version;
        this.distributionUrlPattern = distributionUrlPattern;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.proxy = proxy;
    }

    /**
     * Gets the distribution ID.
     *
     * @return ID.
     */
    @Nonnull
    public String getDistributionId() {
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
     * Gets the URL pattern to download the distribution.
     *
     * @return URL pattern.
     */
    @Nonnull
    public String getDistributionUrlPattern() {
        return distributionUrlPattern;
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
