package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.gradle.api.provider.Provider;

public class TaskContext {

    private final Path defaultNodeInstallDirectoryPath;

    private final Provider<Path> nodeInstallDirectoryFromEnvironment;

    private final FrontendExtension extension;

    public TaskContext(@Nonnull final Path defaultNodeInstallDirectoryPath,
        @Nonnull final Provider<Path> nodeInstallDirectoryFromEnvironment, @Nonnull final FrontendExtension extension) {
        this.defaultNodeInstallDirectoryPath = defaultNodeInstallDirectoryPath;
        this.nodeInstallDirectoryFromEnvironment = nodeInstallDirectoryFromEnvironment;
        this.extension = extension;
    }

    @Nonnull
    public Path getDefaultNodeInstallDirectoryPath() {
        return defaultNodeInstallDirectoryPath;
    }

    @Nonnull
    public Provider<Path> getNodeInstallDirectoryFromEnvironment() {
        return nodeInstallDirectoryFromEnvironment;
    }

    @Nonnull
    public FrontendExtension getExtension() {
        return extension;
    }
}
