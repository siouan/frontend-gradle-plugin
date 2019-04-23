package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.io.IOException;

/**
 * Component capable of hashing a file.
 */
public interface FileHasher {

    /**
     * Computes the hash of the file if not already done.
     *
     * @param inputFile The file to be hashed.
     * @return The hash as an hexadecimal string.
     * @throws IOException If the input file is not readable.
     */
    String hash(final File inputFile) throws IOException;
}
