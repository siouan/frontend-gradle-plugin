package org.siouan.frontendgradleplugin.domain.exception;

/**
 * Exception thrown when a downloaded Node distribution is corrupted.
 *
 * @since 2.0.0
 */
public class InvalidNodeDistributionException extends DistributionValidatorException {

    public InvalidNodeDistributionException() {
        super("Distribution file corrupted: invalid shasum");
    }
}
