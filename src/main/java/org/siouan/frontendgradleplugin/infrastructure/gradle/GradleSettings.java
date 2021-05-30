package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.logging.LogLevel;

/**
 * Global settings fixed for the entire build.
 *
 * @since 5.2.0
 */
public class GradleSettings {

    private final LogLevel projectLogLevel;

    private final LogLevel commandLineLogLevel;

    public GradleSettings(@Nullable final LogLevel projectLogLevel, @Nonnull final LogLevel commandLineLogLevel) {
        this.projectLogLevel = projectLogLevel;
        this.commandLineLogLevel = commandLineLogLevel;
    }

    @Nullable
    public LogLevel getProjectLogLevel() {
        return projectLogLevel;
    }

    @Nonnull
    public LogLevel getCommandLineLogLevel() {
        return commandLineLogLevel;
    }
}
