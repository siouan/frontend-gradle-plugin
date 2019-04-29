package org.siouan.frontendgradleplugin.core;

import org.gradle.api.Task;

/**
 * Class that provides common utilities for this plugin's jobs.
 */
abstract class AbstractJob {

    protected final Task task;

    /**
     * Builds a job instance related to the given task.
     *
     * @param task Task.
     */
    protected AbstractJob(final Task task) {
        this.task = task;
    }

    private String formatMessage(final String message) {
        return '[' + task.getName() + "] " + message;
    }

    /**
     * Shortcut to log a DEBUG message with the task's logger.
     *
     * @param message Message.
     */
    protected void logDebug(final String message) {
        task.getLogger().debug(formatMessage(message));
    }

    /**
     * Shortcut to log an ERROR message with the task's logger.
     *
     * @param message Message.
     */
    protected void logError(final String message) {
        task.getLogger().error(formatMessage(message));
    }

    /**
     * Shortcut to log a LIFECYCLE message with the task's logger.
     *
     * @param message Message.
     */
    protected void logLifecycle(final String message) {
        task.getLogger().lifecycle(formatMessage(message));
    }

    /**
     * Shortcut to log a WARN LIFECYCLE message with the task's logger.
     *
     * @param message Message.
     */
    protected void logWarn(final String message, final Throwable throwable) {
        task.getLogger().warn(formatMessage(message), throwable);
    }
}
