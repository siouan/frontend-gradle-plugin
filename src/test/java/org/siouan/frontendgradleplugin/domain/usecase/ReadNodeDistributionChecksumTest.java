package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.NodeDistributionChecksumNotFoundException;

@ExtendWith(MockitoExtension.class)
class ReadNodeDistributionChecksumTest {

    private static final String CHECKSUM_FILENAME = "checksums.txt";

    private static final String DISTRIBUTION_FILENAME = "node-10.0.0.zip";

    @TempDir
    Path temporaryDirectoryPath;

    @Test
    void shouldFailWhenChecksumFileNotReadable() {
        assertThatThrownBy(() -> new ReadNodeDistributionChecksum().execute(Paths.get("dummy-filename"),
            DISTRIBUTION_FILENAME)).isInstanceOf(IOException.class);
    }

    @Test
    void shouldFailWhenChecksumNotFound() throws IOException {
        final Path checksumFile = temporaryDirectoryPath.resolve(CHECKSUM_FILENAME);
        Files.createFile(checksumFile);
        assertThatThrownBy(
            () -> new ReadNodeDistributionChecksum().execute(checksumFile, DISTRIBUTION_FILENAME)).isInstanceOf(
            NodeDistributionChecksumNotFoundException.class);
    }

    @Test
    void shouldReturnChecksumWhenAtEndOfFileWithoutNewLine()
        throws IOException, NodeDistributionChecksumNotFoundException {
        final String checksum = "523ab86h853e86";
        final Path checksumFile = temporaryDirectoryPath.resolve(CHECKSUM_FILENAME);
        try (final BufferedWriter writer = Files.newBufferedWriter(checksumFile)) {
            writer.append(checksum);
            writer.append("  ");
            writer.append(DISTRIBUTION_FILENAME);
        }

        final String checksumRead = new ReadNodeDistributionChecksum().execute(checksumFile, DISTRIBUTION_FILENAME);
        assertThat(checksumRead).isEqualTo(checksum);
    }

    @Test
    void shouldReturnChecksumWhenAtEndOfFileWithNewLine()
        throws IOException, NodeDistributionChecksumNotFoundException {
        final String checksum = "523ab86h853e86";
        final Path checksumFile = temporaryDirectoryPath.resolve(CHECKSUM_FILENAME);
        try (final BufferedWriter writer = Files.newBufferedWriter(checksumFile)) {
            writer.append(checksum);
            writer.append("  ");
            writer.append(DISTRIBUTION_FILENAME);
            writer.append(System.lineSeparator());
        }

        final String checksumRead = new ReadNodeDistributionChecksum().execute(checksumFile, DISTRIBUTION_FILENAME);
        assertThat(checksumRead).isEqualTo(checksum);
    }
}
