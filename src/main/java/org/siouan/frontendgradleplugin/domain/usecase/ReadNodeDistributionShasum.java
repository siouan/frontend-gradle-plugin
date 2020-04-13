package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * This reader allows to extract the shasum of a distribution file in a Node's file providing all shasums for a given
 * Node version.
 */
public class ReadNodeDistributionShasum {

    private final FileManager fileManager;

    public ReadNodeDistributionShasum(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Reads the shasum of a distribution file, in the given shasum file.
     *
     * @param nodeDistributionShasumFilePath File containing shasums of all Node distributions of a given version.
     * @param distributionFilename Name of the distribution file whose shasum shall be extracted.
     * @return The shasum.
     * @throws IOException If the shasum file was not found or could not be read.
     */
    public Optional<String> execute(@Nonnull final Path nodeDistributionShasumFilePath,
        @Nonnull final String distributionFilename) throws IOException {
        final String trailingValue = "  " + distributionFilename;
        try (final BufferedReader reader = fileManager.newBufferedReader(nodeDistributionShasumFilePath)) {
            return reader
                .lines()
                .filter(line -> line.endsWith(trailingValue))
                .findAny()
                .map(line -> line.substring(0, line.indexOf(trailingValue)));
        }
    }
}
