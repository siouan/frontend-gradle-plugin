package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Class providing download utilities with efficient behavior and low impact on memory. This downloader uses a temporary
 * directory to store data being downloaded.
 */
public class DownloaderImpl implements Downloader {

    private final Path temporaryDirectory;

    /**
     * Builds a downloader using the given directory as a temporary directory.
     *
     * @param temporaryDirectory Temporary directory.
     */
    public DownloaderImpl(final Path temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }

    @Override
    public void download(final URL resourceUrl, final Path destinationFile) throws DownloadException {
        final String resourceName = new File(resourceUrl.getPath()).getName();
        final Path downloadedFile = temporaryDirectory.resolve(resourceName);
        try (final ReadableByteChannel readableByteChannel = Channels.newChannel(resourceUrl.openStream());
             final FileChannel outputChannel = FileChannel
                 .open(downloadedFile, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW)) {
            outputChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (final IOException e) {
            throw new DownloadException("Resource at '" + resourceUrl + "' could not be downloaded.", e);
        }

        try {
            Files.move(downloadedFile, destinationFile);
        } catch (final IOException e) {
            throw new DownloadException("Destination '" + destinationFile + "' could not be created.", e);
        }
    }
}
