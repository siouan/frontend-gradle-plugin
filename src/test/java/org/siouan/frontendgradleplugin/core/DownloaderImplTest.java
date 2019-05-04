package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link DownloaderImpl} class.
 */
public class DownloaderImplTest {

    private static final String RESOURCE_NAME = "resource.zip";

    @TempDir
    protected File temporaryDirectory;

    @Test
    void shouldFailWhenResourceNotFound() throws IOException {
        final File resourceFile = new File(temporaryDirectory, RESOURCE_NAME);
        final File downloadDirectory = new File(temporaryDirectory, "download");
        Files.createDirectory(downloadDirectory.toPath());
        final DownloaderImpl downloader = new DownloaderImpl(downloadDirectory);

        assertThatThrownBy(() -> downloader.download(resourceFile.toURI().toURL(), null))
            .isInstanceOf(DownloadException.class).hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldFailWhenDestinationFileCannotBeCreated() throws Exception {
        final File resourceFile = new File(temporaryDirectory, RESOURCE_NAME);
        final File downloadDirectory = new File(temporaryDirectory, "download");
        final File destinationFile = new File("/volezp/gixkkle");
        Files.createDirectory(downloadDirectory.toPath());
        Files.createFile(resourceFile.toPath());
        final DownloaderImpl downloader = new DownloaderImpl(downloadDirectory);

        assertThatThrownBy(() -> downloader.download(resourceFile.toURI().toURL(), destinationFile))
            .isInstanceOf(DownloadException.class).hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldDownloadLocalResource() throws Exception {
        final File resourceFile = new File(temporaryDirectory, RESOURCE_NAME);
        final File downloadDirectory = new File(temporaryDirectory, "download");
        final File destinationDirectory = new File(temporaryDirectory, "install");
        final File destinationFile = new File(destinationDirectory, RESOURCE_NAME);
        Files.createDirectory(downloadDirectory.toPath());
        Files.createDirectory(destinationDirectory.toPath());
        Files.createFile(resourceFile.toPath());
        final DownloaderImpl downloader = new DownloaderImpl(downloadDirectory);

        downloader.download(resourceFile.toURI().toURL(), destinationFile);

        assertThat(new File(destinationDirectory, RESOURCE_NAME).isFile()).isTrue();
    }
}
