package org.siouan.frontendgradleplugin.core;

/**
 * Exception thrown when a downloaded distribution is invalid.
 */
public class DistributionValidatorException extends FrontendException {

    public DistributionValidatorException(final String message) {
        super(message);
    }

    public DistributionValidatorException(final Throwable cause) {
        super(cause);
    }
}
