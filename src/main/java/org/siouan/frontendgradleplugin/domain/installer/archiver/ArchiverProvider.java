package org.siouan.frontendgradleplugin.domain.installer.archiver;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Provider of archivers.
 *
 * @since 2.0.0
 */
public interface ArchiverProvider {

    /**
     * Gets an archiver capable to process the given archive file.
     *
     * @param archiveFilePath Path to the archive file.
     * @return Archiver.
     */
    Optional<Archiver> findByArchiveFilePath(Path archiveFilePath);
}
