package org.siouan.frontendgradleplugin.domain.usecase;

import javax.annotation.Nonnull;

/**
 * Generates a temporary file name
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
    @Nonnull
    public String execute(@Nonnull final String fileName) {
        return fileName + TMP_EXTENSION;
    }
}
