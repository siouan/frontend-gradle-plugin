package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Base class for checked exceptions thrown by the plugin.
 */
public abstract class FrontendException extends Exception {

    protected FrontendException(@Nonnull final String message) {
        super(message);
    }
}
