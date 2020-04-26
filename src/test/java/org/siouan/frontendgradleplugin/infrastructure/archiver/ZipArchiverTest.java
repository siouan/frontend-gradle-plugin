package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.infrastructure.provider.FileManagerImpl;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

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
    void shouldFailInitializingContextWhenZipArchiveDoesNotExist() {
        final Path archiveFile = temporaryDirectoryPath.resolve("archive");
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFile,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> archiver.initializeContext(settings)).isInstanceOf(IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldFailInitializingContextWhenZipArchiveIsInvalid() throws URISyntaxException {
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource("invalid-archive.unknown").toURI());
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFile,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> archiver.initializeContext(settings)).isInstanceOf(IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldFailReadingSymbolicLinkTarget() throws URISyntaxException, IOException {
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource("archive-linux.zip").toURI());
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFile,
            temporaryDirectoryPath);

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
    void shouldCheckZipArchiveOnWindows() throws URISyntaxException, IOException {
        final Path targetFilePath = temporaryDirectoryPath.resolve("aFile");
        String symbolicLinkTarget = null;
        final String archiveFileName;
        final int expectedCount;
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        if (platform.isWindowsOs()) {
            archiveFileName = "archive-win.zip";
            expectedCount = 1;
        } else {
            archiveFileName = "archive-linux.zip";
            expectedCount = 2;
        }
        final Path archiveFilePath = Paths.get(getClass().getClassLoader().getResource(archiveFileName).toURI());
        when(fileManager.copy(any(InputStream.class), eq(targetFilePath))).thenCallRealMethod();
        final ExplodeSettings settings = new ExplodeSettings(platform, archiveFilePath, temporaryDirectoryPath);

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
