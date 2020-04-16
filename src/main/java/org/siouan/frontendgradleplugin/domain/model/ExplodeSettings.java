package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings to explode an archive.
 *
 * @since 1.1.3
 */
public class ExplodeSettings {

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Archive file.
     */
    private final Path archiveFilePath;

    /**
     * Path to the directory where the archive shall be exploded.
     */
    private final Path targetDirectoryPath;

    /**
     * Builds settings to explode an archive.
     *
     * @param platform Underlying platform.
     * @param archiveFilePath Archive file.
     * @param targetDirectoryPath Path to the directory where the archive shall be exploded.
     */
    public ExplodeSettings(@Nonnull final Platform platform, @Nonnull final Path archiveFilePath,
        @Nonnull final Path targetDirectoryPath) {
        this.platform = platform;
        this.archiveFilePath = archiveFilePath;
        this.targetDirectoryPath = targetDirectoryPath;
    }

    /**
     * Gets the underlying platform.
     *
     * @return Platform.
     */
    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the path to the archive file.
     *
     * @return Path.
     */
    @Nonnull
    public Path getArchiveFilePath() {
        return archiveFilePath;
    }

    /**
     * Gets the path to the directory where the archive shall be exploded.
     *
     * @return Path.
     */
    @Nonnull
    public Path getTargetDirectoryPath() {
        return targetDirectoryPath;
    }
}
