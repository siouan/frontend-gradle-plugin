package org.siouan.frontendgradleplugin.infrastructure.archiver;

import javax.annotation.Nonnull;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.siouan.frontendgradleplugin.domain.model.ArchiveEntry;

/**
 * This class acts as a bridge between this plugin's archive entry implementation, and Apache Commons Compress low-level
 * implementation of TAR entries. It allows to hide implementation details, and conforms to a uniform interface for this
 * plugin's archivers.
 *
 * @since 1.1.3
 */
class TarEntry implements ArchiveEntry {

    private final TarArchiveEntry lowLevelEntry;

    /**
     * Builds an entry mapped to a low-level entry.
     *
     * @param lowLevelEntry Low-level entry.
     */
    TarEntry(final TarArchiveEntry lowLevelEntry) {
        this.lowLevelEntry = lowLevelEntry;
    }

    /**
     * Gets the low-level entry this entry is mapped to.
     *
     * @return Entry.
     */
    TarArchiveEntry getLowLevelEntry() {
        return lowLevelEntry;
    }

    @Nonnull
    @Override
    public String getName() {
        return lowLevelEntry.getName();
    }

    @Override
    public boolean isSymbolicLink() {
        return lowLevelEntry.isSymbolicLink();
    }

    @Override
    public boolean isDirectory() {
        return lowLevelEntry.isDirectory();
    }

    @Override
    public boolean isFile() {
        return lowLevelEntry.isFile();
    }

    @Override
    public int getUnixMode() {
        return lowLevelEntry.getMode();
    }
}
