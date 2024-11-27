package org.siouan.frontendgradleplugin.domain.installer.archiver;

/**
 * Test implementation of an archive entry.
 */
public class ArchiveEntryImpl implements ArchiveEntry {

    private final String name;

    private final ArchiveEntryType type;

    private final int unixMode;

    private final String content;

    ArchiveEntryImpl(final String name, final ArchiveEntryType type, final int unixMode, final String content) {
        this.name = name;
        this.type = type;
        this.unixMode = unixMode;
        this.content = content;
    }

    public static ArchiveEntryImpl newSymbolicLinkEntry(final String name, final String targetPath) {
        return new ArchiveEntryImpl(name, ArchiveEntryType.SYMBOLIC_LINK, 0, targetPath);
    }

    public static ArchiveEntryImpl newDirectoryEntry(final String name, final int unixMode) {
        return new ArchiveEntryImpl(name, ArchiveEntryType.DIRECTORY, unixMode, null);
    }

    public static ArchiveEntryImpl newFileEntry(final String name, final int unixMode, final String content) {
        return new ArchiveEntryImpl(name, ArchiveEntryType.FILE, unixMode, content);
    }

    public static ArchiveEntryImpl newUnknownEntry(final String name) {
        return new ArchiveEntryImpl(name, ArchiveEntryType.UNKNOWN, 0, null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSymbolicLink() {
        return type.equals(ArchiveEntryType.SYMBOLIC_LINK);
    }

    @Override
    public boolean isDirectory() {
        return type.equals(ArchiveEntryType.DIRECTORY);
    }

    @Override
    public boolean isFile() {
        return type.equals(ArchiveEntryType.FILE);
    }

    @Override
    public int getUnixMode() {
        return unixMode;
    }

    String getContent() {
        return content;
    }
}
