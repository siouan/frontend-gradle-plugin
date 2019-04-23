package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link NodeDistributionChecksumReaderImpl} class.
 */
class NodeDistributionChecksumReaderImplTest {

    private static final String CHECKSUM_FILENAME = "checksums.txt";

    private static final String DISTRIBUTION_FILENAME = "node-10.0.0.zip";

    @TempDir
    protected File temporaryDirectory;

    @Test
    public void shouldFailWhenChecksumFileNotReadable() {
        assertThatThrownBy(
            () -> new NodeDistributionChecksumReaderImpl().readHash(new File("dummy-filename"), DISTRIBUTION_FILENAME))
            .isInstanceOf(IOException.class);
    }

    @Test
    public void shouldFailWhenChecksumNotFound() throws IOException {
        final File checksumFile = new File(temporaryDirectory, CHECKSUM_FILENAME);
        Files.createFile(checksumFile.toPath());
        assertThatThrownBy(() -> new NodeDistributionChecksumReaderImpl().readHash(checksumFile, DISTRIBUTION_FILENAME))
            .isInstanceOf(NodeDistributionChecksumNotFoundException.class);
    }

    @Test
    public void shouldReturnChecksumWhenAtEndOfFileWithoutNewLine()
        throws IOException, NodeDistributionChecksumNotFoundException {
        final String checksum = "523ab86h853e86";
        final File checksumFile = new File(temporaryDirectory, CHECKSUM_FILENAME);
        try (final FileWriter writer = new FileWriter(checksumFile)) {
            writer.append(checksum);
            writer.append("  ");
            writer.append(DISTRIBUTION_FILENAME);
        }

        final String checksumRead = new NodeDistributionChecksumReaderImpl()
            .readHash(checksumFile, DISTRIBUTION_FILENAME);
        assertThat(checksumRead).isEqualTo(checksum);
    }

    @Test
    public void shouldReturnChecksumWhenAtEndOfFileWithNewLine()
        throws IOException, NodeDistributionChecksumNotFoundException {
        final String checksum = "523ab86h853e86";
        final File checksumFile = new File(temporaryDirectory, CHECKSUM_FILENAME);
        try (final FileWriter writer = new FileWriter(checksumFile)) {
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
