package org.siouan.frontendgradleplugin.core.archivers;

import java.util.Optional;

/**
 * Interface of a component capable to build instances of archiver.
 *
 * @since 1.1.3
 */
public interface ArchiverFactory {

    /**
     * Gets an archiver capable to process the given file extension.
     *
     * @param extension File extension.
     * @return Archiver, or {@code null} if this type of archive is not supported.
     */
    Optional<Archiver> get(String extension);
}
