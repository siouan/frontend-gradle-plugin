package org.siouan.frontendgradleplugin.domain.usecase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.model.GetExecutablePathQuery;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.StringSplitter;

/**
 * Resolves execution settings to run a script.
 *
 * @since 2.0.0
 */
public class ResolveExecutionSettings {

    /**
     * Path to the wrapper Windows executable to launch the script executable.
     */
    public static final Path WINDOWS_EXECUTABLE_PATH = Paths.get("cmd");

    /**
     * Flag to terminate the Windows executable when the script executable completes.
     */
    public static final String WINDOWS_EXECUTABLE_AUTOEXIT_FLAG = "/c";

    /**
     * Path to the wrapper Unix executable to launch the script executable.
     */
    public static final Path UNIX_EXECUTABLE_PATH = Paths.get("/bin", "sh");

    /**
     * Flag to terminate the Unix executable when the script executable completes.
     */
    public static final String UNIX_EXECUTABLE_AUTOEXIT_FLAG = "-c";

    /**
     * Character used on Unix-like O/S to separate arguments.
     */
    public static final char UNIX_SCRIPT_ARG_SEPARATOR_CHAR = ' ';

    /**
     * Character used by the plugin to escape an unrelevant argument separator.
     */
    public static final char UNIX_SCRIPT_ARG_ESCAPE_CHAR = '\\';

    private final GetNodeExecutablePath getNodeExecutablePath;

    private final GetExecutablePath getExecutablePath;

    public ResolveExecutionSettings(final GetNodeExecutablePath getNodeExecutablePath,
        final GetExecutablePath getExecutablePath) {
        this.getNodeExecutablePath = getNodeExecutablePath;
        this.getExecutablePath = getExecutablePath;
    }

    /**
     * Resolves execution settings to run the given script with a node/npm/npx/yarn executable.
     *
     * @param packageJsonDirectoryPath Path to the {@code package.json} file, used as the working directory.
     * @param executableType Type of executable.
     * @param nodeInstallDirectoryPath Path to the Node install directory.
     * @param platform Underlying platform.
     * @param script Script.
     * @return Appropriate settings to run the script on the given platform.
     * @see ExecutableType
     */
    @Nonnull
    public ExecutionSettings execute(@Nonnull final Path packageJsonDirectoryPath,
        @Nonnull final ExecutableType executableType, @Nonnull final Path nodeInstallDirectoryPath,
        @Nonnull final Platform platform, @Nonnull final String script) {
        final Path nodeExecutablePath = getNodeExecutablePath.execute(nodeInstallDirectoryPath, platform);
        final Path executablePath = getExecutablePath.execute(
            new GetExecutablePathQuery(executableType, nodeInstallDirectoryPath, platform));

        final Path executable;
        final List<String> args = new ArrayList<>();
        if (platform.isWindowsOs()) {
            executable = WINDOWS_EXECUTABLE_PATH;
            args.add(WINDOWS_EXECUTABLE_AUTOEXIT_FLAG);
            // The command that must be executed in the terminal must be a single argument on itself (like if it was
            // quoted).
            args.add(escapeWhitespacesFromCommandLineToken(executablePath) + " " + script.trim());
        } else {
            executable = UNIX_EXECUTABLE_PATH;
            args.add(UNIX_EXECUTABLE_AUTOEXIT_FLAG);
            args.add(
                escapeWhitespacesFromCommandLineToken(executablePath) + UNIX_SCRIPT_ARG_SEPARATOR_CHAR + String.join(
                    Character.toString(UNIX_SCRIPT_ARG_SEPARATOR_CHAR),
                    new StringSplitter(UNIX_SCRIPT_ARG_SEPARATOR_CHAR, UNIX_SCRIPT_ARG_ESCAPE_CHAR).execute(
                        script.trim())));
        }

        final Set<Path> executablePaths = new HashSet<>();
        if (!executableType.equals(ExecutableType.NODE)) {
            final Path nodeExecutableParentPath = nodeExecutablePath.getParent();
            if (nodeExecutableParentPath != null) {
                executablePaths.add(nodeExecutableParentPath);
            }
        }

        return new ExecutionSettings(packageJsonDirectoryPath, executablePaths, executable, args);
    }

    private String escapeWhitespacesFromCommandLineToken(Path path) {
        return '"' + path.toString() + '"';
    }
}
