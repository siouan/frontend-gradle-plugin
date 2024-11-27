package org.siouan.frontendgradleplugin.domain.installer.archiver;

import java.io.IOException;

/**
 * Interface of a component capable to process archives.
 *
 * @since 1.1.3
 */
public interface Archiver {

    /**
     * Explodes an archive using the given settings.
     *
     * @param command Parameters to explode archive content.
     * @throws ArchiverException If extraction fails.
     * @throws IOException If an I/O error occurs.
     */
    void explode(ExplodeCommand command) throws ArchiverException, IOException;
}
