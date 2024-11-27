package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class provides utilities for path management.
 *
 * @since 2.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathUtils {

    private static final Set<String> GZIP_EXTENSIONS = Set.of(".gz", ".gzip");

    /**
     * Gets the extension of a file.
     *
     * @param filePath File path.
     * @return Extension.
     */
    public static Optional<String> getExtension(final Path filePath) {
        return Optional.ofNullable(filePath.getFileName()).map(Path::toString).flatMap(PathUtils::getExtension);
    }

    /**
     * Gets the extension of a file.
     *
     * @param fileName File name.
     * @return Extension.
     */
    public static Optional<String> getExtension(final String fileName) {
        final int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return Optional.empty();
        }

        return Optional.of(fileName.substring(fileName.lastIndexOf('.')));
    }

    /**
     * Removes the extension of a filename. In case of a compressed TAR archive, the method removes the whole extension
     * (e.g. '.tar.gz').
     *
     * @param filePath File path..
     * @return The filename without the extension.
     */
    public static String removeExtension(final Path filePath) {
        final Path leafFilePath = filePath.getFileName();
        if (leafFilePath == null) {
            return filePath.toString();
        }
        final String leafFilePathAsString = leafFilePath.toString();
        final int index = leafFilePathAsString.lastIndexOf('.');
        if (index == -1) {
            return leafFilePathAsString;
        }
        return leafFilePathAsString.substring(0, index);
    }

    /**
     * Tells whether a file name extension is related to a GZIP file. The extension is expected to start with a dot '.'
     * character.
     *
     * @param extension Extension
     * @return {@code true} if the extension is related to a GZIP file.
     */
    public static boolean isGzipExtension(final String extension) {
        return GZIP_EXTENSIONS.contains(extension);
    }
}
