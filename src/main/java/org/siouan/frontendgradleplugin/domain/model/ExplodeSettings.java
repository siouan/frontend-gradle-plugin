package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings for an archive exploder.
 *
 * @since 1.1.3
 */
public class ExplodeSettings {

    /**
     * Archive file.
     */
    private final Path archiveFile;

    /**
     * Directory where the archive shall be exploded.
     */
    private final Path targetDirectory;

    /**
     * Execution platform.
     */
    private final Platform platform;

    /**
     * Builds settings to explode an archive.
     *
     * @param archiveFile Archive file.
     * @param targetDirectory Directory where the archive shall be exploded.
     * @param platform Execution platform.
     */
    public ExplodeSettings(@Nonnull final Path archiveFile, @Nonnull final Path targetDirectory,
        @Nonnull final Platform platform) {
        this.archiveFile = archiveFile;
        this.targetDirectory = targetDirectory;
        this.platform = platform;
    }

    @Nonnull
    public Path getArchiveFile() {
        return archiveFile;
    }

    @Nonnull
    public Path getTargetDirectory() {
        return targetDirectory;
    }

    @Nonnull
    public Platform getPlatform() {
        return platform;
    }
}
