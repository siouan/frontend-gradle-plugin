package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.Action;
import org.gradle.process.ExecSpec;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.StringSplitter;

/**
 * Action that configures a {@link ExecSpec} instance to run a frontend script (NPM/Yarn) with Gradle.
 */
public class ExecSpecAction implements Action<ExecSpec> {

    public static final String CMD_EXECUTABLE = "cmd";

    public static final String CMD_RUN_EXIT_FLAG = "/c";

    public static final char LINUX_SCRIPT_ARG_SEPARATOR_CHAR = ' ';

    public static final char LINUX_SCRIPT_ARG_ESCAPE_CHAR = '\\';

    /**
     * Directory where the 'package.json' file is located.
     */
    private final Path packageJsonDirectory;

    /**
     * Whether the script shall be run with Yarn instead of NPM.
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
     * Execution platform.
     */
    private final Platform platform;

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
     * @param packageJsonDirectory Directory where the 'package.json' file is located.
     * @param executableType Type of execution.
     * @param nodeInstallDirectory Directory where the Node distribution is installed.
     * @param yarnInstallDirectory Directory where the Yarn distribution is installed.
     * @param script Name of the script to execute.
     * @param afterConfigured A consumer called once the exec specification has been configured.
     * @param platform Execution platform.
     * @throws ExecutableNotFoundException When an executable cannot be found (Node, NPM, Yarn).
     */
    public ExecSpecAction(final Path packageJsonDirectory, final ExecutableType executableType, final Path nodeInstallDirectory,
        @Nullable final Path yarnInstallDirectory, final Platform platform, final String script,
        final Consumer<ExecSpec> afterConfigured) throws ExecutableNotFoundException {
        this.packageJsonDirectory = packageJsonDirectory;
        this.executableType = executableType;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.platform = platform;
        this.script = script;
        this.afterConfigured = afterConfigured;

        this.nodeExecutablePath = null;
        this.scriptExecutablePath = null;
        //nodeExecutablePath = GetNodeExecutablePath
        //    .execute(nodeInstallDirectory, platform)
        //    .orElseThrow(ExecutableNotFoundException::newNodeExecutableNotFoundException);
        switch (executableType) {
        case NODE:
            //scriptExecutablePath = nodeExecutablePath;
            break;
        case NPM:
            //scriptExecutablePath = GetNpmExecutablePath
            //    .execute(nodeInstallDirectory, platform)
            //    .orElseThrow(ExecutableNotFoundException::newNpmExecutableNotFoundException);
            break;
        case YARN:
            //scriptExecutablePath = GetYarnExecutablePath
            //    .execute(yarnInstallDirectory, platform)
            //    .orElseThrow(ExecutableNotFoundException::newYarnExecutableNotFoundException);
            break;
        default:
            throw new IllegalArgumentException("Unsupported type of execution: " + executableType);
        }
    }

    /**
     * Configures an execute specification to run the script with a NPM/Yarn command line.
     *
     * @param execSpec Execute specification.
     */
    @Override
    public void execute(@Nonnull final ExecSpec execSpec) {
        execSpec.setWorkingDir(packageJsonDirectory);

        final String executable;
        final List<String> args = new ArrayList<>();
        if (platform.isWindowsOs()) {
            executable = CMD_EXECUTABLE;
            args.add(CMD_RUN_EXIT_FLAG);
            // The command that must be executed in the terminal must be a single argument on itself (like if it was
            // quoted).
            args.add('"' + scriptExecutablePath.toString() + "\" " + script.trim());
        } else {
            executable = scriptExecutablePath.toString();
            args.addAll(new StringSplitter(LINUX_SCRIPT_ARG_SEPARATOR_CHAR, LINUX_SCRIPT_ARG_ESCAPE_CHAR).execute(
                script.trim()));
        }

        // Prepend directories containing the Node and Yarn executables to the 'PATH' environment variable.
        // NPM is in the same directory than Node, do nothing for it.
        final Map<String, Object> environment = execSpec.getEnvironment();
        final String pathVariable = findPathVariable(environment);
        final StringBuilder pathValue = new StringBuilder(nodeExecutablePath.getParent().toString());
        pathValue.append(File.pathSeparatorChar);
        if (executableType == ExecutableType.YARN) {
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
     * Gets the executor used to run the script.
     *
     * @return Executor.
     */
    public ExecutableType getExecutableType() {
        return executableType;
    }

    /**
     * Gets the install directory of Node.
     *
     * @return Directory.
     */
    public Path getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    /**
     * Gets the install directory of Yarn.
     *
     * @return Directory.
     */
    public Path getYarnInstallDirectory() {
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
