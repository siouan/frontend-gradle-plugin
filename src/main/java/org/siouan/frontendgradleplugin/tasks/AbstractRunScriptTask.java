package org.siouan.frontendgradleplugin.tasks;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.core.Executor;
import org.siouan.frontendgradleplugin.core.RunScriptJob;
import org.siouan.frontendgradleplugin.core.Utils;

/**
 * This abstract class provides the reusable logic to run a NPM/Yarn script. Sub-classes must expose inputs and
 * outputs.
 */
public abstract class AbstractRunScriptTask extends DefaultTask {

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    final Property<Boolean> yarnEnabled;

    /**
     * Directory where the Node distribution is installed.
     */
    final Property<File> nodeInstallDirectory;

    /**
     * Directory where the Yarn distribution is installed.
     */
    final Property<File> yarnInstallDirectory;

    /**
     * The script to run with NPM/Yarn.
     */
    final Property<String> script;

    AbstractRunScriptTask() {
        yarnEnabled = getProject().getObjects().property(Boolean.class);
        nodeInstallDirectory = getProject().getObjects().property(File.class);
        yarnInstallDirectory = getProject().getObjects().property(File.class);
        script = getProject().getObjects().property(String.class);
    }

    protected Executor getExecutionType() {
        return yarnEnabled.get() ? Executor.YARN : Executor.NPM;
    }

    /**
     * Executes the task. If a script has been provided, it is run with NPM/Yarn. Otherwise, the task does nothing.
     *
     * @throws ExecutableNotFoundException When an executable cannot be found (Node, NPM, Yarn).
     */
    @TaskAction
    public void execute() throws ExecutableNotFoundException {
        if (script.isPresent()) {
            new RunScriptJob(this, getExecutionType(), nodeInstallDirectory.get(), yarnInstallDirectory.get(),
                script.get(), Utils.getSystemOsName()).run();
        }
    }
}
