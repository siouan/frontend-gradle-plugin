package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Wrapper class for exceptions thrown by a HTTP client.
 *
 * @since 4.0.1
 */
public class HttpClientException extends FrontendException {

    /**
     * Builds an exception with the given cause.
     *
     * @param cause Cause.
     */
    public HttpClientException(@Nonnull final Throwable cause) {
        super(cause);
    }
}
