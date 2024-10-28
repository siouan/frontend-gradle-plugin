package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.net.Proxy;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.event.RetryEvent;
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

    private static final String RETRY_ID = DownloadResource.class.getSimpleName();

    private final FileManager fileManager;

    private final ChannelProvider channelProvider;

    private final HttpClientProvider httpClientProvider;

    private final Logger logger;

    /**
     * Downloads a resource at a given URL in a destination file, by using an intermediate temporary file. It is up to
     * the caller to ensure the temporary file is writable, and the directory receiving the destination file exists and
     * is writable.
     *
     * @param command Command providing download parameters.
     * @throws IOException If the resource transfer failed, or the resource could not be written in the temporary file,
     * or moved to the destination file. In this case, any temporary file created is removed.
     * @throws ResourceDownloadException If the resource download failed .
     */
    public void execute(final DownloadResourceCommand command) throws IOException, ResourceDownloadException {
        // Configure retry with exponential backoff.
        final RetrySettings retrySettings = command.getRetrySettings();
        final RetryConfig retryConfig = RetryConfig
            .<Void>custom()
            .maxAttempts(retrySettings.getMaxDownloadAttempts())
            .intervalFunction(IntervalFunction.ofExponentialBackoff(retrySettings.getRetryInitialIntervalMs(),
                retrySettings.getRetryIntervalMultiplier(), retrySettings.getRetryMaxIntervalMs()))
            .retryExceptions(RetryableResourceDownloadException.class)
            .build();
        final Retry retry = Retry.of(RETRY_ID, retryConfig);
        retry
            .getEventPublisher()
            .onRetry(event -> logAttemptFailure(retrySettings, event))
            .onError(event -> logAttemptFailure(retrySettings, event));

        // Download resource.
        try {
            Retry.decorateCheckedRunnable(retry, () -> attemptDownload(command)).run();
        } catch (final Throwable e) {
            // At this point, a single type of exception is likely to be catched. But in case of a bug, type casting may
            // fail and no information will be logged about the real exception catched. That's why logging the exception
            // is a precondition to ease troubleshooting with end-users.
            logger.debug("All download attempts failed", e);
            throw (ResourceDownloadException) e;
        }

        fileManager.move(command.getTemporaryFilePath(), command.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING);
    }

    private void attemptDownload(final DownloadResourceCommand command) throws ResourceDownloadException {
        final ProxySettings proxySettings = command.getProxySettings();
        if (proxySettings.getProxyType() == Proxy.Type.DIRECT) {
            logger.info("Downloading resource at '{}' (proxy: DIRECT)", command.getResourceUrl());
        } else {
            logger.info("Downloading resource at '{}' (proxy: {}/{}:{})", command.getResourceUrl(),
                proxySettings.getProxyType(), proxySettings.getProxyHost(), proxySettings.getProxyPort());
        }

        final HttpClient httpClient = httpClientProvider.getInstance();
        try (final HttpResponse response = httpClient.sendGetRequest(command.getResourceUrl(),
            command.getServerCredentials(), command.getProxySettings());
             final ReadableByteChannel resourceInputChannel = channelProvider.getReadableByteChannel(
                 response.getInputStream());
             final FileChannel resourceOutputChannel = channelProvider.getWritableFileChannelForNewFile(
                 command.getTemporaryFilePath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE,
                 StandardOpenOption.TRUNCATE_EXISTING)) {
            final int statusCode = response.getStatusCode();
            logger.debug("---> {}/{} {} {}", response.getProtocol(), response.getVersion(), statusCode,
                response.getReasonPhrase());
            if (statusCode == 200) {
                resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
            } else {
                final String errorMessage =
                    "Unexpected HTTP response: " + response.getProtocol() + '/' + response.getVersion() + ' '
                        + statusCode + ' ' + response.getReasonPhrase();
                if (command.getRetrySettings().getRetryHttpStatuses().contains(statusCode)) {
                    throw new RetryableResourceDownloadException(errorMessage);
                } else {
                    // Download failed because the server responded with a non-retryable HTTP status code.
                    throw new ResourceDownloadException(errorMessage);
                }
            }
        } catch (final ResourceDownloadException e) {
            deleteTemporaryFileOrWarn(command.getTemporaryFilePath());
            throw e;
        } catch (final IOException e) {
            deleteTemporaryFileOrWarn(command.getTemporaryFilePath());
            throw new RetryableResourceDownloadException(e);
        }
    }

    private void logAttemptFailure(final RetrySettings retrySettings, final RetryEvent retryEvent) {
        final int maxDownloadAttempts = retrySettings.getMaxDownloadAttempts();
        // Log attempt exception message just in case retry is enabled.
        if (maxDownloadAttempts > 1) {
            logger.info("Attempt {} of {}: {}", retryEvent.getNumberOfRetryAttempts(), maxDownloadAttempts,
                retryEvent.getLastThrowable().getMessage());
        }
    }

    public void deleteTemporaryFileOrWarn(final Path temporaryFilePath) {
        try {
            fileManager.deleteIfExists(temporaryFilePath);
        } catch (IOException e) {
            // Ignore this second exception as it would hide the first exception.
            logger.warn("Unexpected error while deleting file after error: {}", temporaryFilePath);
        }
    }
}
