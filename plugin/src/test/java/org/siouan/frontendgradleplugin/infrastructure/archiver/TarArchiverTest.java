package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.LOCAL_PLATFORM;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;
import org.siouan.frontendgradleplugin.infrastructure.system.FileManagerImpl;

/**
 * Due to the fact it is not possible to control the Apache Commons Compress library (ACC), and its internal
 * implementation and usage of input streams and output streams, this test suite stubs the {@link FileManagerImpl}
 * implementation class instead of the {@link FileManager} interface, and uses concrete archives and file system calls.
 */
@ExtendWith(MockitoExtension.class)
class TarArchiverTest {

    @TempDir
    Path targetDirectoryPath;

    @Mock
    private FileManagerImpl fileManager;

    @InjectMocks
    private TarArchiver archiver;

    @Test
    void should_fail_initializing_context_when_tar_archive_does_not_exist() throws IOException {
        final Path archiveFilePath = targetDirectoryPath.resolve("archive");
        final Exception expectedException = new IOException();
        when(fileManager.newInputStream(archiveFilePath)).thenThrow(expectedException);
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(targetDirectoryPath)
            .build();

        assertThatThrownBy(() -> archiver.initializeContext(settings)).isInstanceOf(IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_fail_initializing_context_when_uncompressing_error_occurs() throws IOException {
        final Path archiveFilePath = getResourcePath("archive.tar");
        final InputStream inputStream = mock(InputStream.class);
        when(fileManager.newInputStream(archiveFilePath)).thenReturn(inputStream);
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(targetDirectoryPath)
            .build();
        final IOException expectedException = new IOException();

        assertThatThrownBy(
            () -> new TarArchiverWithFailure(fileManager, expectedException).initializeContext(settings)).isEqualTo(
            expectedException);

        verify(inputStream).close();
        verifyNoMoreInteractions(inputStream, fileManager);
    }

    @Test
    void should_fail_getting_entry_when_tar_archive_is_invalid() throws IOException {
        final Path archiveFilePath = getResourcePath("invalid-archive.unknown");
        when(fileManager.newInputStream(archiveFilePath)).thenCallRealMethod();
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(targetDirectoryPath)
            .build();

        try (final TarArchiverContext context = archiver.initializeContext(settings)) {
            assertThatThrownBy(() -> archiver.getNextEntry(context)).isInstanceOf(IOException.class);
        }

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_fail_writing_file_entry_when_unexpected_eof_appears() throws IOException {
        final Path archiveFilePath = getResourcePath("archive.tar");
        when(fileManager.newInputStream(archiveFilePath)).thenCallRealMethod();
        final Path entryFilePath = targetDirectoryPath.resolve("aFile");
        when(fileManager.newOutputStream(entryFilePath)).thenCallRealMethod();
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(targetDirectoryPath)
            .build();

        assertThatThrownBy(() -> {
            final TarArchiver archiver = new TarArchiverWithFailure(fileManager);
            try (TarArchiverContext context = archiver.initializeContext(settings)) {
                Optional<TarEntry> option = archiver.getNextEntry(context);
                TarEntry entry;
                while (option.isPresent()) {
                    entry = option.get();
                    if (entry.isFile()) {
                        archiver.writeRegularFile(context, entry, entryFilePath);
                    }
                    option = archiver.getNextEntry(context);
                }
            }
        }).isInstanceOf(UnexpectedEofException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_check_tar_gz_archive() throws IOException {
        final Path archiveFilePath = getResourcePath("archive.tar.gz");
        when(fileManager.newInputStream(archiveFilePath)).thenCallRealMethod();
        final Path entryFilePath = targetDirectoryPath.resolve("aFile");
        when(fileManager.newOutputStream(entryFilePath)).thenCallRealMethod();
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(targetDirectoryPath)
            .build();

        int count = 0;
        try (final TarArchiverContext context = archiver.initializeContext(settings)) {
            Optional<TarEntry> option = archiver.getNextEntry(context);
            TarEntry entry;
            while (option.isPresent()) {
                entry = option.get();
                if (entry.isFile()) {
                    archiver.writeRegularFile(context, entry, entryFilePath);
                } else {
                    fail("Unexpected archive content.");
                }
                count++;
                option = archiver.getNextEntry(context);
            }
        }

        verifyNoMoreInteractions(fileManager);
        assertThat(entryFilePath).exists().hasContent("content");
        assertThat(count).isEqualTo(1);
    }

    @Test
    void should_check_tar_archive() throws IOException {
        final Path archiveFilePath = getResourcePath("archive.tar");
        when(fileManager.newInputStream(archiveFilePath)).thenCallRealMethod();
        final Path entryFilePath = targetDirectoryPath.resolve("aFile");
        when(fileManager.newOutputStream(entryFilePath)).thenCallRealMethod();
        String symbolicLinkTarget = null;
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(targetDirectoryPath)
            .build();

        int count = 0;
        try (final TarArchiverContext context = archiver.initializeContext(settings)) {
            Optional<TarEntry> option = archiver.getNextEntry(context);
            TarEntry entry;
            while (option.isPresent()) {
                entry = option.get();
                if (entry.isSymbolicLink()) {
                    symbolicLinkTarget = archiver.getSymbolicLinkTarget(context, entry);
                } else if (entry.isFile()) {
                    archiver.writeRegularFile(context, entry, entryFilePath);
                } else {
                    fail("Unexpected archive content.");
                }
                count++;
                option = archiver.getNextEntry(context);
            }
        }

        verifyNoMoreInteractions(fileManager);
        assertThat(entryFilePath).exists().usingCharset(StandardCharsets.UTF_8).hasContent("content");
        assertThat(symbolicLinkTarget).isEqualTo("./aFile");
        assertThat(count).isEqualTo(2);
    }

    /**
     * Sub-class that allows simulating I/O errors.
     */
    private static class TarArchiverWithFailure extends TarArchiver {

        private final IOException uncompressException;

        TarArchiverWithFailure(final FileManager fileManager) {
            this(fileManager, null);
        }

        TarArchiverWithFailure(final FileManager fileManager, final IOException uncompressException) {
            super(fileManager);
            this.uncompressException = uncompressException;
        }

        @Override
        InputStream uncompressInputStream(final ExplodeCommand command, final InputStream compressedInputStream)
            throws IOException {
            if (uncompressException == null) {
                return super.uncompressInputStream(command, compressedInputStream);
            } else {
                throw uncompressException;
            }
        }

        @Override
        TarArchiveInputStream buildLowLevelInputStream(final InputStream inputStream) {
            return new TarArchiveInputStreamWithFailure(inputStream);
        }
    }

    /**
     * Implementation that allows simulating unexpected scenarii.
     */
    private static class TarArchiveInputStreamWithFailure extends TarArchiveInputStream {

        public TarArchiveInputStreamWithFailure(final InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public int read(final byte[] buf, final int offset, final int numToRead) {
            return -1;
        }
    }
}
