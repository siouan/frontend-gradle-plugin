package org.siouan.frontendgradleplugin.job;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.gradle.api.Action;
import org.gradle.process.ExecSpec;
import org.siouan.frontendgradleplugin.Utils;

/**
 * Action that configures a {@link ExecSpec} instance to run a frontend script (NPM/Yarn) with Gradle.
 */
public class ExecSpecAction implements Action<ExecSpec> {

    public static final String SHELL_EXECUTABLE = "sh";

    public static final String CMD_EXECUTABLE = "cmd";

    public static final String NPM_EXECUTABLE = "npm";

    public static final String YARN_EXECUTABLE = "yarn";

    /**
     * Whether the script shall be run with Yarn instead of NPM.
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

    /**
     * Name of the script to execute.
     */
    private final String script;

    /**
     * A consumer called once the exec specification has been configured.
     */
    private final Consumer<ExecSpec> afterConfigured;

    /**
     * Name of the O/S.
     */
    private final String osName;

    /**
     * Builds an action to run a frontend script on the local platform.
     *
     * @param yarnEnabled Whether the script shall be run with Yarn instead of NPM.
     * @param nodeInstallDirectory Directory where the Node distribution is installed.
     * @param yarnInstallDirectory Directory where the Yarn distribution is installed.
     * @param script Name of the script to execute.
     * @param afterConfigured A consumer called once the exec specification has been configured.
     */
    public ExecSpecAction(final boolean yarnEnabled, final File nodeInstallDirectory, final File yarnInstallDirectory,
        final String script, final Consumer<ExecSpec> afterConfigured) {
        this(yarnEnabled, nodeInstallDirectory, yarnInstallDirectory, script, afterConfigured,
            System.getProperty("os.name"));
    }

    public ExecSpecAction(final boolean yarnEnabled, final File nodeInstallDirectory, final File yarnInstallDirectory,
        final String script, final Consumer<ExecSpec> afterConfigured, final String osName) {
        this.yarnEnabled = yarnEnabled;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
        this.afterConfigured = afterConfigured;
        this.osName = osName;
    }

    /**
     * Configures an execute specification to run the script with a NPM/Yarn command line.
     *
     * @param execSpec Execute specification.
     */
    @Override
    public void execute(final ExecSpec execSpec) {
        final String executable;
        final List<String> args = new ArrayList<>();
        if (Utils.isWindowsOs(osName)) {
            executable = CMD_EXECUTABLE;
            args.add("/c");
        } else {
            executable = SHELL_EXECUTABLE;
        }
        final String scriptExecutable;
        if (yarnEnabled) {
            scriptExecutable = YARN_EXECUTABLE;
        } else {
            scriptExecutable = NPM_EXECUTABLE;
        }
        // The command that must be executed in the shell/terminal must be a single argument on itself (like if it was
        // quoted).
        args.add(scriptExecutable + ' ' + script);

        // Prepend directories containing the Node and Yarn executables to the 'PATH' environment variable.
        final Map<String, Object> environment = execSpec.getEnvironment();
        final String pathVariable = findPathVariable(environment);
        final StringBuffer pathValue = new StringBuffer(nodeInstallDirectory.getAbsolutePath());
        pathValue.append(File.pathSeparatorChar);
        if (yarnEnabled) {
            pathValue.append(yarnInstallDirectory.getAbsolutePath());
            pathValue.append(File.separatorChar);
            pathValue.append("bin");
            pathValue.append(File.pathSeparatorChar);
        }
        pathValue.append((String) environment.getOrDefault(pathVariable, ""));

        execSpec.environment(pathVariable, pathValue.toString());
        execSpec.setExecutable(executable);
        execSpec.setArgs(args);
        afterConfigured.accept(execSpec);
    }

    public boolean isYarnEnabled() {
        return yarnEnabled;
    }

    public File getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    public File getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    public String getScript() {
        return script;
    }

    /**
     * Finds the name of the 'PATH' variable. Depending on the O/S, it may be a different value than the exact 'PATH'
     * name.
     *
     * @param environment Map of environment variables.
     * @return The name of the 'PATH' variable.
     */
    private String findPathVariable(final Map<String, Object> environment) {
        final String pathVariable;
        if (environment.containsKey("Path")) {
            pathVariable = "Path";
        } else {
            pathVariable = "PATH";
        }
        return pathVariable;
    }
}
