package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A checksum reader allows to extract the checksum of a given distribution file, from a Node's file providing all
 * checksums for a given Node version.
 */
@FunctionalInterface
interface NodeDistributionChecksumReader {

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
    String readHash(final Path nodeDistributionChecksumFile, final String distributionFilename)
        throws NodeDistributionChecksumNotFoundException, IOException;
}
