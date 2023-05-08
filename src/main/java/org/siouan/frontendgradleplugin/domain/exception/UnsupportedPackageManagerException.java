package org.siouan.frontendgradleplugin.domain.exception;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Exception thrown when a metadata file {@code package.json} refers to an unsupported package manager.
 *
 * @since 7.0.0
 */
public class UnsupportedPackageManagerException extends FrontendException {

    public UnsupportedPackageManagerException(@Nonnull final Path metadataFilePath, @Nullable final String packageManagerName) {
        super("Invalid metadata file '" + metadataFilePath
            + "', missing or malformed 'packageManager' property, or unsupported package manager '" + packageManagerName
            + '\'');
    }
}
