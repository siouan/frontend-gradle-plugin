package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import lombok.Builder;
import org.gradle.api.provider.Provider;

@Builder
public record TaskContext(Path defaultNodeInstallDirectoryPath, Provider<Path> nodeInstallDirectoryFromEnvironment,
    FrontendExtension extension) {}
