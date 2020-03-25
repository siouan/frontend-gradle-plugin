package org.siouan.frontendgradleplugin.domain.util;

import java.nio.file.Path;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * This class provides utilities for path management.
 *
 * @since 1.4.2
 */
public final class PathUtils {

    private PathUtils() {
    }

    /**
     * Gets the extension of a file.
     *
     * @param filePath File path.
     * @return Extension.
     */
    @Nonnull
    public static Optional<String> getExtension(@Nonnull final Path filePath) {
        return getExtension(filePath.getFileName().toString());
    }

    /**
     * Gets the extension of a file.
     *
     * @param fileName File name.
     * @return Extension.
     */
    @Nonnull
    public static Optional<String> getExtension(@Nonnull final String fileName) {
        final int index = fileName.lastIndexOf('.');
        final String extension;
        if (index == -1) {
            extension = null;
        } else {
            extension = fileName.substring(fileName.lastIndexOf('.'));
        }
        return Optional.ofNullable(extension);
    }

    /**
     * Removes the extension of a filename. In case of a compressed TAR archive, the method removes the whole extension
     * (e.g. '.tar.gz').
     *
     * @param filePath File path..
     * @return The filename without the extension.
     */
    @Nonnull
    public static String removeExtension(@Nonnull final Path filePath) {
        final String fileName = filePath.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    /**
     * Tells whether a file name extension is related to a GZIP file.
     *
     * @param extension Extension
     * @return {@code true} if the extension is related to a GZIP file.
     */
    public static boolean isGzipExtension(@Nonnull final String extension) {
        return extension.equals(".gz") || extension.equals(".gzip");
    }
}
