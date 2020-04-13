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

    private org.gradle.api.logging.Logger gradleLogger;

    private LogLevel loggingLevel;

    private String prefix;

    public GradleLoggerAdapter() {
        this.loggingLevel = LogLevel.LIFECYCLE;
    }

    /**
     * Initializes the logger to delegate logging to the given Gradle logger.
     *
     * @param gradleLogger Gradle logger.
     * @param loggingLevel Current logging level.
     * @param prefix Prefix prepended before each message logged.
     */
    public void init(@Nullable org.gradle.api.logging.Logger gradleLogger, @Nonnull LogLevel loggingLevel,
        @Nullable final String prefix) {
        this.gradleLogger = gradleLogger;
        this.loggingLevel = loggingLevel;
        this.prefix = prefix;
    }

    @Override
    public void debug(@Nonnull final String message, @Nullable Object... parameters) {
        if (gradleLogger != null) {
            gradleLogger.debug(formatMessage(message), parameters);
        }
    }

    @Override
    public void log(@Nonnull final String message, @Nullable final Object... parameters) {
        if (gradleLogger != null) {
            gradleLogger.log(loggingLevel, formatMessage(message), parameters);
        }
    }

    @Nonnull
    private String formatMessage(@Nonnull final String message) {
        return (prefix == null) ? message : prefix + message;
    }
}
