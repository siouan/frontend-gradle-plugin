package org.siouan.frontendgradleplugin.core.archivers;

import java.nio.file.Path;

/**
 * Exception thrown when an archiver fails.the target directory does not exist.
 *
 * @since 1.1.3
 */
class DirectoryNotFoundException extends ArchiverException {

    /**
     * Builds an exception with the given directory.
     *
     * @param directory Directory.
     */
    DirectoryNotFoundException(final Path directory) {
        super("Target file is not a directory: " + directory);
    }
}
