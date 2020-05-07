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
import java.net.URLConnection;
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
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.provider.URLConnectionProvider;

/**
 * <b>Note on verifications</b>: exhaustive verification of interactions on the resource output channel is not possible
 * with this version of Mockito: it throws a {@link NoInteractionsWanted} exception because the {@link
 * FileChannel#close()} method call has not been verified. However, this method being declared final, it can not be
 * verified by definition.
 */
@ExtendWith(MockitoExtension.class)
class DownloadResourceTest {

    private static final String DOWNLOAD_DIRECTORY_NAME = "download";

    private static final Proxy PROXY = Proxy.NO_PROXY;

    private static final String RESOURCE_NAME = "resource.zip";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private FileManager fileManager;

    @Mock
    private ChannelProvider channelProvider;

    @Mock
    private ApplyAuthorization applyAuthorization;

    @Mock
    private URLConnectionProvider urlConnectionProvider;

    @Mock
    private URLConnection urlConnection;

    @Mock
    private InputStream inputStream;

    @InjectMocks
    private DownloadResource usecase;

    @BeforeEach
    void setUp() {
        usecase = new DownloadResource(fileManager, channelProvider, urlConnectionProvider, applyAuthorization,
            mock(Logger.class));
    }

    @Test
    void shouldFailWhenConnectionCannotBeCreated() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/y45y97@p"));
        final IOException expectedException = new IOException();
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization);
    }

    @Test
    void shouldFailWhenResourceCannotBeDownloaded() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/y45y97@p"));
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenReturn(urlConnection);
        final IOException expectedException = new IOException();
        when(urlConnection.getInputStream()).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(
                downloadSettings.getDestinationFilePath().getFileName().toString() + DownloadResource.TMP_EXTENSION);
        verify(fileManager).deleteIfExists(temporaryFilePath);
        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeCreated() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenReturn(urlConnection);
        when(urlConnection.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(
                downloadSettings.getDestinationFilePath().getFileName().toString() + DownloadResource.TMP_EXTENSION);
        final IOException expectedException = new IOException();
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath, StandardOpenOption.WRITE,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verify(fileManager).deleteIfExists(temporaryFilePath);
        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization, resourceInputChannel);
    }

    @Test
    void shouldFailWhenDataTransferFails() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenReturn(urlConnection);
        when(urlConnection.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(
                downloadSettings.getDestinationFilePath().getFileName().toString() + DownloadResource.TMP_EXTENSION);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath, StandardOpenOption.WRITE,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(resourceOutputChannel);
        final IOException expectedException = new IOException();
        when(resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verify(fileManager).deleteIfExists(temporaryFilePath);
        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization, resourceInputChannel);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeMovedToDestinationFile() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenReturn(urlConnection);
        when(urlConnection.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(
                downloadSettings.getDestinationFilePath().getFileName().toString() + DownloadResource.TMP_EXTENSION);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath, StandardOpenOption.WRITE,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(resourceOutputChannel);
        final Exception expectedException = new IOException();
        when(fileManager.move(temporaryFilePath, downloadSettings.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization, resourceInputChannel);
    }

    @Test
    void shouldDownloadLocalResourceWithoutBasicAuthentication() throws IOException {
        final Path destinationDirectoryPath = temporaryDirectoryPath.resolve("install");
        final Path destinationFilePath = destinationDirectoryPath.resolve(RESOURCE_NAME);
        final DownloadSettings downloadSettings = buildDownloadParameters(destinationFilePath);
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenReturn(urlConnection);
        when(urlConnection.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(
                downloadSettings.getDestinationFilePath().getFileName().toString() + DownloadResource.TMP_EXTENSION);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath, StandardOpenOption.WRITE,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(resourceOutputChannel);

        usecase.execute(downloadSettings);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(fileManager).move(temporaryFilePath, downloadSettings.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING);
        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization, resourceInputChannel);
    }

    @Test
    void shouldDownloadLocalResourceWithBasicAuthentication() throws IOException {
        final Path destinationDirectoryPath = temporaryDirectoryPath.resolve("install");
        final Path destinationFilePath = destinationDirectoryPath.resolve(RESOURCE_NAME);
        final Credentials credentials = new Credentials("username", "password");
        final DownloadSettings downloadSettings = buildDownloadParameters(destinationFilePath, credentials);
        when(urlConnectionProvider.openConnection(downloadSettings.getResourceUrl(),
            downloadSettings.getProxy())).thenReturn(urlConnection);
        when(urlConnection.getInputStream()).thenReturn(inputStream);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(inputStream)).thenReturn(resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(
                downloadSettings.getDestinationFilePath().getFileName().toString() + DownloadResource.TMP_EXTENSION);
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath, StandardOpenOption.WRITE,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(resourceOutputChannel);

        usecase.execute(downloadSettings);

        verify(applyAuthorization).execute(urlConnection, DownloadResource.AUTHORIZATION_HEADER, credentials);
        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(fileManager).move(temporaryFilePath, downloadSettings.getDestinationFilePath(),
            StandardCopyOption.REPLACE_EXISTING);
        verifyNoMoreInteractions(fileManager, channelProvider, urlConnectionProvider, urlConnection, inputStream,
            applyAuthorization, resourceInputChannel);
    }

    private Path getDownloadDirectoryPath() {
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
        @Nullable final Credentials credentials) throws MalformedURLException {
        return new DownloadSettings(getResourceFilePath().toUri().toURL(), credentials, PROXY,
            getDownloadDirectoryPath(), destinationFilePath);
    }
}
