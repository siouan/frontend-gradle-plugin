package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Base class for distribution validator exceptions.
 *
 * @since 2.0.0
 */
public abstract class DistributionValidatorException extends FrontendException {

    public DistributionValidatorException(@Nonnull final String message) {
        super(message);
    }
}
