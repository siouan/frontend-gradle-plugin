package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

public class DownloadParameters {

    private final Path temporaryDirectoryPath;

    private final URL resourceUrl;

    private final Path destinationFilePath;

    /**
     * Builds download parameters.
     *
     * @param resourceUrl URL of a resource.
     * @param temporaryDirectoryPath Temporary directory.
     * @param destinationFilePath Destination file.
     */
    public DownloadParameters(@Nonnull final URL resourceUrl, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Path destinationFilePath) {
        this.resourceUrl = resourceUrl;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.destinationFilePath = destinationFilePath;
    }

    @Nonnull
    public URL getResourceUrl() {
        return resourceUrl;
    }

    @Nonnull
    public Path getTemporaryDirectoryPath() {
        return temporaryDirectoryPath;
    }

    @Nonnull
    public Path getDestinationFilePath() {
        return destinationFilePath;
    }
}
