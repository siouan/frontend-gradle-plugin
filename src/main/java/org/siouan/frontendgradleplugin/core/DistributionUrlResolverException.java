package org.siouan.frontendgradleplugin.core;

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
