package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.URISyntaxException;

import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorProperties;

/**
 * Interface of a component capable to validate a distribution.
 */
@FunctionalInterface
public interface DistributionValidator {

    /**
     * Validates a downloaded distribution.
     *
     * @param distributionValidatorProperties Properties to validate the distribution.
     * @throws DistributionValidatorException If the distribution is invalid.
     */
    void validate(DistributionValidatorProperties distributionValidatorProperties)
        throws DistributionValidatorException, URISyntaxException;
}
