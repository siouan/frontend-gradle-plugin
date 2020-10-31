package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.HttpClientException;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.HttpResponse;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.provider.HttpClientProvider;

/**
 * Downloads a resource with efficient behavior and low impact on memory. This downloader uses a temporary directory to
 * store data being downloaded.
 */
public class DownloadResource {

    private final FileManager fileManager;

    private final ChannelProvider channelProvider;

    private final HttpClientProvider httpClientProvider;

    private final Logger logger;

    public DownloadResource(@Nonnull final FileManager fileManager, @Nonnull final ChannelProvider channelProvider,
        @Nonnull final HttpClientProvider httpClientProvider, @Nonnull final Logger logger) {
        this.fileManager = fileManager;
        this.channelProvider = channelProvider;
        this.httpClientProvider = httpClientProvider;
        this.logger = logger;
    }

    /**
     * Downloads a resource at a given URL in a destination file, by using an intermediate temporary file. It is up to
     * the caller to ensure the temporary file is writable, and the directory receiving the destination file exists and
     * is writable.
     *
     * @param downloadSettings Download settings.
     * @throws IOException If the resource transfer failed, or the resource could not be written in the temporary file,
     * or moved to the destination file. In this case, any temporary file created is removed.
     * @throws ResourceDownloadException If the distribution download failed.
     */
    public void execute(@Nonnull final DownloadSettings downloadSettings)
        throws IOException, ResourceDownloadException {
        final URL resourceUrl = downloadSettings.getResourceUrl();
        final ProxySettings proxySettings = downloadSettings.getProxySettings();

        if (proxySettings == null) {
            logger.info("Downloading resource at '{}' (proxy: DIRECT)", downloadSettings.getResourceUrl());
        } else {
            logger.info("Downloading resource at '{}' (proxy: {}/{}:{})", downloadSettings.getResourceUrl(),
                proxySettings.getProxyType(), proxySettings.getProxyHost(), proxySettings.getProxyPort());
        }

        try (final HttpResponse response = httpClientProvider
            .getInstance()
            .sendGetRequest(resourceUrl, downloadSettings.getServerCredentials(), proxySettings);
             final ReadableByteChannel resourceInputChannel = channelProvider.getReadableByteChannel(
                 response.getInputStream());
             final FileChannel resourceOutputChannel = channelProvider.getWritableFileChannelForNewFile(
                 downloadSettings.getTemporaryFilePath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE,
                 StandardOpenOption.TRUNCATE_EXISTING)) {
            logger.debug("---> {}/{} {} {}", response.getProtocol(), response.getVersion(), response.getStatusCode(),
                response.getReasonPhrase());
            if (response.getStatusCode() != 200) {
                throw new ResourceDownloadException(
                    "Unexpected HTTP response: " + response.getProtocol() + '/' + response.getVersion() + ' ' + response
                        .getStatusCode() + ' ' + response.getReasonPhrase());
            }
            resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        } catch (final HttpClientException e) {
            fileManager.deleteIfExists(downloadSettings.getTemporaryFilePath());
            throw new ResourceDownloadException(e);
        } catch (final IOException | ResourceDownloadException e) {
            fileManager.deleteIfExists(downloadSettings.getTemporaryFilePath());
            throw e;
        }

        fileManager.move(downloadSettings.getTemporaryFilePath(), downloadSettings.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING);
    }
}
