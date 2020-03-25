package org.siouan.frontendgradleplugin.infrastructure.archiver;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.siouan.frontendgradleplugin.domain.model.ArchiveEntry;

/**
 * This class acts as a bridge between this plugin's archive entry implementation, and Apache Commons Compress low-level
 * implementation of ZIP entries. It allows to hide implementation details, and conforms to a uniform interface for this
 * plugin's archivers.
 *
 * @since 1.1.3
 */
public class ZipEntry implements ArchiveEntry {

    private final ZipArchiveEntry lowLevelEntry;

    /**
     * Builds an entry mapped to a low-level entry.
     *
     * @param lowLevelEntry Low-level entry.
     */
    public ZipEntry(final ZipArchiveEntry lowLevelEntry) {
        this.lowLevelEntry = lowLevelEntry;
    }

    /**
     * Gets the low-level entry this entry is mapped to.
     *
     * @return Entry.
     */
    ZipArchiveEntry getLowLevelEntry() {
        return lowLevelEntry;
    }

    @Override
    public String getName() {
        return lowLevelEntry.getName();
    }

    @Override
    public boolean isSymbolicLink() {
        return lowLevelEntry.isUnixSymlink();
    }

    @Override
    public boolean isDirectory() {
        return lowLevelEntry.isDirectory();
    }

    @Override
    public boolean isFile() {
        return !lowLevelEntry.isDirectory() && !lowLevelEntry.isUnixSymlink();
    }

    @Override
    public int getUnixMode() {
        return lowLevelEntry.getUnixMode();
    }
}
