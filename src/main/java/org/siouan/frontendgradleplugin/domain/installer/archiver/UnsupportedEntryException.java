package org.siouan.frontendgradleplugin.domain.installer.archiver;

/**
 * Exception thrown when an archive contains an entry that cannot be extracted by an archiver, because it is neither a
 * directory, nor a file, nor a symbolic link.
 *
 * @since 1.1.3
 */
public class UnsupportedEntryException extends ArchiverException {

    /**
     * Builds an exception for the given entry.
     *
     * @param entryName Entry name.
     */
    public UnsupportedEntryException(final String entryName) {
        super("Unsupported entry: " + entryName);
    }
}
