package org.siouan.frontendgradleplugin.domain.installer;

import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Command to read a Node.js distribution shasum in a file.
 *
 * @since 7.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReadNodeDistributionShasumCommand {

    /**
     * File containing shasums of all Node distributions of a given version.
     */
    @EqualsAndHashCode.Include
    private final Path nodeDistributionShasumFilePath;

    /**
     * Name of the distribution file whose shasum shall be extracted.
     */
    @EqualsAndHashCode.Include
    private final String distributionFileName;
}
