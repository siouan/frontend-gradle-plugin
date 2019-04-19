package org.siouan.frontendgradleplugin.job;

import org.gradle.api.Task;

/**
 * Class that provides common utilities for this plugin's jobs.
 */
public abstract class AbstractJob {

    protected final Task task;

    protected AbstractJob(final Task task) {
        this.task = task;
    }

    private String formatMessage(final String message) {
        return '[' + task.getName() + "] " + message;
    }

    protected void logDebug(final String message) {
        task.getLogger().debug(formatMessage(message));
    }

    protected void logError(final String message) {
        task.getLogger().error(formatMessage(message));
    }

    protected void logError(final String message, final Throwable throwable) {
        task.getLogger().error(formatMessage(message), throwable);
    }

    protected void logInfo(final String message) {
        task.getLogger().info(formatMessage(message));
    }

    protected void logLifecycle(final String message) {
        task.getLogger().lifecycle(formatMessage(message));
    }

    protected void logWarn(final String message) {
        task.getLogger().warn(formatMessage(message));
    }

    protected void logWarn(final String message, final Throwable throwable) {
        task.getLogger().warn(formatMessage(message), throwable);
    }
}
