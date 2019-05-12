package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;
import org.siouan.frontendgradleplugin.core.Utils;

/**
 * Unit tests for the {@link TarArchiver} class.
 *
 * @since 1.1.3
 */
class TarArchiverTest {

    @TempDir
    File targetDirectory;

    @Test
    void shouldFailInitializingContextWhenTarArchiveDoesNotExist() {
        final Path archiveFile = targetDirectory.toPath().resolve("archive");
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        assertThatThrownBy(() -> new TarArchiver().initializeContext(settings)).isInstanceOf(ArchiverException.class)
            .hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldFailInitializingContextWhenTarArchiveIsInvalid() throws URISyntaxException, ArchiverException {
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource("invalid-archive.unknown").toURI());
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        final TarArchiver archiver = new TarArchiver();
        TarArchiverContext context = null;
        try {
            context = archiver.initializeContext(settings);
            final TarArchiverContext finalContext = context;
            assertThatThrownBy(() -> archiver.getNextEntry(finalContext)).isInstanceOf(ArchiverException.class)
                .hasCauseInstanceOf(IOException.class);
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

    @Test
    void shouldCheckTarGzArchive() throws ArchiverException, URISyntaxException, IOException {
        final Path targetFile = targetDirectory.toPath().resolve("aFile");
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource("archive.tar.gz").toURI());
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        TarArchiverContext context = null;
        int count = 0;
        try {
            final TarArchiver archiver = new TarArchiver();
            context = archiver.initializeContext(settings);

            Optional<TarEntry> option = archiver.getNextEntry(context);
            TarEntry entry;
            while (option.isPresent()) {
                entry = option.get();
                if (entry.isFile()) {
                    archiver.writeRegularFile(context, entry, targetFile);
                } else {
                    fail("Unexpected archive content.");
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
        assertThat(count).isEqualTo(1);
    }

    @Test
    void shouldCheckTarArchive() throws ArchiverException, URISyntaxException, IOException {
        final Path targetFile = targetDirectory.toPath().resolve("aFile");
        final Path archiveFile = Paths.get(getClass().getClassLoader().getResource("archive.tar").toURI());
        String symbolicLinkTarget = null;
        final ExplodeSettings settings = new ExplodeSettings(archiveFile, targetDirectory.toPath(),
            Utils.getSystemOsName());

        TarArchiverContext context = null;
        int count = 0;
        try {
            final TarArchiver archiver = new TarArchiver();
            context = archiver.initializeContext(settings);

            Optional<TarEntry> option = archiver.getNextEntry(context);
            TarEntry entry;
            while (option.isPresent()) {
                entry = option.get();
                if (entry.isSymbolicLink()) {
                    symbolicLinkTarget = archiver.getSymbolicLinkTarget(context, entry);
                } else if (entry.isFile()) {
                    archiver.writeRegularFile(context, entry, targetFile);
                } else {
                    fail("Unexpected archive content.");
                }
                count++;
                option = archiver.getNextEntry(context);
            }
        } finally {
            if (context != null) {
                context.close();
            }
        }

        assertThat(targetFile).exists().usingCharset(StandardCharsets.UTF_8).hasContent("content");
        assertThat(symbolicLinkTarget).isEqualTo("./aFile");
        assertThat(count).isEqualTo(2);
    }
}
