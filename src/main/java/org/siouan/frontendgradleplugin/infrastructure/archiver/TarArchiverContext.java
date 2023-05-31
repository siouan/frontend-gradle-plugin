package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.IOException;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

/**
 * Context used to extract entries in a TAR archive.
 *
 * @since 1.1.3
 */
@Builder
@Getter
class TarArchiverContext implements ArchiverContext {

    /**
     * Explode settings.
     */
    private final ExplodeCommand explodeCommand;

    /**
     * TAR archive input stream.
     */
    private final TarArchiveInputStream inputStream;

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
