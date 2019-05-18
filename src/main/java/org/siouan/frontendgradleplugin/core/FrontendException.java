package org.siouan.frontendgradleplugin.core;

/**
 * Base exception class for checked exceptions thrown by the plugin.
 */
public abstract class FrontendException extends Exception {

    protected FrontendException() {
        super();
    }

    protected FrontendException(final String message) {
        super(message);
    }

    protected FrontendException(final Throwable cause) {
        super(cause);
    }

    protected FrontendException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
