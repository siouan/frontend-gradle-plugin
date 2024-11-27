package org.siouan.frontendgradleplugin.domain.installer;

import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Settings for distribution deployment.
 *
 * @since 2.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DeployDistributionCommand {

    /**
     * Underlying platform.
     */
    @EqualsAndHashCode.Include
    private final Platform platform;

    /**
     * Directory where the distribution content can be temporarily extracted (must not exist). The directory is deleted
     * after deployment.
     */
    @EqualsAndHashCode.Include
    private final Path temporaryDirectoryPath;

    /**
     * Directory where the distribution shall be installed.
     */
    @EqualsAndHashCode.Include
    private final Path installDirectoryPath;

    /**
     * Path to the distribution archive.
     */
    @EqualsAndHashCode.Include
    private final Path distributionFilePath;
}
