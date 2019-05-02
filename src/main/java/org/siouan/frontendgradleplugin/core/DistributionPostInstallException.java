package org.siouan.frontendgradleplugin.core;

/**
 * Exception thrown when a distribution post-install action has failed.
 */
public class DistributionPostInstallException extends FrontendException {

    public DistributionPostInstallException(final Throwable cause) {
        super(cause);
    }
}
