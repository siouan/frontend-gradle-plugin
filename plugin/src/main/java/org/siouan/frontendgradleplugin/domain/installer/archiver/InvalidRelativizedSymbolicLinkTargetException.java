package org.siouan.frontendgradleplugin.domain.installer.archiver;

import java.nio.file.Path;

/**
 * Exception thrown when the relativized and normalized path of the target of a symbolic link does not point to the same
 * file after these 2 operations. For instance if one of the intermediate directory is a symbolic link (e.g. symbolic
 * link '/a' points to file '/e/f' and path '/a/../c/d' points to file '/e/c/d'), these operations may return a path
 * pointing to a different file (e.g. '/c/d'). Such case - though very unlikely to appear in an archive - would lead to
 * creating an incorrect symbolic link on the file system.
 *
 * @see Path#relativize(Path)
 * @see Path#normalize()
 * @since 2.0.0
 */
public class InvalidRelativizedSymbolicLinkTargetException extends ArchiverException {

    public InvalidRelativizedSymbolicLinkTargetException(final String entryName, final Path targetFilePath) {
        super("Target path for symbolic link entry '" + entryName + "' is not relativized consistently: "
            + targetFilePath);
    }
}
