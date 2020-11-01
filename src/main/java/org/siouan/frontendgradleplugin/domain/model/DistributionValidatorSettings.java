package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings for distribution validation.
 *
 * @since 2.0.0
 */
public class DistributionValidatorSettings {

    private final Path temporaryDirectoryPath;

    private final URL distributionUrl;

    private final Credentials distributionServerCredentials;

    private final ProxySettings proxySettings;

    private final Path distributionFilePath;

    /**
     * Builds validator settings.
     *
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param distributionUrl URL used to download the distribution.
     * @param distributionServerCredentials Credentials to authenticate on the distribution server before download.
     * @param proxySettings Proxy settings used for downloads.
     * @param distributionFilePath Path to the distribution archive.
     */
    public DistributionValidatorSettings(@Nonnull final Path temporaryDirectoryPath, @Nonnull final URL distributionUrl,
        @Nullable final Credentials distributionServerCredentials, @Nullable final ProxySettings proxySettings,
        @Nonnull final Path distributionFilePath) {
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.distributionUrl = distributionUrl;
        this.distributionServerCredentials = distributionServerCredentials;
        this.proxySettings = proxySettings;
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
    @Nullable
    public ProxySettings getProxySettings() {
        return proxySettings;
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
