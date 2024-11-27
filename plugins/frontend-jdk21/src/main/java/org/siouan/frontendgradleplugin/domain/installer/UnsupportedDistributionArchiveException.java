package org.siouan.frontendgradleplugin.domain.installer;

import java.nio.file.Path;

import org.siouan.frontendgradleplugin.domain.FrontendException;

/**
 * Exception thrown when the type of a downloaded distribution archive is not supported.
 */
public class UnsupportedDistributionArchiveException extends FrontendException {

    public UnsupportedDistributionArchiveException(final Path distributionFilePath) {
        super("Unsupported type of distribution: " + distributionFilePath);
    }
}
