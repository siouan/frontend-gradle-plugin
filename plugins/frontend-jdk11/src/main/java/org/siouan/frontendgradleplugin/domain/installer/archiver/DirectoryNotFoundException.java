package org.siouan.frontendgradleplugin.domain.installer.archiver;

import java.nio.file.Path;

/**
 * Exception thrown by an archiver when the directory where the archive shall be exploded does not exist.
 *
 * @since 1.1.3
 */
public class DirectoryNotFoundException extends ArchiverException {

    /**
     * Builds an exception with the given path.
     *
     * @param directoryPath Path to the directory.
     */
    public DirectoryNotFoundException(final Path directoryPath) {
        super("Target path is not a directory: " + directoryPath);
    }
}
