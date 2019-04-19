package org.siouan.frontendgradleplugin.node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This reader allows to extract the checksum of a distribution file in a Node's file providing all checksums for a
 * given Node version.
 */
class NodeDistributionChecksumReaderImpl implements NodeDistributionChecksumReader {

    public String readHash(final File nodeDistributionChecksumFile, final String distributionFilename)
        throws NodeDistributionChecksumNotFoundException, IOException {
        final String trailingValue = "  " + distributionFilename;
        try (final BufferedReader reader = new BufferedReader(new FileReader(nodeDistributionChecksumFile))) {
            final String checksumLine = reader.lines().filter(line -> line.endsWith(trailingValue)).findFirst()
                .orElseThrow(NodeDistributionChecksumNotFoundException::new);
            return checksumLine.substring(0, checksumLine.indexOf(trailingValue));
        }
    }
}
