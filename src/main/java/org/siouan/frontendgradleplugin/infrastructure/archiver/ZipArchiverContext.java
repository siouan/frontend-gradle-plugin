package org.siouan.frontendgradleplugin.infrastructure.archiver;

import java.io.IOException;
import java.util.Enumeration;

import lombok.Getter;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

/**
 * Context used to extract entries in a ZIP archive.
 *
 * @since 1.1.3
 */
@Getter
public class ZipArchiverContext implements ArchiverContext {

    /**
     * Parameters to explode archive content.
     */
    private final ExplodeCommand explodeCommand;

    /**
     * ZIP file.
     */
    private final ZipFile zipFile;

    /**
     * Enumeration of entries in the ZIP file.
     */
    private final Enumeration<ZipArchiveEntry> entries;

    /**
     * Builds a context providing explode settings, and archive entries.
     *
     * @param explodeCommand Parameters to explode archive content.
     * @param zipFile ZIP file.
     */
    public ZipArchiverContext(final ExplodeCommand explodeCommand, final ZipFile zipFile) {
        this.explodeCommand = explodeCommand;
        this.zipFile = zipFile;
        this.entries = this.zipFile.getEntries();
    }

    @Override
    public void close() throws IOException {
        zipFile.close();
    }
}
