package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.IOErrorCode;
import org.siouan.frontendgradleplugin.domain.model.DownloadParameters;
import org.siouan.frontendgradleplugin.domain.provider.ChannelProvider;

@ExtendWith(MockitoExtension.class)
class DownloadResourceTest {

    private static final String DOWNLOAD_DIRECTORY_NAME = "download";

    private static final String RESOURCE_NAME = "resource.zip";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private ChannelProvider channelProvider;

    @InjectMocks
    private DownloadResource usecase;

    @Test
    void shouldFailWhenResourceCannotBeDownloaded() throws IOException {
        final DownloadParameters downloadParameters = buildDownloadParameters(Paths.get("/y45y97@p"));
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).thenThrow(IOException.class);

        assertThatThrownBy(() -> usecase.execute(downloadParameters))
            .isInstanceOfSatisfying(FrontendIOException.class,
                e -> assertThat(e.getIoErrorCode()).isEqualTo(IOErrorCode.NON_DOWNLOADABLE_RESOURCE_ERROR))
            .hasCauseInstanceOf(IOException.class);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verifyNoMoreInteractions(channelProvider);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeCreated() throws IOException, URISyntaxException {
        final DownloadParameters downloadParameters = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        Files.createFile(Paths.get(downloadParameters.getResourceUrl().toURI()));
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).then(
            invocation -> Channels.newChannel(downloadParameters.getResourceUrl().openStream()));
        final Path temporaryFilePath = downloadParameters.getTemporaryDirectoryPath().resolve(RESOURCE_NAME);
        final IOException exception = new IOException();
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenThrow(exception);

