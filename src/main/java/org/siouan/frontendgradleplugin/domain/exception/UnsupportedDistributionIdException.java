package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionId;

/**
 * Exception thrown when a type of distribution is not supported.
 *
 * @since 2.0.0
 */
public class UnsupportedDistributionIdException extends FrontendException {

    public UnsupportedDistributionIdException(@Nonnull final DistributionId distributionId) {
        super("Unsupported distribution ID: " + distributionId);
    }
}
