package org.siouan.frontendgradleplugin.domain.exception;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Exception thrown when the type of a downloaded distribution archive is not supported.
 */
public class UnsupportedDistributionArchiveException extends FrontendException {

    public UnsupportedDistributionArchiveException(@Nonnull final Path distributionFilePath) {
        super("Unsupported type of distribution: " + distributionFilePath);
    }
}
