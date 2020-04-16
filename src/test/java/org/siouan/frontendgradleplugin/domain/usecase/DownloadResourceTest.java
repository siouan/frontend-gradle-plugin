package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * <b>Note on verifications</b>: exhaustive verification of interactions on the resource output channel is not possible
 * with this version of Mockito: it throws a {@link NoInteractionsWanted} exception because the {@link
 * FileChannel#close()} method call has not been verified. However, this method being declared final, it can not be
 * verified by definition.
 */
@ExtendWith(MockitoExtension.class)
class DownloadResourceTest {

    private static final String DOWNLOAD_DIRECTORY_NAME = "download";

    private static final String RESOURCE_NAME = "resource.zip";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private FileManager fileManager;

    @Mock
    private ChannelProvider channelProvider;

    @InjectMocks
    private DownloadResource usecase;

    @Test
    void shouldFailWhenResourceCannotBeDownloaded() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/y45y97@p"));
        final IOException expectedException = new IOException();
        when(channelProvider.getReadableByteChannel(downloadSettings.getResourceUrl())).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, channelProvider);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeCreated() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(downloadSettings.getResourceUrl())).thenReturn(
            resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(downloadSettings.getDestinationFilePath().getFileName().toString());
        final IOException expectedException = new IOException();
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verifyNoMoreInteractions(fileManager, channelProvider, resourceInputChannel);
    }

    @Test
    void shouldFailWhenDataTransferFails() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(downloadSettings.getResourceUrl())).thenReturn(
            resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(downloadSettings.getDestinationFilePath().getFileName().toString());
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenReturn(resourceOutputChannel);
        final IOException expectedException = new IOException();
        when(resourceOutputChannel.transferFrom(resourceInputChannel, 0, Long.MAX_VALUE)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceInputChannel).close();
        verifyNoMoreInteractions(fileManager, channelProvider, resourceInputChannel);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeMovedToDestinationFile() throws IOException {
        final DownloadSettings downloadSettings = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(downloadSettings.getResourceUrl())).thenReturn(
            resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(downloadSettings.getDestinationFilePath().getFileName().toString());
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenReturn(resourceOutputChannel);
        final Exception expectedException = new IOException();
        when(fileManager.move(downloadSettings
                .getTemporaryDirectoryPath()
                .resolve(downloadSettings.getDestinationFilePath().getFileName()),
            downloadSettings.getDestinationFilePath())).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(downloadSettings)).isEqualTo(expectedException);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verifyNoMoreInteractions(fileManager, channelProvider, resourceInputChannel);
    }

    @Test
    void shouldDownloadLocalResource() throws Exception {
        final Path destinationDirectoryPath = temporaryDirectoryPath.resolve("install");
        final Path destinationFilePath = destinationDirectoryPath.resolve(RESOURCE_NAME);
        final DownloadSettings downloadSettings = buildDownloadParameters(destinationFilePath);
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(downloadSettings.getResourceUrl())).thenReturn(
            resourceInputChannel);
        final Path temporaryFilePath = downloadSettings
            .getTemporaryDirectoryPath()
            .resolve(downloadSettings.getDestinationFilePath().getFileName().toString());
        final FileChannel resourceOutputChannel = spy(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenReturn(resourceOutputChannel);

        usecase.execute(downloadSettings);

        verify(resourceOutputChannel).transferFrom(resourceInputChannel, 0, Long.MAX_VALUE);
        verify(resourceInputChannel).close();
        verify(fileManager).move(downloadSettings
                .getTemporaryDirectoryPath()
                .resolve(downloadSettings.getDestinationFilePath().getFileName()),
            downloadSettings.getDestinationFilePath());
        // Refer to class doc comments.
        verifyNoMoreInteractions(fileManager, channelProvider, resourceInputChannel);
    }

    private Path getDownloadDirectoryPath() {
        return temporaryDirectoryPath.resolve(DOWNLOAD_DIRECTORY_NAME);
    }

    private Path getResourceFilePath() {
        return temporaryDirectoryPath.resolve(RESOURCE_NAME);
    }

    private DownloadSettings buildDownloadParameters(@Nonnull final Path destinationFilePath)
        throws MalformedURLException {
        return new DownloadSettings(getResourceFilePath().toUri().toURL(), getDownloadDirectoryPath(),
            destinationFilePath);
    }
}

