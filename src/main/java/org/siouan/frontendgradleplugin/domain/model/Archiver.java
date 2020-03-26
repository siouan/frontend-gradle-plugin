package org.siouan.frontendgradleplugin.domain.model;

import java.io.IOException;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;

/**
 * Interface of a component capable to process archives.
 *
 * @since 1.1.3
 */
public interface Archiver {

    /**
     * Explodes an archive using the given settings.
     *
     * @param settings Explode settings.
     * @throws ArchiverException If extraction fails.
     * @throws IOException If an I/O error occurs.
     */
    void explode(@Nonnull ExplodeSettings settings) throws ArchiverException, IOException;
}
