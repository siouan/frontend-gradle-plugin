package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings to install a distribution.
 *
 * @since 1.1.2
 */
public class InstallSettings {

    private final Platform platform;

    private final String version;

    private final String distributionUrlPattern;

    private final Credentials distributionServerCredentials;

    private final ProxySettings proxySettings;

    private final Path temporaryDirectoryPath;

    private final Path installDirectoryPath;

    /**
     * Builds an installer.
     *
     * @param platform Underlying platform.
     * @param version Version of the distribution.
     * @param distributionUrlPattern URL pattern to download the distribution.
     * @param distributionServerCredentials Credentials to authenticate on the distribution server before download.
     * @param proxySettings Proxy settings used for downloads.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param installDirectoryPath Path to a directory where the distribution shall be installed.
     */
    public InstallSettings(@Nonnull final Platform platform, @Nonnull final String version,
        @Nonnull final String distributionUrlPattern, @Nullable final Credentials distributionServerCredentials,
        @Nonnull final ProxySettings proxySettings, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Path installDirectoryPath) {
        this.platform = platform;
        this.version = version;
        this.distributionUrlPattern = distributionUrlPattern;
        this.distributionServerCredentials = distributionServerCredentials;
        this.proxySettings = proxySettings;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.installDirectoryPath = installDirectoryPath;
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
     * Gets the version of the distribution.
     *
     * @return Version.
     */
    @Nonnull
    public String getVersion() {
        return version;
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
     * Gets the path to a temporary directory.
     *
     * @return Path.
     */
    @Nonnull
    public Path getTemporaryDirectoryPath() {
        return temporaryDirectoryPath;
    }

    /**
     * Gets the path to a directory where the distribution shall be installed.
     *
     * @return Path.
     */
    @Nonnull
    public Path getInstallDirectoryPath() {
        return installDirectoryPath;
    }
}
