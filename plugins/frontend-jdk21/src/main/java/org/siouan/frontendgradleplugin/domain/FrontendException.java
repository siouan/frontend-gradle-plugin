package org.siouan.frontendgradleplugin.domain;

/**
 * Base class for checked exceptions thrown by the plugin.
 */
public abstract class FrontendException extends Exception {

    protected FrontendException(final String message) {
        super(message);
    }

    protected FrontendException(final Throwable throwable) {
        super(throwable);
    }
}
