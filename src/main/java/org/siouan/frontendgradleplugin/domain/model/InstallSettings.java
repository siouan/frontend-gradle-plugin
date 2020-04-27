package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import java.net.URL;
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

    private final URL downloadUrl;

    private final Proxy proxy;

    private final Path temporaryDirectoryPath;

    private final Path installDirectoryPath;

    /**
     * Builds an installer.
     *
     * @param platform Underlying platform.
     * @param version Version of the distribution.
     * @param downloadUrl URL to download the distribution.
     * @param proxy Proxy used for downloads.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param installDirectoryPath Path to a directory where the distribution shall be installed.
     */
    public InstallSettings(@Nonnull final Platform platform, @Nonnull final String version,
        @Nullable final URL downloadUrl, @Nonnull final Proxy proxy, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Path installDirectoryPath) {
        this.platform = platform;
        this.version = version;
        this.downloadUrl = downloadUrl;
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
     * Gets the URL to download the distribution.
     *
     * @return URL.
     */
    @Nullable
    public URL getDownloadUrl() {
        return downloadUrl;
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
