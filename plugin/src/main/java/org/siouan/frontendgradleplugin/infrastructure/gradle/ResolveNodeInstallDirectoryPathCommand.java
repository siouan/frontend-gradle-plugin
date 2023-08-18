package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import lombok.Builder;
import org.gradle.api.provider.Provider;

/**
 * @param nodeInstallDirectoryFromUser A user-defined provider of the path to the install directory
 * @param nodeDistributionProvided Whether the Node.js distribution is already installed in the system and shall not be
 * downloaded.
 * @param nodeInstallDirectoryFromEnvironment A provider of the path to the install directory given by the environment.
 * @param defaultPath A default path to an install directory.
 */
@Builder
public record ResolveNodeInstallDirectoryPathCommand(Provider<Path> nodeInstallDirectoryFromUser,
    Provider<Boolean> nodeDistributionProvided, Provider<Path> nodeInstallDirectoryFromEnvironment, Path defaultPath) {}
