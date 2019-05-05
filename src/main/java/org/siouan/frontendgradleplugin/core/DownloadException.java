package org.siouan.frontendgradleplugin.core;

/**
 * Exception thrown when the download of a resource failed.
 */
class DownloadException extends FrontendException {

    DownloadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
