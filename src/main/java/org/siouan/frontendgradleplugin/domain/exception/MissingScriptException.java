package org.siouan.frontendgradleplugin.domain.exception;

/**
 * Exception thrown when no script was provided.
 *
 * @since 1.3.0
 */
public class MissingScriptException extends FrontendException {

    public MissingScriptException() {
        super("No script defined");
    }
}
