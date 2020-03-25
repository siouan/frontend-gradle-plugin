package org.siouan.frontendgradleplugin.domain.model;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;

/**
 * Interface of a context used internally by an archiver to extract entries.
 *
 * @since 1.1.3
 */
public interface ArchiverContext {

    /**
     * Gets the explode settings.
     *
     * @return Settings.
     */
    ExplodeSettings getSettings();

    /**
     * Closes this context. If this context  is already closed, calling this method has no effect.
     *
     * @throws ArchiverException If closing this context fails.
     */
    void close() throws ArchiverException;
}
