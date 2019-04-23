package org.siouan.frontendgradleplugin.core;

/**
 * Base exception class for all custom exceptions thrown by the plugin.
 */
abstract class FrontendException extends Exception {

    FrontendException() {
        super();
    }

    FrontendException(final String message) {
        super(message);
    }

    FrontendException(final Throwable cause) {
        super(cause);
    }

    FrontendException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
