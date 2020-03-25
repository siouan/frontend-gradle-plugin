package org.siouan.frontendgradleplugin.domain.exception;

/**
 * Exception thrown when the checksum of a Node distribution could not be found in the file providing checksums for all
 * packages of a given release.
 */
public class NodeDistributionChecksumNotFoundException extends FrontendException {

    public NodeDistributionChecksumNotFoundException() {
        super("Checksum not found for Node distribution.");
    }
}
