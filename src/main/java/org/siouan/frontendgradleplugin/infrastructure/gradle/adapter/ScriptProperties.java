package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import java.nio.file.Path;

import org.gradle.api.Project;
import org.gradle.api.logging.LogLevel;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;

public class ScriptProperties {

    /**
     * Gradle project.
     */
    private final Project project;

    private final LogLevel loggingLevel;

    private final Path packageJsonDirectory;

    /**
     * Executable use to run the script.
     */
    private final ExecutableType executableType;

    /**
     * Directory where the Node distribution is installed.
     */
    private final Path nodeInstallDirectory;

    /**
     * Directory where the Yarn distribution is installed.
     */
    private final Path yarnInstallDirectory;

    /**
     * The script run by the job with NPM or Yarn.
     */
    private final String script;

    /**
     * O/S name.
     */
    private final Platform platform;

    /**
     * Builds a job to run a script.
     *
     * @param project
     * @param loggingLevel
     * @param packageJsonDirectory
     * @param executableType Executor to use to run the script.
     * @param nodeInstallDirectory Node install directory.
     * @param yarnInstallDirectory Yarn install directory.
     * @param script The script run by the job.
     * @param platform Execution platform.
     */
    public ScriptProperties(final Project project, final LogLevel loggingLevel, final Path packageJsonDirectory,
        final ExecutableType executableType, final Path nodeInstallDirectory, final Path yarnInstallDirectory,
        final String script, final Platform platform) {
        this.project = project;
        this.loggingLevel = loggingLevel;
        this.packageJsonDirectory = packageJsonDirectory;
        this.executableType = executableType;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
        this.platform = platform;
    }

    public Project getProject() {
        return project;
    }

    public LogLevel getLoggingLevel() {
        return loggingLevel;
    }

    public Path getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    public ExecutableType getExecutableType() {
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
