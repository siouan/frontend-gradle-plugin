package org.siouan.frontendgradleplugin.domain.exception;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Exception thrown by an archiver when the target directory does not exist.
 *
 * @since 1.1.3
 */
public class DirectoryNotFoundException extends ArchiverException {

    /**
     * Builds an exception with the given directory.
     *
     * @param directory Directory.
     */
    public DirectoryNotFoundException(@Nonnull final Path directory) {
        super("Target file is not a directory: " + directory);
    }
}
