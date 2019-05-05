package org.siouan.frontendgradleplugin.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This reader allows to extract the checksum of a distribution file in a Node's file providing all checksums for a
 * given Node version.
 */
public class NodeDistributionChecksumReaderImpl implements NodeDistributionChecksumReader {

    @Override
    public String readHash(final Path nodeDistributionChecksumFile, final String distributionFilename)
        throws NodeDistributionChecksumNotFoundException, IOException {
        final String trailingValue = "  " + distributionFilename;
        try (final BufferedReader reader = Files.newBufferedReader(nodeDistributionChecksumFile)) {
            final String checksumLine = reader.lines().filter(line -> line.endsWith(trailingValue)).findFirst()
                .orElseThrow(NodeDistributionChecksumNotFoundException::new);
            return checksumLine.substring(0, checksumLine.indexOf(trailingValue));
        }
    }
}
