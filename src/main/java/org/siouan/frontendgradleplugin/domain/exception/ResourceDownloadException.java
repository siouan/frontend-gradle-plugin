package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Exception thrown when a resource download does not complete with a HTTP 200 response.
 *
 * @since 4.0.1
 */
public class ResourceDownloadException extends FrontendException {

    public ResourceDownloadException(@Nonnull final String message) {
        super(message);
    }
}
