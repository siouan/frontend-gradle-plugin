package org.siouan.frontendgradleplugin.domain.installer.archiver;

/**
 * Interface of an entry in an archive, to be processed by an archiver.
 *
 * @since 1.1.3
 */
public interface ArchiveEntry {

    /**
     * Gets the entry name.
     *
     * @return Entry name.
     */
    String getName();

    /**
     * Whether this entry is a symbolic link.
     *
     * @return {@code true} if the entry is a symbolic link.
     */
    boolean isSymbolicLink();

    /**
     * Whether this entry is a directory.
     *
     * @return {@code true} if the entry is a directory.
     */
    boolean isDirectory();

    /**
     * Whether this entry is a regular file
     *
     * @return {@code true} if the entry is a file.
     */
    boolean isFile();

    /**
     * Gets the Unix permissions of this entry.
     *
     * @return Permissions.
     */
    int getUnixMode();
}
