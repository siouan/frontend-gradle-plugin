package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.siouan.frontendgradleplugin.domain.exception.NodeDistributionChecksumNotFoundException;

/**
 * This reader allows to extract the checksum of a distribution file in a Node's file providing all checksums for a
 * given Node version.
 */
public class ReadNodeDistributionChecksum {

    /**
     * Reads the SHA-256 checksum of a distribution file, in the given checksum file.
     *
     * @param nodeDistributionChecksumFile File containing checksums of all Node distributions of a given version.
     * @param distributionFilename Name of the distribution file whose checksum shall be extracted.
     * @return The checksum.
     * @throws NodeDistributionChecksumNotFoundException If the checksum was not found, i.e. the distribution file name
     * is not present in the checksum file.
     * @throws IOException If the checksum file was not found or could not be read.
     */
    public String execute(final Path nodeDistributionChecksumFile, final String distributionFilename)
        throws NodeDistributionChecksumNotFoundException, IOException {
        final String trailingValue = "  " + distributionFilename;
        try (final BufferedReader reader = Files.newBufferedReader(nodeDistributionChecksumFile)) {
            final String checksumLine = reader
                .lines()
                .filter(line -> line.endsWith(trailingValue))
                .findAny()
                .orElseThrow(NodeDistributionChecksumNotFoundException::new);
            return checksumLine.substring(0, checksumLine.indexOf(trailingValue));
        }
    }
}
