package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.IOErrorCode;
import org.siouan.frontendgradleplugin.domain.model.DownloadParameters;
import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;

/**
 * Downloads a resource with efficient behavior and low impact on memory. This downloader uses a temporary directory to
 * store data being downloaded.
 */
public class DownloadResource {

    private final ChannelProvider channelProvider;

    public DownloadResource(@Nonnull final ChannelProvider channelProvider) {
        this.channelProvider = channelProvider;
    }

    /**
     * Downloads a resource at a given URL in a destination file, by using a temporary directory. It is up to the caller
     * to ensure the temporary directory and the directory receiving the destination file exist and are writable.
     *
     * @param downloadParameters Download parameters.
     * @throws FrontendIOException If the resource could not be downloaded, or could not be written in the temporary
     * directory, or moved to the destination file.
     * @see IOErrorCode#NON_DOWNLOADABLE_RESOURCE_ERROR
     * @see IOErrorCode#NON_WRITABLE_FILE_ERROR
     * @see IOErrorCode#DATA_TRANSFER_ERROR
     * @see IOErrorCode#CHANNEL_CLOSE_ERROR
     */
    public void execute(@Nonnull final DownloadParameters downloadParameters) throws FrontendIOException {
        final URL resourceUrl = downloadParameters.getResourceUrl();
        final String resourceName = new File(resourceUrl.getPath()).getName();
        final Path downloadedFile = downloadParameters.getTemporaryDirectoryPath().resolve(resourceName);
        try (final ReadableByteChannel resourceInputChannel = openResourceInputChannel(resourceUrl);
             final FileChannel resourceOutputChannel = openResourceOutputChannel(downloadedFile)) {
            transferData(resourceInputChannel, resourceOutputChannel);
        } catch (final IOException e) {
            throw new FrontendIOException(IOErrorCode.CHANNEL_CLOSE_ERROR,
                "Input channel or output channel could not be closed.", e);
        }

        final Path destinationFile = downloadParameters.getDestinationFilePath();
        try {
            Files.move(downloadedFile, destinationFile);
        } catch (final IOException e) {
            throw new FrontendIOException(IOErrorCode.FILE_MOVE_ERROR,
                "File located at '" + downloadedFile + "' could not be moved to '" + destinationFile + "'.", e);
        }
    }

    @Nonnull
    private ReadableByteChannel openResourceInputChannel(@Nonnull final URL resourceUrl) throws FrontendIOException {
        try {
            return channelProvider.getReadableByteChannel(resourceUrl);
        } catch (final IOException e) {
            throw new FrontendIOException(IOErrorCode.NON_DOWNLOADABLE_RESOURCE_ERROR,
                "Resource at '" + resourceUrl + "' could not be opened for download.", e);
        }
    }

    @Nonnull
    private FileChannel openResourceOutputChannel(@Nonnull final Path downloadedFilePath) throws FrontendIOException {
        try {
            return channelProvider.getWritableFileChannelForNewFile(downloadedFilePath);
        } catch (final IOException e) {
            throw new FrontendIOException(IOErrorCode.NON_WRITABLE_FILE_ERROR,
                "File located at '" + downloadedFilePath + "' could not be opened.", e);
        }
    }

    private void transferData(@Nonnull final ReadableByteChannel inputChannel, @Nonnull final FileChannel outputChannel)
        throws FrontendIOException {
        try {
            outputChannel.transferFrom(inputChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new FrontendIOException(IOErrorCode.DATA_TRANSFER_ERROR, "An error occurred while downloading data.",
                e);
        }
    }
}
