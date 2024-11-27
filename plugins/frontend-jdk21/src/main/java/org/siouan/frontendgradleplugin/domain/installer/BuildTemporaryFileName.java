package org.siouan.frontendgradleplugin.domain.installer;

/**
 * Generates a temporary file name.
 *
 * @since 4.0.1
 */
public class BuildTemporaryFileName {

    public static final String TMP_EXTENSION = ".tmp";

    /**
     * Generates a temporary file name based on the given name.
     *
     * @param fileName Regular file name.
     * @return Temporary file name.
     */
    public String execute(final String fileName) {
        return fileName + TMP_EXTENSION;
    }
}
