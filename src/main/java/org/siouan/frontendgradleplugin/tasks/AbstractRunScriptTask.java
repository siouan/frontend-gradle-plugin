package org.siouan.frontendgradleplugin.tasks;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.core.Executor;
import org.siouan.frontendgradleplugin.core.MissingScriptException;
import org.siouan.frontendgradleplugin.core.RunScriptJob;
import org.siouan.frontendgradleplugin.core.Utils;

/**
 * This abstract class provides the reusable logic to run a NPM/Yarn script. Sub-classes must expose inputs and
 * outputs.
 */
public abstract class AbstractRunScriptTask extends DefaultTask {

    /**
     * Directory where the 'package.json' file is located.
     */
    final Property<File> packageJsonDirectory;

    /**
     * Default logging level.
     */
    final Property<LogLevel> loggingLevel;

    /**
     * Directory where the Node distribution is installed.
     */
    final DirectoryProperty nodeInstallDirectory;

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    final Property<Boolean> yarnEnabled;

    /**
     * Directory where the Yarn distribution is installed.
     */
    final DirectoryProperty yarnInstallDirectory;

    /**
     * The script to run with NPM/Yarn.
     */
    final Property<String> script;

    /**
     * Whether this task shall complete with failed status when the script is not defined, or with success status
     * otherwise.
     */
    final boolean failOnMissingScriptEnabled;

    AbstractRunScriptTask(boolean failOnMissingScriptEnabled) {
        packageJsonDirectory = getProject().getObjects().property(File.class);
        loggingLevel = getProject().getObjects().property(LogLevel.class);
        nodeInstallDirectory = getProject().getObjects().directoryProperty();
        yarnEnabled = getProject().getObjects().property(Boolean.class);
        yarnInstallDirectory = getProject().getObjects().directoryProperty();
        script = getProject().getObjects().property(String.class);
        this.failOnMissingScriptEnabled = failOnMissingScriptEnabled;
    }

    @Input
    @Optional
    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    @Input
    @Optional
    public Property<LogLevel> getLoggingLevel() {
        return loggingLevel;
    }

    @InputDirectory
    @Optional
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @InputDirectory
    @Optional
    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    @Internal
    protected Executor getExecutionType() {
        return yarnEnabled.get() ? Executor.YARN : Executor.NPM;
    }

    /**
     * Executes the task. If a script has been provided, it is run with NPM/Yarn. Otherwise, the task does nothing.
     *
     * @throws MissingScriptException When the script was not defined in this task and the task is configured to fail in
     * such case (see {@link #failOnMissingScriptEnabled} property).
     * @throws ExecutableNotFoundException When an executable cannot be found (Node, NPM, Yarn).
     */
    @TaskAction
    public void execute() throws MissingScriptException, ExecutableNotFoundException {
        if (script.isPresent()) {
            new RunScriptJob(this, loggingLevel.get(), packageJsonDirectory.map(File::toPath).get(), getExecutionType(),
                nodeInstallDirectory.getAsFile().map(File::toPath).get(),
                yarnInstallDirectory.getAsFile().map(File::toPath).getOrNull(), script.get(),
                Utils.getSystemOsName()).run();
        } else if (failOnMissingScriptEnabled) {
            throw new MissingScriptException();
        }
    }
}
