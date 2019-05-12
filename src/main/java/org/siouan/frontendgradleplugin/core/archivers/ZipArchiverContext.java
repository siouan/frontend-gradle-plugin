package org.siouan.frontendgradleplugin.core.archivers;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;

/**
 * Context used to extract entries in a ZIP archive.
 *
 * @since 1.1.3
 */
class ZipArchiverContext implements ArchiverContext {

    /**
     * Explode settings.
     */
    private final ExplodeSettings settings;

    private final ZipFile zipFile;

    private final Enumeration<ZipArchiveEntry> entries;

    /**
     * Builds a context providing explode settings, and archive entries.
     *
     * @param settings Explode settings.
     * @param zipFile ZIP file.
     * @throws IOException If the archive file cannot be read or is not a valid ZIP file.
     */
    ZipArchiverContext(final ExplodeSettings settings, final ZipFile zipFile) throws IOException {
        this.settings = settings;
        this.zipFile = zipFile;
        this.entries = this.zipFile.getEntries();
    }

    /**
     * Gets the enumeration of entries in the archive.
     *
     * @return Enumeration of entries.
     */
    Enumeration<ZipArchiveEntry> getEntries() {
        return entries;
    }

    @Override
    public ExplodeSettings getSettings() {
        return settings;
    }

    /**
     * Gets the archive file.
     *
     * @return File.
     */
    ZipFile getZipFile() {
        return zipFile;
    }

    @Override
    public void close() throws ArchiverException {
        try {
            zipFile.close();
        } catch (final IOException e) {
            throw new ArchiverException(e);
        }
    }
}
