package org.siouan.frontendgradleplugin;

/**
 * Exception thrown when the download of a resource failed.
 */
public class DownloadException extends FrontendException {

    public DownloadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
