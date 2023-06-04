package org.siouan.frontendgradleplugin.domain;

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
    void debug(String message, Object... parameters);

    /**
     * Logs a message with a INFO level.
     *
     * @param message Message.
     * @param parameters Message parameters.
     */
    void info(String message, Object... parameters);

    /**
     * Logs a message with a WARN level.
     *
     * @param message Message.
     * @param parameters Message parameters.
     */
    void warn(String message, Object... parameters);
}
