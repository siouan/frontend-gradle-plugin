package org.siouan.frontendgradleplugin.domain.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface of a component capable to log messages.
 *
 * @since 2.0.0
 */
public interface Logger {

    /**
     * Logs a message with a DEBUG level.
     *
     * @param message Message.
     * @param parameters Message parameters.
     */
    void debug(@Nonnull String message, @Nullable Object... parameters);

    /**
     * Logs a message with a INFO level.
     *
     * @param message Message.
     * @param parameters Message parameters.
     */
    void info(@Nonnull String message, @Nullable Object... parameters);

    /**
     * Logs a message with a WARN level.
     *
     * @param message Message.
     * @param parameters Message parameters.
     */
    void warn(@Nonnull String message, @Nullable Object... parameters);
}
