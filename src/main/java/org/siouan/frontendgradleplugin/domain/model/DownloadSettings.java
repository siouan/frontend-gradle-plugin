package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings to download a resource.
 *
 * @since 2.0.0
 */
public class DownloadSettings {

    private final URL resourceUrl;

    private final Credentials serverCredentials;

    private final ProxySettings proxySettings;

    private final Path temporaryDirectoryPath;

    private final Path destinationFilePath;

    /**
     * Builds download settings.
     *
     * @param resourceUrl URL to download the resource.
     * @param serverCredentials Credentials to authenticate on the server before download.
     * @param proxySettings Proxy settings used for the connection.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param destinationFilePath Path to a destination file.
     */
    public DownloadSettings(@Nonnull final URL resourceUrl, @Nullable final Credentials serverCredentials,
        @Nonnull final ProxySettings proxySettings, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Path destinationFilePath) {
        this.resourceUrl = resourceUrl;
        this.serverCredentials = serverCredentials;
        this.proxySettings = proxySettings;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.destinationFilePath = destinationFilePath;
    }

    /**
     * Gets the URL to download the resource.
     *
     * @return URL.
     */
    @Nonnull
    public URL getResourceUrl() {
        return resourceUrl;
    }

    /**
     * Gets credentials used to authenticate on the server before download.
     *
     * @return Credentials.
     */
    @Nullable
    public Credentials getServerCredentials() {
        return serverCredentials;
    }

    /**
     * Gets the proxy settings used for the connection.
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
     * Gets the path to a destination file.
     *
     * @return Path.
     */
    @Nonnull
    public Path getDestinationFilePath() {
        return destinationFilePath;
    }
}
