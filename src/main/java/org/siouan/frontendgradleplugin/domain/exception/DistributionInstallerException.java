package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Exception thrown when a distribution installer fails.
 *
 * @since 1.1.3
 */
public class DistributionInstallerException extends FrontendException {

    public DistributionInstallerException(@Nonnull final Throwable cause) {
        super(cause);
    }
}
