package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.gradle.api.Action;
import org.gradle.process.ExecSpec;

/**
 * Action that configures a {@link ExecSpec} instance to run a frontend script (NPM/Yarn) with Gradle.
 */
public class ExecSpecAction implements Action<ExecSpec> {

    public static final String CMD_EXECUTABLE = "cmd";

    public static final String CMD_RUN_EXIT_FLAG = "/c";

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
     * Name of the O/S.
     */
    private final String osName;

    /**
     * Name of the script to execute.
     */
    private final String script;

    /**
     * A consumer called once the exec specification has been configured.
     */
    private final Consumer<ExecSpec> afterConfigured;

    /**
     * Path to the Node executable.
     */
    private final Path nodeExecutablePath;

    /**
     * Path to the script executable (i.e. NPM or Yarn).
     */
    private final Path scriptExecutablePath;

    /**
     * Builds an action to run a frontend script on the local platform.
     *
     * @param yarnEnabled Whether the script shall be run with Yarn instead of NPM.
     * @param nodeInstallDirectory Directory where the Node distribution is installed.
     * @param yarnInstallDirectory Directory where the Yarn distribution is installed.
     * @param script Name of the script to execute.
     * @param afterConfigured A consumer called once the exec specification has been configured.
     * @param osName Name of the O/S.
     * @throws ExecutableNotFoundException When an executable cannot be found (Node, NPM, Yarn).
     */
    public ExecSpecAction(final boolean yarnEnabled, final File nodeInstallDirectory, final File yarnInstallDirectory,
        final String osName, final String script, final Consumer<ExecSpec> afterConfigured)
        throws ExecutableNotFoundException {
        this.yarnEnabled = yarnEnabled;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.osName = osName;
        this.script = script;
        this.afterConfigured = afterConfigured;

        nodeExecutablePath = Utils.getNodeExecutablePath(nodeInstallDirectory.toPath(), osName)
            .orElseThrow(ExecutableNotFoundException::newNodeExecutableNotFoundException);
        if (yarnEnabled) {
            scriptExecutablePath = Utils.getYarnExecutablePath(yarnInstallDirectory.toPath(), osName)
                .orElseThrow(ExecutableNotFoundException::newYarnExecutableNotFoundException);
        } else {
            scriptExecutablePath = Utils.getNpmExecutablePath(nodeInstallDirectory.toPath(), osName)
                .orElseThrow(ExecutableNotFoundException::newNpmExecutableNotFoundException);
        }
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
            args.add(CMD_RUN_EXIT_FLAG);
            // The command that must be executed in the terminal must be a single argument on itself (like if it was
            // quoted).
            args.add('"' + scriptExecutablePath.toString() + "\" " + script.trim());
        } else {
            executable = scriptExecutablePath.toString();
            args.addAll(Arrays.asList(script.trim().split("\\s+")));
        }

        // Prepend directories containing the Node and Yarn executables to the 'PATH' environment variable.
        // NPM is in the same directory than Node, do nothing for it.
        final Map<String, Object> environment = execSpec.getEnvironment();
        final String pathVariable = findPathVariable(environment);
        final StringBuilder pathValue = new StringBuilder(nodeExecutablePath.getParent().toString());
        pathValue.append(File.pathSeparatorChar);
        if (yarnEnabled) {
            pathValue.append(scriptExecutablePath.getParent().toString());
            pathValue.append(File.pathSeparatorChar);
        }
        pathValue.append((String) environment.getOrDefault(pathVariable, ""));

        execSpec.environment(pathVariable, pathValue.toString());
        execSpec.setExecutable(executable);
        execSpec.setArgs(args);
        afterConfigured.accept(execSpec);
    }

    /**
     * Whether Yarn is enabled and will be used at execution.
     *
     * @return {@code true} if Yarn is enabled.
     */
    public boolean isYarnEnabled() {
        return yarnEnabled;
    }

    /**
     * Gets the install directory of Node.
     *
     * @return Directory.
     */
    public File getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    /**
     * Gets the install directory of Yarn.
     *
     * @return Directory.
     */
    public File getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    /**
     * Gets the script to be executed.
     *
     * @return Script.
     */
    public String getScript() {
        return script;
    }

    /**
     * Finds the name of the 'PATH' variable. Depending on the O/S, it may be have a different case than the exact
     * 'PATH' name.
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
