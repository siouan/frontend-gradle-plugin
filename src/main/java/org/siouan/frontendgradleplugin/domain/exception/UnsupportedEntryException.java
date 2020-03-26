package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

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
    public UnsupportedEntryException(@Nonnull final String entryName) {
        super("Unsupported entry: " + entryName);
    }
}
