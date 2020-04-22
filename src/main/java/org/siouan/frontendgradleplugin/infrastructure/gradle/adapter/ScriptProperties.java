package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import java.nio.file.Path;

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
    public ScriptProperties(final Project project, final Path packageJsonDirectoryPath, final String executableType,
        final Path nodeInstallDirectory, final Path yarnInstallDirectory, final String script,
        final Platform platform) {
        this.project = project;
        this.packageJsonDirectoryPath = packageJsonDirectoryPath;
        this.executableType = executableType;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
        this.platform = platform;
    }

    public Project getProject() {
        return project;
    }

    public Path getPackageJsonDirectoryPath() {
        return packageJsonDirectoryPath;
    }

    public String getExecutableType() {
        return executableType;
    }

    public Path getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    public Path getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    public String getScript() {
        return script;
    }

    public Platform getPlatform() {
        return platform;
    }
}
