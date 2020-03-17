package org.siouan.frontendgradleplugin.core;

import java.nio.file.Path;
import javax.annotation.Nullable;

import org.gradle.api.Task;

/**
 * This abstract class provides the reusable logic to run a NPM/Yarn script.
 */
public class RunScriptJob extends AbstractTaskJob {

    /**
     * Directory where the 'package.json' file is located.
     */
    private final Path packageJsonDirectory;

    /**
     * Executor use to run the script.
     */
    private final Executor executor;

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
    private final String osName;

    /**
     * Builds a job to run a script.
     *
     * @param task Parent task.
     * @param packageJsonDirectory Directory where the 'package.json' file is located.
     * @param executor Executor to use to run the script.
     * @param nodeInstallDirectory Node install directory.
     * @param yarnInstallDirectory Yarn install directory.
     * @param script The script run by the job.
     * @param osName O/S name.
     */
    public RunScriptJob(final Task task, final Path packageJsonDirectory, final Executor executor,
        final Path nodeInstallDirectory, @Nullable final Path yarnInstallDirectory, final String script,
        final String osName) {
        super(task);
        this.packageJsonDirectory = packageJsonDirectory;
        this.executor = executor;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
        this.osName = osName;
    }

    public void run() throws ExecutableNotFoundException {
        task
            .getProject()
            .exec(new ExecSpecAction(packageJsonDirectory, executor, nodeInstallDirectory, yarnInstallDirectory, osName,
                script, execSpec -> {
                logDebug(execSpec.getEnvironment().toString());
                logLifecycle(
                    "Running '" + execSpec.getExecutable() + ' ' + String.join(" ", execSpec.getArgs()) + '\'');
            }))
            .rethrowFailure()
            .assertNormalExitValue();
    }
}
