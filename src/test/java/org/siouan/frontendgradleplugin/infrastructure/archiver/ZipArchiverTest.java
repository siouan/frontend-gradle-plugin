package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.LOCAL_PLATFORM;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;
import org.siouan.frontendgradleplugin.infrastructure.system.FileManagerImpl;

/**
 * Due to the fact it is not possible to control the Apache Commons Compress library (ACC), and its internal
 * implementation and usage of input streams and output streams, this test suite stubs the {@link FileManagerImpl}
 * implementation class instead of the {@link FileManager} interface, and uses concrete archives and file system calls.
 */
@ExtendWith(MockitoExtension.class)
class ZipArchiverTest {

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private FileManagerImpl fileManager;

    @InjectMocks
    private ZipArchiver archiver;

    @Test
    void should_fail_initializing_context_when_zip_archive_does_not_exist() {
        final Path archiveFile = temporaryDirectoryPath.resolve("archive");
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFile)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();

        assertThatThrownBy(() -> archiver.initializeContext(settings)).isInstanceOf(IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_fail_initializing_context_when_zip_archive_is_invalid() {
        final Path archiveFile = getResourcePath("invalid-archive.unknown");
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFile)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();

        assertThatThrownBy(() -> archiver.initializeContext(settings)).isInstanceOf(IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_fail_reading_symbolic_link_target() throws IOException {
        final Path archiveFile = getResourcePath("archive-linux.zip");
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFile)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();

        final IOException expectedException = new IOException();
        final ZipArchiver archiver = new ZipArchiverWithSymbolicLinkFailure(fileManager, expectedException);
        boolean failure = false;
        try (final ZipArchiverContext context = archiver.initializeContext(settings)) {
            Optional<ZipEntry> option = archiver.getNextEntry(context);
            while (option.isPresent()) {
                final ZipEntry entry = option.get();
                if (entry.isSymbolicLink()) {
                    assertThatThrownBy(() -> archiver.getSymbolicLinkTarget(context, entry)).isEqualTo(
                        expectedException);
                    failure = true;
                }
                option = archiver.getNextEntry(context);
            }
        }

        verifyNoMoreInteractions(fileManager);
        assertThat(failure).isTrue();
    }

    @Test
    void should_check_zip_archive_on_windows() throws IOException {
        final Path targetFilePath = temporaryDirectoryPath.resolve("aFile");
        String symbolicLinkTarget = null;
        final String archiveFileName;
        final int expectedCount;
        final Platform platform = LOCAL_PLATFORM;
        if (platform.isWindowsOs()) {
            archiveFileName = "archive-win.zip";
            expectedCount = 1;
        } else {
            archiveFileName = "archive-linux.zip";
            expectedCount = 2;
        }
        final Path archiveFilePath = getResourcePath(archiveFileName);
        when(fileManager.copy(any(InputStream.class), eq(targetFilePath))).thenCallRealMethod();
        final ExplodeCommand settings = ExplodeCommand
            .builder()
            .platform(platform)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();

        int count = 0;
        try (final ZipArchiverContext context = archiver.initializeContext(settings)) {
            Optional<ZipEntry> option = archiver.getNextEntry(context);
            ZipEntry entry;
            while (option.isPresent()) {
                entry = option.get();
                if (entry.isFile()) {
                    archiver.writeRegularFile(context, entry, targetFilePath);
                } else if (entry.isSymbolicLink()) {
                    symbolicLinkTarget = archiver.getSymbolicLinkTarget(context, entry);
                } else {
                    fail("Unexpected archive content.");
                }
                count++;
                option = archiver.getNextEntry(context);
            }
        }

        verifyNoMoreInteractions(fileManager);
        if (!platform.isWindowsOs()) {
            assertThat(symbolicLinkTarget).isEqualTo("./aFile");
        }
        assertThat(count).isEqualTo(expectedCount);
    }

    private static class ZipArchiverWithSymbolicLinkFailure extends ZipArchiver {

        private final IOException exception;

        ZipArchiverWithSymbolicLinkFailure(final FileManager fileManager, final IOException exception) {
            super(fileManager);
            this.exception = exception;
        }

        @Override
        String readSymbolicLinkTarget(final ZipFile zipFile, final ZipArchiveEntry entry) throws IOException {
            throw exception;
        }
    }
}
