package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.siouan.frontendgradleplugin.domain.FrontendException;

/**
 * Exception thrown when a task is not runnable.
 *
 * @since 6.0.0
 */
public class NonRunnableTaskException extends FrontendException {

    public NonRunnableTaskException(final String message) {
        super(message);
    }
}
