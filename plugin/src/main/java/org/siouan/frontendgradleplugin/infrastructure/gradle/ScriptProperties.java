package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Properties to run a script.
 *
 * @since 2.0.0
 */
@Builder
@Getter
public class ScriptProperties {

    /**
     * Gradle exec operations.
     */
    private final ExecOperations execOperations;

    /**
     * Path to the directory containing the {@code package.json} file.
     */
    private final Path packageJsonDirectoryPath;

    /**
     * Executable use to run the script.
     */
    private final ExecutableType executableType;

    /**
     * Directory where the Node distribution is installed.
     */
    private final Path nodeInstallDirectoryPath;

    /**
     * The script run by the job with npm or Yarn.
     */
    private final String script;

    /**
     * Underlying platform.
     */
    private final Platform platform;
}
