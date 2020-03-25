package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.logging.LogLevel;

/**
 * Settings for the distribution installer.
 *
 * @since 1.1.2
 */
public class InstallDistributionSettings {

    private final String version;

    private final String downloadUrl;

    private final Platform platform;

    /**
     * Default logging level.
     */
    private final LogLevel loggingLevel;

    private final Path temporaryDirectory;

    /**
     * Directory where the distribution shall be installed.
     */
    private final Path installDirectory;

    /**
     * Builds an installer.
     *
     * @param platform Execution platform.
     * @param temporaryDirectory Temporary directory.
     * @param installDirectory Install directory.
     */
    public InstallDistributionSettings(@Nonnull final String version, @Nullable final String downloadUrl,
        @Nonnull final Platform platform, @Nonnull LogLevel loggingLevel, @Nonnull final Path temporaryDirectory,
        @Nonnull final Path installDirectory) {
        this.version = version;
        this.downloadUrl = downloadUrl;
        this.platform = platform;
        this.loggingLevel = loggingLevel;
        this.temporaryDirectory = temporaryDirectory;
        this.installDirectory = installDirectory;
    }

    @Nonnull
    public String getVersion() {
        return version;
    }

    @Nullable
    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the default logging level.
     *
     * @return Logging level.
     */
    @Nonnull
    public LogLevel getLoggingLevel() {
        return loggingLevel;
    }

    @Nonnull
    public Path getTemporaryDirectory() {
        return temporaryDirectory;
    }

    /**
     * Gets the install directory.
     *
     * @return Install directory.
     */
    @Nonnull
    public Path getInstallDirectory() {
        return installDirectory;
    }
}
