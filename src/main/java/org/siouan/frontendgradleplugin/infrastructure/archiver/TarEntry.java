package org.siouan.frontendgradleplugin.infrastructure.archiver;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiveEntry;

/**
 * This class acts as a bridge between this plugin's archive entry implementation, and Apache Commons Compress low-level
 * implementation of TAR entries. It allows to hide implementation details, and conforms to a uniform interface for this
 * plugin's archivers.
 *
 * @since 1.1.3
 */
@Builder
@Getter
class TarEntry implements ArchiveEntry {

    /**
     * Low-level entry mapped to this entry.
     */
    private final TarArchiveEntry lowLevelEntry;

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
