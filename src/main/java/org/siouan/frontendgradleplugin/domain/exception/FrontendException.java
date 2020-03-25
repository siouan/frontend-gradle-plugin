package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Base exception class for checked exceptions thrown by the plugin.
 */
public abstract class FrontendException extends Exception {

    protected FrontendException(@Nonnull final String message) {
        super(message);
    }

    protected FrontendException(@Nonnull final Throwable cause) {
        super(cause);
    }

    protected FrontendException(@Nonnull final String message, final Throwable cause) {
        super(message, cause);
    }
}
