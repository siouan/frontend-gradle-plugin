package org.siouan.frontendgradleplugin.core;

import java.net.URL;
import java.nio.file.Path;

/**
 * Interface of a component capable to validate a downloaded distribution.
 */
@FunctionalInterface
interface DistributionValidator {

    /**
     * Validates the distribution in the given file.
     *
     * @param distributionUrl URL used to download the distribution.
     * @param distributionFile Distribution file.
     * @throws DistributionValidatorException If the distribution is invalid.
     */
    void validate(URL distributionUrl, Path distributionFile) throws DistributionValidatorException;
}
