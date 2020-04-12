package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.logging.LogLevel;
import org.siouan.frontendgradleplugin.domain.model.Logger;

/**
 * Implementation that delegate logging to a Gradle logger.
 *
 * @since 2.0.0
 */
public class GradleLoggerAdapter implements Logger {

    private final org.gradle.api.logging.Logger gradleLogger;

    private final LogLevel loggingLevel;

    private final String prefix;

    /**
     * Builds a job instance related to the given task.
     *
     * @param gradleLogger Gradle logger.
     * @param loggingLevel Current logging level.
     * @param prefix Prefix prepended before each message logged.
     */
    public GradleLoggerAdapter(@Nonnull org.gradle.api.logging.Logger gradleLogger, @Nonnull LogLevel loggingLevel,
        @Nonnull final String prefix) {
        this.gradleLogger = gradleLogger;
        this.loggingLevel = loggingLevel;
        this.prefix = prefix;
    }

    @Override
    public void debug(@Nonnull final String message, @Nullable Object... parameters) {
        gradleLogger.debug(formatMessage(message), parameters);
    }

    @Override
    public void log(@Nonnull final String message, @Nullable final Object... parameters) {
        gradleLogger.log(loggingLevel, formatMessage(message), parameters);
    }

    @Nonnull
    private String formatMessage(@Nonnull final String message) {
        return prefix + message;
    }
}
