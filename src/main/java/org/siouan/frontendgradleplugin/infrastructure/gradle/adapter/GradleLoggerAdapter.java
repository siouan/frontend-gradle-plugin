package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;

/**
 * Class that provides common utilities for this plugin's jobs.
 */
abstract class GradleLoggerAdapter {

    final Task task;

    final LogLevel loggingLevel;

    /**
     * Builds a job instance related to the given task.
     *
     * @param task Task.
     * @param loggingLevel Default logging level.
     */
    GradleLoggerAdapter(final Task task, final LogLevel loggingLevel) {
        this.task = task;
        this.loggingLevel = loggingLevel;
    }

    private String formatMessage(final String message) {
        return '[' + task.getName() + "] " + message;
    }

    /**
     * Shortcut to log a DEBUG message with the task's logger.
     *
     * @param message Message.
     */
    void logDebug(final String message) {
        task.getLogger().debug(formatMessage(message));
    }

    /**
     * Shortcut to log a WARN message with the task's logger.
     *
     * @param message Message.
     * @param throwable Throwable.
     */
    void logWarn(final String message, final Throwable throwable) {
        task.getLogger().warn(formatMessage(message), throwable);
    }

    /**
     * Shortcut to log a message with the task default log level.
     *
     * @param message Message.
     */
    void logMessage(final String message) {
        task.getLogger().log(loggingLevel, formatMessage(message));
    }
}
