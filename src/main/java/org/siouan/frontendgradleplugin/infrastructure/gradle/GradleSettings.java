package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.logging.LogLevel;

/**
 * Global settings fixed for the entire build.
 *
 * @since 5.2.0
 */
public class GradleSettings {

    private final LogLevel projectLogLevel;

    private final LogLevel commandLineLogLevel;

    public GradleSettings(final LogLevel projectLogLevel, final LogLevel commandLineLogLevel) {
        this.projectLogLevel = projectLogLevel;
        this.commandLineLogLevel = commandLineLogLevel;
    }

    public LogLevel getProjectLogLevel() {
        return projectLogLevel;
    }

    public LogLevel getCommandLineLogLevel() {
        return commandLineLogLevel;
    }
}
