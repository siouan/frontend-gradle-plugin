package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Properties to run a script.
 *
 * @since 2.0.0
 */
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
    private final Path nodeInstallDirectory;

    /**
     * The script run by the job with npm or Yarn.
     */
    private final String script;

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Builds a job to run a script.
     *
     * @param execOperations Gradle project.
     * @param packageJsonDirectoryPath Path to the directory containing the {@code package.json} file.
     * @param executableType Executor to use to run the script.
     * @param nodeInstallDirectory Node install directory.
     * @param script The script run by the job.
     * @param platform Underlying platform.
     * @see ExecutableType
     */
    public ScriptProperties(@Nonnull final ExecOperations execOperations, @Nonnull final Path packageJsonDirectoryPath,
        @Nonnull final ExecutableType executableType, @Nonnull final Path nodeInstallDirectory,
        @Nonnull final String script, @Nonnull final Platform platform) {
        this.execOperations = execOperations;
        this.packageJsonDirectoryPath = packageJsonDirectoryPath;
        this.executableType = executableType;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.script = script;
        this.platform = platform;
    }

    @Nonnull
    public ExecOperations getExecOperations() {
        return execOperations;
    }

    @Nonnull
    public Path getPackageJsonDirectoryPath() {
        return packageJsonDirectoryPath;
    }

    @Nonnull
    public ExecutableType getExecutableType() {
        return executableType;
    }

    @Nonnull
    public Path getNodeInstallDirectoryPath() {
        return nodeInstallDirectory;
    }

    @Nonnull
    public String getScript() {
        return script;
    }

    @Nonnull
    public Platform getPlatform() {
        return platform;
    }
}
