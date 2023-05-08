package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Provider;

public class ResolveNodeInstallDirectoryPath {

    public Provider<Path> execute(final boolean nodeDistributionProvided,
        @Nonnull final Provider<Path> primaryNodeInstallDirectoryPath,
        @Nonnull final Path defaultNodeInstallDirectoryPath,
        @Nonnull final Provider<Path> nodeInstallDirectoryPathFromEnvironment) {
        final Provider<Path> nodeInstallDirectoryPath =
            nodeDistributionProvided ? primaryNodeInstallDirectoryPath.orElse(nodeInstallDirectoryPathFromEnvironment)
                : primaryNodeInstallDirectoryPath;
        return nodeInstallDirectoryPath.orElse(defaultNodeInstallDirectoryPath);
    }
}
