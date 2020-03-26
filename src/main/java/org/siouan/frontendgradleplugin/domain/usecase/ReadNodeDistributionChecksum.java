package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * This reader allows to extract the checksum of a distribution file in a Node's file providing all checksums for a
 * given Node version.
 */
public class ReadNodeDistributionChecksum {

    private final FileManager fileManager;

    public ReadNodeDistributionChecksum(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Reads the SHA-256 checksum of a distribution file, in the given checksum file.
     *
     * @param nodeDistributionChecksumFilePath File containing checksums of all Node distributions of a given version.
     * @param distributionFilename Name of the distribution file whose checksum shall be extracted.
     * @return The checksum.
     * @throws IOException If the checksum file was not found or could not be read.
     */
    public Optional<String> execute(@Nonnull final Path nodeDistributionChecksumFilePath,
        @Nonnull final String distributionFilename) throws IOException {
        final String trailingValue = "  " + distributionFilename;
        try (final BufferedReader reader = fileManager.newBufferedReader(nodeDistributionChecksumFilePath)) {
            return reader
                .lines()
                .filter(line -> line.endsWith(trailingValue))
                .findAny()
                .map(line -> line.substring(0, line.indexOf(trailingValue)));
        }
    }
}
