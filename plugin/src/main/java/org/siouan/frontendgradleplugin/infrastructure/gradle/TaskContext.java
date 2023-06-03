package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;
import org.gradle.api.provider.Provider;

@Builder
@Getter
public class TaskContext {

    private final Path defaultNodeInstallDirectoryPath;

    private final Provider<Path> nodeInstallDirectoryFromEnvironment;

    private final FrontendExtension extension;
}
