package org.siouan.frontendgradleplugin;

/**
 * Base exception class for all custom exceptions thrown by the plugin.
 */
public abstract class FrontendException extends Exception {

    public FrontendException() {
        super();
    }

    public FrontendException(final String message) {
        super(message);
    }

    public FrontendException(final Throwable cause) {
        super(cause);
    }

    public FrontendException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
