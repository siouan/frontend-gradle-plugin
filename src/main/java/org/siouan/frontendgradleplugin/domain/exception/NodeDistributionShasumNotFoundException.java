package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Exception thrown when the shasum of a Node distribution could not be found in the file providing shasums for all
 * packages of a given release.
 */
public class NodeDistributionShasumNotFoundException extends DistributionValidatorException {

    public NodeDistributionShasumNotFoundException(@Nonnull final String distributionFileName) {
        super("Distribution shasum not found: '" + distributionFileName + '\'');
    }
}
