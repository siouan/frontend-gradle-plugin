package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

/**
 * Exception thrown when a JSON file does not contain valid JSON.
 *
 * @since 7.0.0
 */
public class InvalidJsonFileException extends FrontendException {

    public InvalidJsonFileException(final Path jsonFilePath) {
        super("Invalid JSON file: " + jsonFilePath);
    }
}
