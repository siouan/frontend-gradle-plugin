package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
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

    private final Proxy proxy;

    private final String authorizationHeader;

    private final Path temporaryDirectoryPath;

    private final Path destinationFilePath;

    /**
     * Builds download settings.
     *
     * @param resourceUrl URL to download the resource.
     * @param proxy Proxy used for the connection.
     * @param authorizationHeader optional authorization header to send with the request.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param destinationFilePath Path to a destination file.
     */
    public DownloadSettings(@Nonnull final URL resourceUrl, @Nonnull final Proxy proxy,
                            @Nullable final String authorizationHeader, @Nonnull final Path temporaryDirectoryPath,
                            @Nonnull final Path destinationFilePath) {
        this.resourceUrl = resourceUrl;
        this.proxy = proxy;
        this.authorizationHeader = authorizationHeader;
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
     * Gets the proxy used for the connection.
     *
     * @return Proxy.
     */
    @Nonnull
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Gets the optional authorization header to send with the request.
     *
     * @return authorization header.
     */
    @Nullable
    public String getAuthorizationHeader() {
        return authorizationHeader;
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
