package org.siouan.frontendgradleplugin.domain.provider;

import java.util.Optional;

import org.siouan.frontendgradleplugin.domain.model.Archiver;

/**
 * Provider of archivers.
 *
 * @since 1.4.2
 */
public interface ArchiverProvider {

    /**
     * Gets an archiver capable to process the given file extension.
     *
     * @param extension File extension.
     * @return Archiver.
     */
    Optional<Archiver> findByFilenameExtension(String extension);
}
