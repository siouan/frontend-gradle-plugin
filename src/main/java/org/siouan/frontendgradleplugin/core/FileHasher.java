package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Component capable of hashing a file.
 */
@FunctionalInterface
interface FileHasher {

    /**
     * Computes the hash of the file if not already done.
     *
     * @param inputFile The file to be hashed.
     * @return The hash as an hexadecimal string.
     * @throws IOException If the input file is not readable.
     */
    String hash(final Path inputFile) throws IOException;
}
