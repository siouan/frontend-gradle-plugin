package org.siouan.frontendgradleplugin.infrastructure.exception;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.FrontendException;

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
