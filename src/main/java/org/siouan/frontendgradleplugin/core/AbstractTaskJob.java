package org.siouan.frontendgradleplugin.core;

import org.gradle.api.Task;

/**
 * Class that provides common utilities for this plugin's jobs.
 */
abstract class AbstractTaskJob {

    final Task task;

    /**
     * Builds a job instance related to the given task.
     *
     * @param task Task.
     */
    AbstractTaskJob(final Task task) {
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
    void logDebug(final String message) {
        task.getLogger().debug(formatMessage(message));
    }

    /**
     * Shortcut to log a LIFECYCLE message with the task's logger.
     *
     * @param message Message.
     */
    void logLifecycle(final String message) {
        task.getLogger().lifecycle(formatMessage(message));
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
}