        assertThatThrownBy(() -> usecase.execute(downloadParameters))
            .isInstanceOfSatisfying(FrontendIOException.class,
                e -> assertThat(e.getIoErrorCode()).isEqualTo(IOErrorCode.NON_WRITABLE_FILE_ERROR))
            .hasCause(exception);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verify(channelProvider).getWritableFileChannelForNewFile(temporaryFilePath);
        verifyNoMoreInteractions(channelProvider);
    }

    @Test
    void shouldFailWhenDataTransferFails() throws IOException, URISyntaxException {
        final DownloadParameters downloadParameters = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        Files.createFile(Paths.get(downloadParameters.getResourceUrl().toURI()));
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).then(
            invocation -> Channels.newChannel(downloadParameters.getResourceUrl().openStream()));
        final Path temporaryFilePath = downloadParameters.getTemporaryDirectoryPath().resolve(RESOURCE_NAME);
        final FileChannel temporaryFileChannel = mock(FileChannel.class);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenReturn(temporaryFileChannel);
        when(temporaryFileChannel.transferFrom(any(ReadableByteChannel.class), eq(0L), eq(Long.MAX_VALUE))).thenThrow(
            IOException.class);

        assertThatThrownBy(() -> usecase.execute(downloadParameters))
            .isInstanceOfSatisfying(FrontendIOException.class,
                e -> assertThat(e.getIoErrorCode()).isEqualTo(IOErrorCode.DATA_TRANSFER_ERROR))
            .hasCauseInstanceOf(IOException.class);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verify(channelProvider).getWritableFileChannelForNewFile(temporaryFilePath);
        verify(temporaryFileChannel).transferFrom(any(ReadableByteChannel.class), eq(0L), eq(Long.MAX_VALUE));
        verifyNoMoreInteractions(channelProvider, temporaryFileChannel);
    }

    @Test
    void shouldFailWhenResourceInputChannelCannotBeClosed() throws IOException, URISyntaxException {
        final DownloadParameters downloadParameters = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        Files.createFile(Paths.get(downloadParameters.getResourceUrl().toURI()));
        Files.createDirectory(getDownloadDirectoryPath());
        final ReadableByteChannel resourceInputChannel = mock(ReadableByteChannel.class);
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).thenReturn(
            resourceInputChannel);
        final Path temporaryFilePath = downloadParameters.getTemporaryDirectoryPath().resolve(RESOURCE_NAME);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenReturn(
            new MockFileChannel(false));
        doThrow(IOException.class).when(resourceInputChannel).close();

        assertThatThrownBy(() -> usecase.execute(downloadParameters))
            .isInstanceOfSatisfying(FrontendIOException.class,
                e -> assertThat(e.getIoErrorCode()).isEqualTo(IOErrorCode.CHANNEL_CLOSE_ERROR))
            .hasCauseInstanceOf(IOException.class);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verify(channelProvider).getWritableFileChannelForNewFile(temporaryFilePath);
        verify(resourceInputChannel).close();
        verifyNoMoreInteractions(channelProvider, resourceInputChannel);
    }

    @Test
    void shouldFailWhenResourceOutputChannelCannotBeClosed() throws IOException, URISyntaxException {
        final DownloadParameters downloadParameters = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        Files.createFile(Paths.get(downloadParameters.getResourceUrl().toURI()));
        Files.createDirectory(getDownloadDirectoryPath());
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).then(
            invocation -> Channels.newChannel(downloadParameters.getResourceUrl().openStream()));
        final Path temporaryFilePath = downloadParameters.getTemporaryDirectoryPath().resolve(RESOURCE_NAME);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).thenReturn(new MockFileChannel(true));

        assertThatThrownBy(() -> usecase.execute(downloadParameters))
            .isInstanceOfSatisfying(FrontendIOException.class,
                e -> assertThat(e.getIoErrorCode()).isEqualTo(IOErrorCode.CHANNEL_CLOSE_ERROR))
            .hasCauseInstanceOf(IOException.class);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verify(channelProvider).getWritableFileChannelForNewFile(temporaryFilePath);
        verifyNoMoreInteractions(channelProvider);
    }

    @Test
    void shouldFailWhenTemporaryFileCannotBeMovedToDestinationFile() throws IOException, URISyntaxException {
        final DownloadParameters downloadParameters = buildDownloadParameters(Paths.get("/volezp", "gixkkle"));
        Files.createFile(Paths.get(downloadParameters.getResourceUrl().toURI()));
        Files.createDirectory(getDownloadDirectoryPath());
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).then(
            invocation -> Channels.newChannel(downloadParameters.getResourceUrl().openStream()));
        final Path temporaryFilePath = downloadParameters.getTemporaryDirectoryPath().resolve(RESOURCE_NAME);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).then(
            invocation -> FileChannel.open(invocation.getArgument(0), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE_NEW));

        assertThatThrownBy(() -> usecase.execute(downloadParameters))
            .isInstanceOfSatisfying(FrontendIOException.class,
                e -> assertThat(e.getIoErrorCode()).isEqualTo(IOErrorCode.FILE_MOVE_ERROR))
            .hasCauseInstanceOf(IOException.class);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verify(channelProvider).getWritableFileChannelForNewFile(temporaryFilePath);
        verifyNoMoreInteractions(channelProvider);
    }

    @Test
    void shouldDownloadLocalResource() throws Exception {
        final Path destinationDirectoryPath = temporaryDirectoryPath.resolve("install");
        final Path destinationFilePath = destinationDirectoryPath.resolve(RESOURCE_NAME);
        final DownloadParameters downloadParameters = buildDownloadParameters(destinationFilePath);
        Files.createFile(Paths.get(downloadParameters.getResourceUrl().toURI()));
        Files.createDirectory(getDownloadDirectoryPath());
        Files.createDirectory(destinationDirectoryPath);
        when(channelProvider.getReadableByteChannel(downloadParameters.getResourceUrl())).then(
            invocation -> Channels.newChannel(downloadParameters.getResourceUrl().openStream()));
        final Path temporaryFilePath = downloadParameters.getTemporaryDirectoryPath().resolve(RESOURCE_NAME);
        when(channelProvider.getWritableFileChannelForNewFile(temporaryFilePath)).then(
            invocation -> FileChannel.open(invocation.getArgument(0), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE_NEW));

        usecase.execute(downloadParameters);

        verify(channelProvider).getReadableByteChannel(downloadParameters.getResourceUrl());
        verify(channelProvider).getWritableFileChannelForNewFile(temporaryFilePath);
        verifyNoMoreInteractions(channelProvider);
        assertThat(Files.readAllBytes(destinationFilePath)).isEqualTo(Files.readAllBytes(getResourceFilePath()));
    }

    private Path getDownloadDirectoryPath() {
        return temporaryDirectoryPath.resolve(DOWNLOAD_DIRECTORY_NAME);
    }

    private Path getResourceFilePath() {
        return temporaryDirectoryPath.resolve(RESOURCE_NAME);
    }

    private DownloadParameters buildDownloadParameters(@Nonnull final Path destinationFilePath)
        throws MalformedURLException {
        return new DownloadParameters(getResourceFilePath().toUri().toURL(), getDownloadDirectoryPath(),
            destinationFilePath);
    }

    private static class MockFileChannel extends FileChannel {

        private final boolean failOnCloseEnabled;

        public MockFileChannel(final boolean failOnCloseEnabled) {
            this.failOnCloseEnabled = failOnCloseEnabled;
        }

        @Override
        public int read(ByteBuffer dst) {
            return 0;
        }

        @Override
        public long read(ByteBuffer[] dsts, int offset, int length) {
            return 0;
        }

        @Override
        public int write(ByteBuffer src) {
            return 0;
        }

        @Override
        public long write(ByteBuffer[] srcs, int offset, int length) {
            return 0;
        }

        @Override
        public long position() {
            return 0;
        }

        @Override
        public FileChannel position(long newPosition) {
            return null;
        }

        @Override
        public long size() {
            return 0;
        }

        @Override
        public FileChannel truncate(long size) {
            return null;
        }

        @Override
        public void force(boolean metaData) {

        }

        @Override
        public long transferTo(long position, long count, WritableByteChannel target) {
            return 0;
        }

        @Override
        public long transferFrom(ReadableByteChannel src, long position, long count) {
            return 0;
        }

        @Override
        public int read(ByteBuffer dst, long position) {
            return 0;
        }

        @Override
        public int write(ByteBuffer src, long position) {
            return 0;
        }

        @Override
        public MappedByteBuffer map(MapMode mode, long position, long size) {
            return null;
        }

        @Override
        public FileLock lock(long position, long size, boolean shared) {
            return null;
        }

        @Override
        public FileLock tryLock(long position, long size, boolean shared) {
            return null;
        }

        @Override
        protected void implCloseChannel() throws IOException {
            if (failOnCloseEnabled) {
                throw new IOException();
            }
        }
    }
}

