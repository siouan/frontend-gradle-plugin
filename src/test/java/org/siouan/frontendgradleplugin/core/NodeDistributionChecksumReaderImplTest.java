package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link NodeDistributionChecksumReaderImpl} class.
 */
class NodeDistributionChecksumReaderImplTest {

    private static final String CHECKSUM_FILENAME = "checksums.txt";

    private static final String DISTRIBUTION_FILENAME = "node-10.0.0.zip";

    @TempDir
    Path temporaryDirectory;

    @Test
    void shouldFailWhenChecksumFileNotReadable() {
        assertThatThrownBy(
            () -> new NodeDistributionChecksumReaderImpl().readHash(Paths.get("dummy-filename"), DISTRIBUTION_FILENAME))
            .isInstanceOf(IOException.class);
    }

    @Test
    void shouldFailWhenChecksumNotFound() throws IOException {
        final Path checksumFile = temporaryDirectory.resolve(CHECKSUM_FILENAME);
        Files.createFile(checksumFile);
        assertThatThrownBy(() -> new NodeDistributionChecksumReaderImpl().readHash(checksumFile, DISTRIBUTION_FILENAME))
            .isInstanceOf(NodeDistributionChecksumNotFoundException.class);
    }

    @Test
    void shouldReturnChecksumWhenAtEndOfFileWithoutNewLine()
        throws IOException, NodeDistributionChecksumNotFoundException {
        final String checksum = "523ab86h853e86";
        final Path checksumFile = temporaryDirectory.resolve(CHECKSUM_FILENAME);
        try (final BufferedWriter writer = Files.newBufferedWriter(checksumFile)) {
            writer.append(checksum);
            writer.append("  ");
            writer.append(DISTRIBUTION_FILENAME);
        }

        final String checksumRead = new NodeDistributionChecksumReaderImpl()
            .readHash(checksumFile, DISTRIBUTION_FILENAME);
        assertThat(checksumRead).isEqualTo(checksum);
    }

    @Test
    void shouldReturnChecksumWhenAtEndOfFileWithNewLine()
        throws IOException, NodeDistributionChecksumNotFoundException {
        final String checksum = "523ab86h853e86";
        final Path checksumFile = temporaryDirectory.resolve(CHECKSUM_FILENAME);
        try (final BufferedWriter writer = Files.newBufferedWriter(checksumFile)) {
            writer.append(checksum);
            writer.append("  ");
            writer.append(DISTRIBUTION_FILENAME);
            writer.append(System.lineSeparator());
        }

        final String checksumRead = new NodeDistributionChecksumReaderImpl()
            .readHash(checksumFile, DISTRIBUTION_FILENAME);
        assertThat(checksumRead).isEqualTo(checksum);
    }
}
