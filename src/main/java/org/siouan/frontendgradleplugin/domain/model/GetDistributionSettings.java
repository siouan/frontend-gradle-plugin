package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings to get a distribution.
 *
 * @since 1.1.3
 */
public class GetDistributionSettings {

    private final String distributionId;

    private final String version;

    private final Platform platform;

    private final String distributionUrlRoot;

    private final String distributionUrlPathPattern;

    private final Credentials distributionServerCredentials;

    private final ProxySettings proxySettings;

    private final Path temporaryDirectoryPath;

    /**
     * Builds settings to get a distribution.
     *
     * @param distributionId Distribution ID.
     * @param platform Underlying platform.
     * @param version Version.
     * @param distributionUrlRoot URL root part to build the exact URL to download the distribution.
     * @param distributionUrlPathPattern Trailing path pattern to build the exact URL to download the distribution.
     * @param distributionServerCredentials Credentials to authenticate on the distribution server before download.
     * @param proxySettings Proxy settings used for downloads.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @see DistributionId
     */
    public GetDistributionSettings(@Nonnull String distributionId, @Nonnull final Platform platform,
        @Nonnull final String version, @Nonnull final String distributionUrlRoot,
        @Nonnull final String distributionUrlPathPattern, @Nullable final Credentials distributionServerCredentials,
        @Nonnull final ProxySettings proxySettings, @Nonnull final Path temporaryDirectoryPath) {
        this.distributionId = distributionId;
        this.platform = platform;
        this.version = version;
        this.distributionUrlRoot = distributionUrlRoot;
        this.distributionUrlPathPattern = distributionUrlPathPattern;
        this.distributionServerCredentials = distributionServerCredentials;
        this.proxySettings = proxySettings;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
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
     * Gets the URL root part to build the exact URL to download the distribution.
     *
     * @return URL root.
     */
    @Nonnull
    public String getDistributionUrlRoot() {
        return distributionUrlRoot;
    }

    /**
     * Gets the trailing path pattern to build the exact URL to download the distribution.
     *
     * @return Path pattern.
     */
    @Nonnull
    public String getDistributionUrlPathPattern() {
        return distributionUrlPathPattern;
    }

    /**
     * Gets credentials used to authenticate on the distribution server before download.
     *
     * @return Credentials.
     */
    @Nullable
    public Credentials getDistributionServerCredentials() {
        return distributionServerCredentials;
    }

    /**
     * Gets the proxy settings used for downloads.
     *
     * @return Proxy settings.
     */
    @Nonnull
    public ProxySettings getProxySettings() {
        return proxySettings;
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
}
