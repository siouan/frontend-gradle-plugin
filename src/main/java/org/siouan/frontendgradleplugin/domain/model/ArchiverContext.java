package org.siouan.frontendgradleplugin.domain.model;

import java.io.IOException;
import javax.annotation.Nonnull;

/**
 * Interface of a context used internally by an archiver to provide information during entries extraction.
 *
 * @since 1.1.3
 */
public interface ArchiverContext extends AutoCloseable {

    /**
     * Gets the explode settings.
     *
     * @return Settings.
     */
    @Nonnull
    ExplodeSettings getSettings();

    /**
     * Closes this context. If this context is already closed, calling this method has no effect.
     */
    @Override
    void close() throws IOException;
}
