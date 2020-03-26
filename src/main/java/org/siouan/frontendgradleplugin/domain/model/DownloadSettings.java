package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings to download a resource.
 *
 * @since 2.0.0
 */
public class DownloadSettings {

    /**
     * URL to download the resource.
     */
    private final URL resourceUrl;

    /**
     * Path to a temporary directory.
     */
    private final Path temporaryDirectoryPath;

    /**
     * Path to the file where the resource will be written.
     */
    private final Path destinationFilePath;

    /**
     * Builds download settings.
     *
     * @param resourceUrl URL to download the resource.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param destinationFilePath Path to a destination file.
     */
    public DownloadSettings(@Nonnull final URL resourceUrl, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Path destinationFilePath) {
        this.resourceUrl = resourceUrl;
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
