package org.siouan.frontendgradleplugin.core;

import java.net.URL;
import java.nio.file.Path;

/**
 * Functional interface of a component capable to download resources.
 */
@FunctionalInterface
interface Downloader {

    /**
     * Downloads a resource at a given URL, and moves it to a destination file once completed. Before calling this
     * method, the caller must ensure the directory receiving the destination file exists and is writable.
     *
     * @param resourceUrl URL of a resource.
     * @param destinationFile Destination file.
     * @throws DownloadException If the resource could not be downloaded or the destination file could not be written.
     */
    void download(final URL resourceUrl, final Path destinationFile) throws DownloadException;
}
