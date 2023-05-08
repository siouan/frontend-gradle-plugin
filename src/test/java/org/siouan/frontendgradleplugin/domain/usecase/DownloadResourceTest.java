package org.siouan.frontendgradleplugin.domain.usecase;

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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.HttpClient;
import org.siouan.frontendgradleplugin.domain.model.HttpResponse;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.provider.HttpClientProvider;

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
    void shouldFailWhenHttpRequestFails() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/y45y97@p"));
        final IOException expectedException = new IOException();
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(fileManager).deleteIfExists(downloadSettings.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void shouldFailWhenResourceCannotBeDownloaded() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/,èjtt(é"));
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenReturn(httpResponse);
        final IOException expectedException = new IOException();
        when(httpResponse.getInputStream()).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadSettings.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeCreated() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenReturn(httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final IOException expectedException = new IOException();
        when(channelProvider.getWritableFileChannelForNewFile(downloadSettings.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenThrow(
            expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadSettings.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void shouldFailWhenHttpResponseIsNotOk() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/htrsgvrqehjynjt"));
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenReturn(httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadSettings.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(404);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isInstanceOf(ResourceDownloadException.class);

        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadSettings.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void shouldFailWhenDataTransferFails() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenReturn(httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadSettings.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(200);
        final IOException expectedException = new IOException();
        when(resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).deleteIfExists(downloadSettings.getTemporaryFilePath());
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeMovedToDestinationFile() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volhrts '4"));
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenReturn(httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadSettings.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(200);
        final Exception expectedException = new IOException();
        when(fileManager.move(downloadSettings.getTemporaryFilePath(), downloadSettings.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    @Test
    void shouldDownloadResource() throws IOException, ResourceDownloadException {
        final Path destinationDirectoryPath = temporaryDirectoryPath.resolve("install");
        final Path destinationFilePath = destinationDirectoryPath.resolve(RESOURCE_NAME);
        final DownloadSettings downloadSettings = buildDownloadParameters(destinationFilePath,
            new ProxySettings(Proxy.Type.HTTP, "localhost", 8080, null));
        when(httpClient.sendGetRequest(downloadSettings.getResourceUrl(), downloadSettings.getServerCredentials(),
            downloadSettings.getProxySettings())).thenReturn(httpResponse);
        when(httpResponse.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(downloadSettings.getTemporaryFilePath(),
            StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            resourceOutputChannel);
        when(httpResponse.getStatusCode()).thenReturn(200);

        usecase.execute(downloadSettings);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(httpResponse).close();
        verify(fileManager).move(downloadSettings.getTemporaryFilePath(), downloadSettings.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING);
        verifyNoMoreInteractions(fileManager, channelProvider, httpClientProvider, httpClient);
    }

    private Path getTemporaryFilePath() {
        return temporaryDirectoryPath.resolve(DOWNLOAD_DIRECTORY_NAME);
    }

    private Path getResourceFilePath() {
        return temporaryDirectoryPath.resolve(RESOURCE_NAME);
    }

    private DownloadSettings buildDownloadParameters(@Nonnull final Path destinationFilePath)
        throws MalformedURLException {
        return buildDownloadParameters(destinationFilePath, null);
    }

    private DownloadSettings buildDownloadParameters(@Nonnull final Path destinationFilePath,
        @Nullable final ProxySettings proxySettings) throws MalformedURLException {
        return new DownloadSettings(getResourceFilePath().toUri().toURL(), null, proxySettings, getTemporaryFilePath(),
            destinationFilePath);
    }
}
