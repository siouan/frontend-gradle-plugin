package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Exception thrown when a type of distribution is not supported.
 *
 * @since 2.0.0
 */
public class UnsupportedDistributionIdException extends FrontendException {

    public UnsupportedDistributionIdException(@Nonnull final String distributionId) {
        super("Unsupported distribution ID: " + distributionId);
    }
}
