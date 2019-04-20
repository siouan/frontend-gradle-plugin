package org.siouan.frontendgradleplugin.job;

import org.siouan.frontendgradleplugin.FrontendException;

/**
 * Exception thrown when the URL to download a distribution can not be resolved.
 */
public class DistributionUrlResolverException extends FrontendException {

    public DistributionUrlResolverException(final String message) {
        super(message);
    }

    public DistributionUrlResolverException(final Throwable cause) {
        super(cause);
    }
}
