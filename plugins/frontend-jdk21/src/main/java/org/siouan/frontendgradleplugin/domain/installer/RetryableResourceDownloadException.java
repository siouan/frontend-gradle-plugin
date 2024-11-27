package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;

/**
 * Exception used to notify an attempt to download a resource failed for a reason that may trigger a retry.
 *
 * @since 7.1.0.
 */
class RetryableResourceDownloadException extends ResourceDownloadException {

    public RetryableResourceDownloadException(final String message) {
        super(message);
    }

    /**
     * Wraps an I/O error, generally a connectivity issue.
     *
     * @param e I/O error.
     */
    public RetryableResourceDownloadException(final IOException e) {
        super(e);
    }
}
