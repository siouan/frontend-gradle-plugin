package org.siouan.frontendgradleplugin.core.archivers;

import org.siouan.frontendgradleplugin.core.FrontendException;

/**
 * Exception thrown when an archiver fails.
 *
 * @since 1.1.3
 */
public class ArchiverException extends FrontendException {

    /**
     * Builds an exception with the given message.
     *
     * @param message Message.
     */
    public ArchiverException(final String message) {
        super(message);
    }

    /**
     * Builds an exception with the given cause.
     *
     * @param cause Cause.
     */
    public ArchiverException(final Throwable cause) {
        super(cause);
    }
}
