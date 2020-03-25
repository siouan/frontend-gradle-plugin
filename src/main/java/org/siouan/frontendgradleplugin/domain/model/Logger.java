package org.siouan.frontendgradleplugin.domain.model;

/**
 * Interface of a component capable to log messages.
 *
 * @since 1.4.2
 */
public interface Logger {

    /**
     * Logs a message with a DEBUG level.
     *
     * @param message Message.
     */
    void debug(String message);

    /**
     * Logs a message with an INFO level.
     *
     * @param message Message.
     */
    void info(String message);

    /**
     * Logs a message with a WARN level.
     *
     * @param message Message.
     * @param throwable Throwable.
     */
    void warn(String message, Throwable throwable);

    /**
     * Logs a message with the default level.
     *
     * @param message Message.
     */
    void log(String message);
}
