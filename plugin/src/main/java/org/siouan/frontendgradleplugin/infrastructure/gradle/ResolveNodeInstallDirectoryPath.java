package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import org.gradle.api.provider.Provider;

/**
 * Resolves the path to the Node.js distribution.
 *
 * @since 7.0.0
 */
public class ResolveNodeInstallDirectoryPath {

    /**
     * Resolves the applicable path to the Node.js distribution just-in-time.
     *
     * @param command Command providing resolution parameters.
     * @return A provider of the path to the install directory:
     * <ul>
     * <li>The directory given by a provider user-defined.</li>
     * <li>Or the directory given by a provider scanning the environment if the distribution is already provided.</li>
     * <li>Or a default directory.</li>
     * </ul>
     */
    public Provider<Path> execute(final ResolveNodeInstallDirectoryPathCommand command) {
        final Provider<Path> nodeInstallDirectoryPath = command
            .getNodeDistributionProvided()
            .flatMap(nodeDistributionProvided -> Boolean.TRUE.equals(nodeDistributionProvided) ? command
                .getNodeInstallDirectoryFromUser()
                .orElse(command.getNodeInstallDirectoryFromEnvironment()) : command.getNodeInstallDirectoryFromUser());
        return nodeInstallDirectoryPath.orElse(command.getDefaultPath());
    }
}
