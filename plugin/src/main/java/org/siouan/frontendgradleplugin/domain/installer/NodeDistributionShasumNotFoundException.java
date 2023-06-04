package org.siouan.frontendgradleplugin.domain.installer;

import org.siouan.frontendgradleplugin.domain.FrontendException;

/**
 * Exception thrown when the shasum of a Node distribution could not be found in the file providing shasums for all
 * packages of a given release.
 */
public class NodeDistributionShasumNotFoundException extends FrontendException {

    public NodeDistributionShasumNotFoundException(final String distributionFileName) {
        super("Distribution shasum not found: '" + distributionFileName + '\'');
    }
}
