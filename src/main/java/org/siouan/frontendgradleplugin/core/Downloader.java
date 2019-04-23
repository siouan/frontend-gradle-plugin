package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.net.URL;

/**
 * Functional interface of a component capable to download resources.
 */
@FunctionalInterface
public interface Downloader {

    /**
     * Downloads a resource at a given URL, and moves it to a destination file once completed. Before calling this
     * method, the caller must ensure the directory receiving the destination file exists and is writable.
     *
     * @param resourceUrl URL of a resource.
     * @param destinationFile Destination file.
     * @throws DownloadException If the resource could not be downloaded or the destination file could not be written.
     */
    void download(final URL resourceUrl, final File destinationFile) throws DownloadException;
}
