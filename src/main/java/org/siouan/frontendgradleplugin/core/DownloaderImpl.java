package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;

/**
 * Class providing download utilities with efficient behavior and low impact on memory. This downloader uses a temporary
 * directory to store data being downloaded.
 */
public class DownloaderImpl implements Downloader {

    private final File temporaryDirectory;

    /**
     * Builds a downloader using the given directory as a temporary directory.
     *
     * @param temporaryDirectory Temporary directory.
     */
    public DownloaderImpl(final File temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }

    @Override
    public void download(final URL resourceUrl, final File destinationFile) throws DownloadException {
        final String resourceName = new File(resourceUrl.getPath()).getName();
        final File downloadedFile = new File(temporaryDirectory, resourceName);
        try (final ReadableByteChannel readableByteChannel = Channels.newChannel(resourceUrl.openStream());
             final FileOutputStream fileOutputStream = new FileOutputStream(downloadedFile)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (final IOException e) {
            throw new DownloadException("Resource at '" + resourceUrl + "' could not be downloaded.", e);
        }

        try {
            Files.move(downloadedFile.toPath(), destinationFile.toPath());
        } catch (final IOException e) {
            throw new DownloadException("Destination '" + destinationFile.getPath() + "' could not be created.", e);
        }
    }
}
