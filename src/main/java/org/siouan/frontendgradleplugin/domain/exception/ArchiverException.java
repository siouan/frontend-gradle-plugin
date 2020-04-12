package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Base class for exceptions thrown by an archiver when it detects an error.
 *
 * @since 1.1.3
 */
public abstract class ArchiverException extends FrontendException {

    /**
     * Builds an exception with the given message.
     *
     * @param message Message.
     */
    protected ArchiverException(@Nonnull final String message) {
        super(message);
    }
}
