package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.Project;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Properties to run a script.
 *
 * @since 2.0.0
 */
public class ScriptProperties {

    /**
     * Gradle project.
     */
    private final Project project;

    /**
     * Path to the directory containing the {@code package.json} file.
     */
    private final Path packageJsonDirectoryPath;

    /**
     * Executable use to run the script.
     */
    private final String executableType;

    /**
     * Directory where the Node distribution is installed.
     */
    private final Path nodeInstallDirectory;

    /**
     * Directory where the Yarn distribution is installed.
     */
    private final Path yarnInstallDirectory;

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
     * @param project Gradle project.
     * @param packageJsonDirectoryPath Path to the directory containing the {@code package.json} file.
     * @param executableType Executor to use to run the script.
     * @param nodeInstallDirectory Node install directory.
     * @param yarnInstallDirectory Yarn install directory.
     * @param script The script run by the job.
     * @param platform Underlying platform.
     * @see ExecutableType
     */
    public ScriptProperties(@Nonnull final Project project, @Nonnull final Path packageJsonDirectoryPath,
        @Nonnull final String executableType, @Nullable final Path nodeInstallDirectory,
        @Nullable final Path yarnInstallDirectory, @Nonnull final String script, @Nonnull final Platform platform) {
        this.project = project;
        this.packageJsonDirectoryPath = packageJsonDirectoryPath;
        this.executableType = executableType;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
        this.platform = platform;
    }

    @Nonnull
    public Project getProject() {
        return project;
    }

    @Nonnull
    public Path getPackageJsonDirectoryPath() {
        return packageJsonDirectoryPath;
    }

    @Nonnull
    public String getExecutableType() {
        return executableType;
    }

    @Nullable
    public Path getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Nullable
    public Path getYarnInstallDirectory() {
        return yarnInstallDirectory;
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
