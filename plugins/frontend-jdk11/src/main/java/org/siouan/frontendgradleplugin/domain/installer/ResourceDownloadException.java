package org.siouan.frontendgradleplugin.domain.installer;

import org.siouan.frontendgradleplugin.domain.FrontendException;

/**
 * Exception thrown when a resource download definitely failed despite eventual multiple attempts.
 *
 * @since 4.0.1
 */
public class ResourceDownloadException extends FrontendException {

    public ResourceDownloadException(final String message) {
        super(message);
    }

    public ResourceDownloadException(final Throwable e) {
        super(e);
    }
}
