package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import lombok.Builder;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Properties to run a script.
 *
 * @param execOperations Gradle exec operations.
 * @param packageJsonDirectoryPath Path to the directory containing the {@code package.json} file.
 * @param executableType Executable use to run the script.
 * @param nodeInstallDirectoryPath Directory where the Node distribution is installed.
 * @param script The script run by the job with npm or Yarn.
 * @param platform Underlying platform.
 * @since 2.0.0
 */
@Builder
public record ScriptProperties(ExecOperations execOperations, Path packageJsonDirectoryPath,
    ExecutableType executableType, Path nodeInstallDirectoryPath, String script, Platform platform) {}
