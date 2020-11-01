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

    private final Path temporaryFilePath;

    private final Path destinationFilePath;

    /**
     * Builds download settings.
     *
     * @param resourceUrl URL to download the resource.
     * @param serverCredentials Credentials to authenticate on the server before download.
     * @param proxySettings Proxy settings used for the connection.
     * @param temporaryFilePath Path to a temporary file.
     * @param destinationFilePath Path to a destination file.
     */
    public DownloadSettings(@Nonnull final URL resourceUrl, @Nullable final Credentials serverCredentials,
        @Nullable final ProxySettings proxySettings, @Nonnull final Path temporaryFilePath,
        @Nonnull final Path destinationFilePath) {
        this.resourceUrl = resourceUrl;
        this.serverCredentials = serverCredentials;
        this.proxySettings = proxySettings;
        this.temporaryFilePath = temporaryFilePath;
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
    @Nullable
    public ProxySettings getProxySettings() {
        return proxySettings;
    }

    /**
     * Gets the path to a temporary file.
     *
     * @return Path.
     */
    @Nonnull
    public Path getTemporaryFilePath() {
        return temporaryFilePath;
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
