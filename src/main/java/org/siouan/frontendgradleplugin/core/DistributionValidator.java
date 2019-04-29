package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.net.URL;

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
     * @throws InvalidDistributionException If the distribution is invalid.
     */
    void validate(URL distributionUrl, File distributionFile) throws InvalidDistributionException;
}
