package org.siouan.frontendgradleplugin.domain.installer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.FileManager;

/**
 * This reader allows to extract the shasum of a distribution file in a Node's file providing all shasums for a given
 * Node version.
 */
@RequiredArgsConstructor
public class ReadNodeDistributionShasum {

    private final FileManager fileManager;

    /**
     * Reads the shasum of a distribution file, in the given shasum file.
     *
     * @param command Command providing parameters.
     * @return The shasum.
     * @throws IOException If the shasum file was not found or could not be read.
     */
    public Optional<String> execute(final ReadNodeDistributionShasumCommand command) throws IOException {
        final String trailingValue = "  " + command.getDistributionFileName();
        try (final BufferedReader reader = fileManager.newBufferedReader(command.getNodeDistributionShasumFilePath())) {
            return reader
                .lines()
                .filter(line -> line.endsWith(trailingValue))
                .findAny()
                .map(line -> line.substring(0, line.indexOf(trailingValue)));
        }
    }
}
