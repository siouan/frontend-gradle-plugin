package org.siouan.frontendgradleplugin.domain.exception;

/**
 * Exception thrown when the shasum of a Node distribution could not be found in the file providing shasums for all
 * packages of a given release.
 */
public class NodeDistributionShasumNotFoundException extends DistributionValidatorException {

    public NodeDistributionShasumNotFoundException() {
        super("Checksum not found for Node distribution.");
    }
}
