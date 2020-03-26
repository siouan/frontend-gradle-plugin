package org.siouan.frontendgradleplugin.domain.provider;

import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Archiver;

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
    @Nonnull
    Optional<Archiver> findByArchiveFilePath(@Nonnull Path archiveFilePath);
}
