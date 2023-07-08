package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.logging.LogLevel;

/**
 * Global settings fixed for the entire build.
 *
 * @since 5.2.0
 */
public record GradleSettings(LogLevel projectLogLevel, LogLevel commandLineLogLevel) {}
