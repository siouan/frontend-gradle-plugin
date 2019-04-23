package org.siouan.frontendgradleplugin.core;

import java.io.File;

import org.gradle.api.Task;

/**
 * This abstract class provides the reusable logic to run a NPM/Yarn script.
 */
public class ScriptRunJob extends AbstractJob {

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    private final boolean yarnEnabled;

    /**
     * Directory where the Node distribution is installed.
     */
    private final File nodeInstallDirectory;

    /**
     * Directory where the Yarn distribution is installed.
     */
    private final File yarnInstallDirectory;

    private final String script;

    public ScriptRunJob(final Task task, final boolean yarnEnabled, final File nodeInstallDirectory,
        final File yarnInstallDirectory, final String script) {
        super(task);
        this.yarnEnabled = yarnEnabled;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
    }

    public void run() {
        task.getProject()
            .exec(new ExecSpecAction(yarnEnabled, nodeInstallDirectory, yarnInstallDirectory, script, execSpec -> {
                logDebug(execSpec.getEnvironment().toString());
                logLifecycle("Running '" + execSpec.getExecutable() + ' ' + String.join(" ", execSpec.getArgs()) + '\'');
            })).rethrowFailure().assertNormalExitValue();
    }
}
