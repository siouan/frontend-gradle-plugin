package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.ChannelProvider;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;

/**
 * Downloads a resource with efficient behavior and low impact on memory. This downloader uses a temporary directory to
 * store data being downloaded.
 */
@RequiredArgsConstructor
public class DownloadResource {

    public static final int MAX_TRIES = 3;
    private final FileManager fileManager;

    private final ChannelProvider channelProvider;

    private final HttpClientProvider httpClientProvider;

    private final Logger logger;

    /**
     * Downloads a resource at a given URL in a destination file, by using an intermediate temporary file. It is up to
     * the caller to ensure the temporary file is writable, and the directory receiving the destination file exists and
     * is writable.
     *
     * @param downloadResourceCommand Command providing download parameters.
     * @throws IOException If the resource transfer failed, or the resource could not be written in the temporary file,
     * or moved to the destination file. In this case, any temporary file created is removed.
     * @throws ResourceDownloadException If the distribution download failed.
     */
    public void execute(final DownloadResourceCommand downloadResourceCommand)
        throws IOException, ResourceDownloadException {
        final URL resourceUrl = downloadResourceCommand.getResourceUrl();
        final ProxySettings proxySettings = downloadResourceCommand.getProxySettings();

        if (proxySettings == null) {
            logger.info("Downloading resource at '{}' (proxy: DIRECT)", downloadResourceCommand.getResourceUrl());
        } else {
            logger.info("Downloading resource at '{}' (proxy: {}/{}:{})", downloadResourceCommand.getResourceUrl(),
                proxySettings.getProxyType(), proxySettings.getProxyHost(), proxySettings.getProxyPort());
        }

        int trie = 0;
        while (trie < MAX_TRIES) {
            try (final HttpResponse response = httpClientProvider
                    .getInstance()
                    .sendGetRequest(resourceUrl, downloadResourceCommand.getServerCredentials(), proxySettings);
                 final ReadableByteChannel resourceInputChannel = channelProvider.getReadableByteChannel(
                         response.getInputStream());
                 final FileChannel resourceOutputChannel = channelProvider.getWritableFileChannelForNewFile(
                         downloadResourceCommand.getTemporaryFilePath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE,
                         StandardOpenOption.TRUNCATE_EXISTING)) {
                logger.debug("---> {}/{} {} {}", response.getProtocol(), response.getVersion(), response.getStatusCode(),
                        response.getReasonPhrase());
                trie++;
                if (response.getStatusCode() != 200) {
                    if (trie == MAX_TRIES) {
                        throw new ResourceDownloadException(
                                "Unexpected HTTP response: " + response.getProtocol() + '/' + response.getVersion() + ' '
                                        + response.getStatusCode() + ' ' + response.getReasonPhrase());
                    }
                    logger.debug("---> Got status {}, retry {}/{}", response.getStatusCode(), trie, MAX_TRIES);
                    continue;
                }
                resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
                break;
            } catch (final IOException | ResourceDownloadException e) {
                fileManager.deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
                throw e;
            }
        }

        fileManager.move(downloadResourceCommand.getTemporaryFilePath(),
                downloadResourceCommand.getDestinationFilePath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
