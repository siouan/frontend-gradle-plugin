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
    private final Provider<Path> nodeInstallDirectoryFromUser;

    /**
     * Whether the Node.js distribution is already installed in the system and shall not be downloaded.
     */
    private final Provider<Boolean> nodeDistributionProvided;

    /**
     * A provider of the path to the install directory given by the environment.
     */
    private final Provider<Path> nodeInstallDirectoryFromEnvironment;

    /**
     * A default path to an install directory.
     */
    private final Path defaultPath;
}
