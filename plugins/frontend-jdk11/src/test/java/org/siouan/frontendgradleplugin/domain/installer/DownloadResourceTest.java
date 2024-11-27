package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.ChannelProvider;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;

/**
 * <b>Note on verifications</b>: exhaustive verification of interactions on the resource output channel is not possible
 * with this version of Mockito: it throws a {@link NoInteractionsWanted} exception because the
 * {@link FileChannel#close()} method call has not been verified. However, this method being declared final, it can not
 * be verified by definition.
 */
@ExtendWith(MockitoExtension.class)
class DownloadResourceTest {

    private static final Path DESTINATION_FILE_PATH = Path.of("/dir1/dest");

    private static final Path RESOURCE_FILE_PATH = Path.of("/dir2/resource");

    private static final Path TEMPORARY_FILE_PATH = Path.of("/dir3/tmp");

    @Mock
    private HttpResponse httpResponse;

    @Mock
    private FileManager fileManager;

    @Mock
    private ChannelProvider channelProvider;

    @Mock
    private HttpClientProvider httpClientProvider;

    @Mock
    private HttpClient httpClient;

    @Mock
    private InputStream inputStream;

    @InjectMocks
    private DownloadResource usecase;

    @BeforeEach
    void setUp() {
        when(httpClientProvider.getInstance()).thenReturn(httpClient);
        usecase = new DownloadResource(fileManager, channelProvider, httpClientProvider, mock(Logger.class));
    }

    @Test
    void should_retry_and_fail_when_http_server_is_not_reachable() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommandWithOneRetry();
        final IOException exception1 = new IOException();
        final IOException exception2 = new IOException();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenThrow(
            exception1, exception2);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isInstanceOfSatisfying(
            RetryableResourceDownloadException.class,
            retryableResourceDownloadException -> assertThat(retryableResourceDownloadException).hasCause(exception2));

        verify(fileManager, times(2)).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_retry_and_fail_when_http_response_cannot_be_read() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommandWithOneRetry();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        final IOException exception1 = new IOException();
        final IOException exception2 = new IOException();
        when(httpResponse.getInputStream()).thenThrow(exception1, exception2);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isInstanceOfSatisfying(
            RetryableResourceDownloadException.class,
            retryableResourceDownloadException -> assertThat(retryableResourceDownloadException).hasCause(exception2));

        verify(httpResponse, times(2)).close();
        verify(fileManager, times(2)).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_retry_and_fail_when_temporary_file_cannot_be_created_to_write_resource() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommandWithOneRetry();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final IOException exception1 = new IOException();
        final IOException exception2 = new IOException();
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenThrow(
            exception1, exception2);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isInstanceOfSatisfying(
            RetryableResourceDownloadException.class,
            retryableResourceDownloadException -> assertThat(retryableResourceDownloadException).hasCause(exception2));

        verify(resourceInputChannel, times(2)).close();
        verify(httpResponse, times(2)).close();
        verify(fileManager, times(2)).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_retry_and_fail_when_http_response_status_code_is_not_ok_and_retryable() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommand(2, Set.of(404));
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(404);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isInstanceOf(
            ResourceDownloadException.class);

        verify(resourceInputChannel, times(2)).close();
        verify(httpResponse, times(2)).close();
        verify(fileManager, times(2)).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_not_retry_and_fail_when_http_response_status_code_is_not_ok_and_not_retryable() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommand(2, Set.of(502));
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(404);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isInstanceOf(
            ResourceDownloadException.class);

        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_fail_without_retry_when_resource_writing_fails() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommandWithOneRetry();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(200);
        final IOException exception1 = new IOException();
        final IOException exception2 = new IOException();
        when(resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE)).thenThrow(exception1,
            exception2);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isInstanceOfSatisfying(
            RetryableResourceDownloadException.class,
            retryableResourceDownloadException -> assertThat(retryableResourceDownloadException).hasCause(exception2));

        verify(resourceInputChannel, times(2)).close();
        verify(httpResponse, times(2)).close();
        verify(fileManager, times(2)).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_not_retry_but_fail_when_temporary_file_cannot_be_moved_to_destination_file() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommandWithOneRetry();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(200);
        final Exception expectedException = new IOException();
        when(fileManager.move(downloadResourceCommand.getTemporaryFilePath(),
            downloadResourceCommand.getDestinationFilePath(), StandardCopyOption.REPLACE_EXISTING)).thenThrow(
            expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isEqualTo(expectedException);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_download_resource_after_retries() throws IOException, ResourceDownloadException {
        final int retryableHttpStatus = 502;
        final DownloadResourceCommand downloadResourceCommand = aDownloadResourceCommand(10,
            Set.of(retryableHttpStatus));
        final IOException exception = new IOException();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings()))
            .thenThrow(exception)
            .thenReturn(httpResponse);
        when(httpResponse.getInputStream()).thenThrow(new IOException()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))
            .thenThrow(exception)
            .thenReturn(resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(retryableHttpStatus, 200);
        when(resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE))
            .thenThrow(exception)
            .thenReturn(1L);
        when(fileManager.deleteIfExists(downloadResourceCommand.getTemporaryFilePath())).thenThrow(exception);
        when(fileManager.move(downloadResourceCommand.getTemporaryFilePath(),
            downloadResourceCommand.getDestinationFilePath(), StandardCopyOption.REPLACE_EXISTING)).thenReturn(
            downloadResourceCommand.getDestinationFilePath());

        usecase.execute(downloadResourceCommand);

        verify(resourceInputChannel, times(4)).close();
        verify(httpResponse, times(5)).close();
        verify(fileManager, times(5)).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    private DownloadResourceCommand aDownloadResourceCommandWithOneRetry() {
        return aDownloadResourceCommand(2, Set.of());
    }

    private DownloadResourceCommand aDownloadResourceCommand(final int maxDownloadAttempts,
        final Set<Integer> retryableHttpStatuses) {
        return DownloadResourceCommandFixture.aCommand(RESOURCE_FILE_PATH,
            (maxDownloadAttempts == 2) ? ProxySettingsFixture.direct() : ProxySettingsFixture.someProxySettings(),
            RetrySettingsFixture.someRetrySettings(maxDownloadAttempts, retryableHttpStatuses), TEMPORARY_FILE_PATH,
            DESTINATION_FILE_PATH);
    }
}
