package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.logging.LogLevel;
import org.siouan.frontendgradleplugin.domain.Logger;

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
    public void init(final org.gradle.api.logging.Logger gradleLogger, final LogLevel loggingLevel,
        final boolean verboseModeEnabled, final String prefix) {
        this.gradleLogger = gradleLogger;
        this.loggingLevel = loggingLevel;
        this.verboseModeEnabled = verboseModeEnabled;
        this.prefix = prefix;
    }

    @Override
    public void debug(final String message, Object... parameters) {
        if ((gradleLogger != null) && gradleLogger.isDebugEnabled()) {
            gradleLogger.debug(formatMessage(message), parameters);
        }
    }

    @Override
    public void info(final String message, final Object... parameters) {
        logWithDefaultLevel(LogLevel.INFO, message, parameters);
    }

    @Override
    public void warn(final String message, final Object... parameters) {
        logWithDefaultLevel(LogLevel.WARN, message, parameters);
    }

    /**
     * Logs a message with a default logging level if verbose mode is disabled.
     *
     * @param defaultLoggingLevel Default logging level.
     * @param message message.
     * @param parameters Message parameters.
     */
    private void logWithDefaultLevel(final LogLevel defaultLoggingLevel, final String message,
        final Object... parameters) {
        if (gradleLogger == null) {
            return;
        }

        if (verboseModeEnabled) {
            gradleLogger.log(loggingLevel, formatMessage(message), parameters);
        } else {
            gradleLogger.log(defaultLoggingLevel, formatMessage(message), parameters);
        }
    }

    private String formatMessage(final String message) {
        return (prefix == null) ? message : prefix + message;
    }
}
