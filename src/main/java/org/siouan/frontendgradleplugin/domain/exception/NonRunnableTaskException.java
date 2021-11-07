package org.siouan.frontendgradleplugin.domain.exception;

import javax.annotation.Nonnull;

/**
 * Exception thrown when a task is not runnable.
 *
 * @since 6.0.0
 */
public class NonRunnableTaskException extends FrontendException {

    public NonRunnableTaskException(@Nonnull final String message) {
        super(message);
    }
}
