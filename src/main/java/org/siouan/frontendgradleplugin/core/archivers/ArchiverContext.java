package org.siouan.frontendgradleplugin.core.archivers;

import org.siouan.frontendgradleplugin.core.ExplodeSettings;

/**
 * Interface of a context used internally by an archiver to extract entries.
 *
 * @since 1.1.3
 */
interface ArchiverContext {

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
