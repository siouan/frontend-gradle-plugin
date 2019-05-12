package org.siouan.frontendgradleplugin.core;

import java.nio.file.Path;

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
     * O/S name.
     */
    private final String osName;

    /**
     * Builds settings to explode an archive.
     *
     * @param archiveFile Archive file.
     * @param targetDirectory Directory where the archive shall be exploded.
     * @param osName O/S name.
     */
    public ExplodeSettings(final Path archiveFile, final Path targetDirectory, final String osName) {
        this.archiveFile = archiveFile;
        this.targetDirectory = targetDirectory;
        this.osName = osName;
    }

    public Path getArchiveFile() {
        return archiveFile;
    }

    public Path getTargetDirectory() {
        return targetDirectory;
    }

    public String getOsName() {
        return osName;
    }
}
