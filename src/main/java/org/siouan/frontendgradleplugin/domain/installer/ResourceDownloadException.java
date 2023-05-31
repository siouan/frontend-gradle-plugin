package org.siouan.frontendgradleplugin.domain.installer;

import org.siouan.frontendgradleplugin.domain.FrontendException;

/**
 * Exception thrown when a resource download does not complete with a HTTP 200 response.
 *
 * @since 4.0.1
 */
public class ResourceDownloadException extends FrontendException {

    public ResourceDownloadException(final String message) {
        super(message);
    }
}
