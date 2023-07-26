package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
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

    private static final String DOWNLOAD_DIRECTORY_NAME = "download";

    private static final String RESOURCE_NAME = "resource.zip";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private HttpResponse httpResponse;
    @Mock
    private HttpResponse httpResponse2;

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
    @Mock
    private InputStream inputStream2;

    @InjectMocks
    private DownloadResource usecase;

    @BeforeEach
    void setUp() {
        when(httpClientProvider.getInstance()).thenReturn(httpClient);
        usecase = new DownloadResource(fileManager, channelProvider, httpClientProvider, mock(Logger.class));
    }

    @Test
    void should_fail_when_http_request_fails() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(Paths.get("/y45y97@p"));
        final IOException expectedException = new IOException();
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenThrow(
            expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isEqualTo(expectedException);

        verify(fileManager).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_fail_when_resource_cannot_be_downloaded() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(Paths.get("/,èjtt(é"));
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        final IOException expectedException = new IOException();
        when(httpResponse.getInputStream()).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isEqualTo(expectedException);

        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_fail_when_temporary_file_cannot_be_created() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(
            Paths.get("/volezp", "gixkkle"));
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings())).thenReturn(
            httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final IOException expectedException = new IOException();
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenThrow(
            expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_fail_when_http_response_is_not_ok() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(Paths.get("/htrsgvrqehjynjt"));
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
    void should_retry_when_http_response_is_not_ok() throws IOException, ResourceDownloadException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(Paths.get("/htrsgvrqehjynjt"));
        when(httpClient.sendGetRequest(downloadResourceCommand.getResourceUrl(),
            downloadResourceCommand.getServerCredentials(), downloadResourceCommand.getProxySettings()))
                .thenReturn(httpResponse, httpResponse2);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        when(httpResponse2.getInputStream()).thenReturn(inputStream2);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        final ReadableByteChannel resourceInputChannel2 = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream2)).thenReturn(resourceInputChannel2);
        when(channelProvider.getWritableFileChannelForNewFile(downloadResourceCommand.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(404);
        when(httpResponse2.getStatusCode()).thenReturn(200);

        usecase.execute(downloadResourceCommand);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel2, 0, Long.MAX_VALUE);
        verify(resourceInputChannel2).close();
        verify(httpResponse).close();
        verify(httpResponse2).close();
        verify(fileManager).move(downloadResourceCommand.getTemporaryFilePath(),
                downloadResourceCommand.getDestinationFilePath(), StandardCopyOption.REPLACE_EXISTING);
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_fail_when_data_transfer_tails() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(
            Paths.get("/volezp", "gixkkle"));
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
        final IOException expectedException = new IOException();
        when(resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadResourceCommand)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadResourceCommand.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void should_fail_when_temporary_file_cannot_be_moved_to_destination_file() throws IOException {
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(Paths.get("/volhrts '4"));
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
    void should_download_resource() throws IOException, ResourceDownloadException {
        final Path destinationDirectoryPath = temporaryDirectoryPath.resolve("install");
        final Path destinationFilePath = destinationDirectoryPath.resolve(RESOURCE_NAME);
        final DownloadResourceCommand downloadResourceCommand = buildDownloadParameters(destinationFilePath,
            ProxySettings
                .builder()
                .proxyType(Proxy.Type.HTTP)
                .proxyHost("localhost")
                .proxyPort(8080)
                .credentials(null)
                .build());
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

        usecase.execute(downloadResourceCommand);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).move(downloadResourceCommand.getTemporaryFilePath(),
            downloadResourceCommand.getDestinationFilePath(), StandardCopyOption.REPLACE_EXISTING);
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    private Path getTemporaryFilePath() {
        return temporaryDirectoryPath.resolve(DOWNLOAD_DIRECTORY_NAME);
    }

    private Path getResourceFilePath() {
        return temporaryDirectoryPath.resolve(RESOURCE_NAME);
    }

    private DownloadResourceCommand buildDownloadParameters(final Path destinationFilePath)
        throws MalformedURLException {
        return buildDownloadParameters(destinationFilePath, null);
    }

    private DownloadResourceCommand buildDownloadParameters(final Path destinationFilePath,
        final ProxySettings proxySettings) throws MalformedURLException {
        return new DownloadResourceCommand(getResourceFilePath().toUri().toURL(), null, proxySettings,
            getTemporaryFilePath(), destinationFilePath);
    }
}
