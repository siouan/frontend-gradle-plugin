package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;
import org.siouan.frontendgradleplugin.core.Utils;

/**
 * Unit tests for the {@link ZipArchiver} class.
 *
 * @since 1.1.3
 */
class ZipArchiverTest {

    @TempDir
    File targetDirectory;

    @Test
    void shouldFailInitializingContextWhenZipArchiveDoesNotExist() {
        final Path archiveFile = targetDirectory.toPath().resolve("archive");
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        assertThatThrownBy(() -> new ZipArchiver().initializeContext(settings)).isInstanceOf(ArchiverException.class)
            .hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldFailGettingNextEntryWhenZipArchiveIsInvalid() throws URISyntaxException {
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource("invalid-archive.unknown").toURI());
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        assertThatThrownBy(() -> new ZipArchiver().initializeContext(settings)).isInstanceOf(ArchiverException.class)
            .hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldCheckZipArchiveOnWindows() throws ArchiverException, URISyntaxException, IOException {
        final Path targetFile = targetDirectory.toPath().resolve("aFile");
        String symbolicLinkTarget = null;
        final String archiveFileName;
        final int expectedCount;
        if (Utils.isWindowsOs(Utils.getSystemOsName())) {
            archiveFileName = "archive-win.zip";
            expectedCount = 1;
        } else {
            archiveFileName = "archive-linux.zip";
            expectedCount = 2;
        }
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource(archiveFileName).toURI());
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        ZipArchiverContext context = null;
        int count = 0;
        try {
            final ZipArchiver archiver = new ZipArchiver();
            context = archiver.initializeContext(settings);

            Optional<ZipEntry> option = archiver.getNextEntry(context);
            ZipEntry entry;
            while (option.isPresent()) {
                entry = option.get();
                if (entry.isFile()) {
                    archiver.writeRegularFile(context, entry, targetFile);
                } else {
                    if (entry.isSymbolicLink()) {
                        symbolicLinkTarget = archiver.getSymbolicLinkTarget(context, entry);
                    } else {
                        fail("Unexpected archive content.");
                    }
                }
                count++;
                option = archiver.getNextEntry(context);
            }
        } finally {
            if (context != null) {
                context.close();
            }
        }

        assertThat(targetFile).exists().hasContent("content");
        if (!Utils.isWindowsOs(Utils.getSystemOsName())) {
            assertThat(symbolicLinkTarget).isEqualTo("./aFile");
        }
        assertThat(count).isEqualTo(expectedCount);
    }
}
