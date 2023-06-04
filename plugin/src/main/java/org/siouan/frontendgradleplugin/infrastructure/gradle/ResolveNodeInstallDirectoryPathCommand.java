package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;
import org.gradle.api.provider.Provider;

@Getter
@Builder
public class ResolveNodeInstallDirectoryPathCommand {

    /**
     * A user-defined provider of the path to the install directory.
     */
    private final Provider<Path> userPath;

    /**
     * Whether the Node.js distribution is already installed in the system and shall not be downloaded.
     */
    private final boolean nodeDistributionProvided;

    /**
     * A provider of the path to the install directory scanning the environment.
     */
    private final Provider<Path> environmentPath;

    /**
     * A default path to an install directory.
     */
    private final Path defaultPath;
}
