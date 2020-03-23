package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link DownloaderImpl} class.
 */
class DownloaderImplTest {

    private static final String RESOURCE_NAME = "resource.zip";

    @TempDir
    Path temporaryDirectory;

    @Test
    void shouldFailWhenResourceNotFound() throws IOException {
        final Path resourceFile = temporaryDirectory.resolve(RESOURCE_NAME);
        final Path downloadDirectory = temporaryDirectory.resolve("download");
        Files.createDirectory(downloadDirectory);
        final DownloaderImpl downloader = new DownloaderImpl(downloadDirectory);

        assertThatThrownBy(() -> downloader.download(resourceFile.toUri().toURL(), null))
            .isInstanceOf(DownloadException.class).hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldFailWhenDestinationFileCannotBeCreated() throws Exception {
        final Path resourceFile = temporaryDirectory.resolve(RESOURCE_NAME);
        final Path downloadDirectory = temporaryDirectory.resolve("download");
        final Path destinationFile = Paths.get("/volezp", "gixkkle");
        Files.createDirectory(downloadDirectory);
        Files.createFile(resourceFile);
        final DownloaderImpl downloader = new DownloaderImpl(downloadDirectory);

        assertThatThrownBy(() -> downloader.download(resourceFile.toUri().toURL(), destinationFile))
            .isInstanceOf(DownloadException.class).hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldDownloadLocalResource() throws Exception {
        final Path resourceFile = temporaryDirectory.resolve(RESOURCE_NAME);
        final Path downloadDirectory = temporaryDirectory.resolve("download");
        final Path destinationDirectory = temporaryDirectory.resolve("install");
        final Path destinationFile = destinationDirectory.resolve(RESOURCE_NAME);
        Files.createDirectory(downloadDirectory);
        Files.createDirectory(destinationDirectory);
        Files.createFile(resourceFile);
        final DownloaderImpl downloader = new DownloaderImpl(downloadDirectory);

        downloader.download(resourceFile.toUri().toURL(), destinationFile);

        assertThat(Files.isRegularFile(destinationFile)).isTrue();
    }
}
