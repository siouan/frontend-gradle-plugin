package org.siouan.frontendgradleplugin.infrastructure.exception;

import java.io.IOException;
import javax.annotation.Nonnull;

/**
 * Exception thrown when an EOF was unexpectedly reached when reading an entry in an archive, i.e. when the archiver
 * expects to read more bytes for the entry.
 *
 * @since 2.0.0
 */
public class UnexpectedEofException extends IOException {

    /**
     * Builds an exception with the given entry name.
     *
     * @param entryName Entry name.
     * @param entrySize Entry size.
     * @param bytesRead Number of bytes read when the EOF was encountered.
     */
    public UnexpectedEofException(@Nonnull final String entryName, final long entrySize, final int bytesRead) {
        super("Unexpected EOF when reading entry '" + entryName + "': " + bytesRead + " bytes read / " + entrySize
            + " total bytes");
    }
}
