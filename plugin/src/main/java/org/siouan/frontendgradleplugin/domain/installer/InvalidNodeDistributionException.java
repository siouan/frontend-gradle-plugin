package org.siouan.frontendgradleplugin.domain.installer;

import org.siouan.frontendgradleplugin.domain.FrontendException;

/**
 * Exception thrown when a downloaded Node distribution is corrupted.
 *
 * @since 2.0.0
 */
public class InvalidNodeDistributionException extends FrontendException {

    public InvalidNodeDistributionException() {
        super("Distribution file corrupted: invalid shasum");
    }
}
