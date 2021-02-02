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

    private boolean verboseModeEnabled;

    private String prefix;

    public GradleLoggerAdapter() {
        this.loggingLevel = LogLevel.LIFECYCLE;
        this.verboseModeEnabled = false;
    }

    /**
     * Initializes the logger to delegate logging to the given Gradle logger.
     *
     * @param gradleLogger Gradle logger.
     * @param loggingLevel Current logging level.
     * @param verboseModeEnabled Whether verbose mode is enabled.
     * @param prefix Prefix prepended before each message logged.
     */
    public void init(@Nullable final org.gradle.api.logging.Logger gradleLogger, @Nonnull final LogLevel loggingLevel,
        final boolean verboseModeEnabled, @Nullable final String prefix) {
        this.gradleLogger = gradleLogger;
        this.loggingLevel = loggingLevel;
        this.verboseModeEnabled = verboseModeEnabled;
        this.prefix = prefix;
    }

    @Override
    public void debug(@Nonnull final String message, @Nullable Object... parameters) {
        if ((gradleLogger == null) || !gradleLogger.isDebugEnabled()) {
            return;
        }

        gradleLogger.debug(formatMessage(message), parameters);
    }

    @Override
    public void info(@Nonnull final String message, @Nullable final Object... parameters) {
        logWithDefaultLevel(LogLevel.INFO, message, parameters);
    }

    @Override
    public void warn(@Nonnull final String message, @Nullable final Object... parameters) {
        logWithDefaultLevel(LogLevel.WARN, message, parameters);
    }

    /**
     * Logs a message with a default logging level if verbose mode is disabled.
     *
     * @param defaultLoggingLevel Default logging level.
     * @param message message.
     * @param parameters Message parameters.
     */
    private void logWithDefaultLevel(@Nonnull final LogLevel defaultLoggingLevel, @Nonnull final String message,
        @Nullable final Object... parameters) {
        if (gradleLogger == null) {
            return;
        }

        if (verboseModeEnabled) {
            gradleLogger.log(loggingLevel, formatMessage(message), parameters);
        } else {
            gradleLogger.log(defaultLoggingLevel, formatMessage(message), parameters);
        }
    }

    @Nonnull
    private String formatMessage(@Nonnull final String message) {
        return (prefix == null) ? message : prefix + message;
    }
}
