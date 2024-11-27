package org.siouan.frontendgradleplugin.domain.installer.archiver;

import java.io.IOException;

/**
 * Interface of a context used internally by an archiver to provide information during entries extraction.
 *
 * @since 1.1.3
 */
public interface ArchiverContext extends AutoCloseable {

    /**
     * Gets the parameters to explode archive content.
     *
     * @return Settings.
     */
    ExplodeCommand getExplodeCommand();

    /**
     * Closes this context. If this context is already closed, calling this method has no effect.
     */
    @Override
    void close() throws IOException;
}
