package org.siouan.frontendgradleplugin.domain.installer.archiver;

import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Settings to explode an archive.
 *
 * @since 1.1.3
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExplodeCommand {

    /**
     * Underlying platform.
     */
    @EqualsAndHashCode.Include
    private final Platform platform;

    /**
     * Archive file.
     */
    @EqualsAndHashCode.Include
    private final Path archiveFilePath;

    /**
     * Path to the directory where the archive shall be exploded.
     */
    @EqualsAndHashCode.Include
    private final Path targetDirectoryPath;
}
