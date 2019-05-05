package org.siouan.frontendgradleplugin.core.archivers;

import org.siouan.frontendgradleplugin.core.FrontendException;

/**
 * Exception thrown when an archive contains an entry that cannot be extracted by an archiver.
 *
 * @since 1.1.3
 */
public class UnsupportedEntryException extends FrontendException {

    /**
     * Builds an exception for the given entry.
     *
     * @param entryName Entry name.
     */
    public UnsupportedEntryException(final String entryName) {
        super("Unsupported entry: " + entryName);
    }
}
