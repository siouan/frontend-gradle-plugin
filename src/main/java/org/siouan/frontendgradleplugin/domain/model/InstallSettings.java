package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings to install a distribution.
 *
 * @since 1.1.2
 */
public class InstallSettings {

    private final Platform platform;

    private final String version;

    private final String distributionUrlPattern;

    private final Proxy proxy;

    private final Path temporaryDirectoryPath;

    private final Path installDirectoryPath;

    /**
     * Builds an installer.
     *
     * @param platform Underlying platform.
     * @param version Version of the distribution.
     * @param distributionUrlPattern URL pattern to download the distribution.
     * @param proxy Proxy used for downloads.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param installDirectoryPath Path to a directory where the distribution shall be installed.
     */
    public InstallSettings(@Nonnull final Platform platform, @Nonnull final String version,
        @Nonnull final String distributionUrlPattern, @Nonnull final Proxy proxy,
        @Nonnull final Path temporaryDirectoryPath, @Nonnull final Path installDirectoryPath) {
        this.platform = platform;
        this.version = version;
        this.distributionUrlPattern = distributionUrlPattern;
        this.proxy = proxy;
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
     * Gets the proxy used for the connection.
     *
     * @return Proxy.
     */
    @Nonnull
    public Proxy getProxy() {
        return proxy;
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
