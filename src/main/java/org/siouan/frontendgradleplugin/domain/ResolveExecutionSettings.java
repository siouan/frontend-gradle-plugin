package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

/**
 * Resolves execution settings to run a script.
 *
 * @since 2.0.0
 */
@RequiredArgsConstructor
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

    private final ResolveGlobalNodeExecutablePath getNodeExecutablePath;

    private final GetExecutablePath getExecutablePath;

    /**
     * Resolves execution settings to run the given script with a node/npm/npx/yarn executable.
     *
     * @param command Command providing parameters.
     * @return Appropriate settings to run the script on the given platform.
     * @see ExecutableType
     */
    public ExecutionSettings execute(final ResolveExecutionSettingsCommand command) {
        final Path nodeExecutablePath = getNodeExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(command.getNodeInstallDirectoryPath())
            .platform(command.getPlatform())
            .build());
        final Path executablePath = getExecutablePath.execute(
            new GetExecutablePathCommand(command.getExecutableType(), command.getNodeInstallDirectoryPath(),
                command.getPlatform()));

        final Path executable;
        final List<String> args = new ArrayList<>();
        if (command.getPlatform().isWindowsOs()) {
            executable = WINDOWS_EXECUTABLE_PATH;
            args.add(WINDOWS_EXECUTABLE_AUTOEXIT_FLAG);
            // The command that must be executed in the terminal must be a single argument on itself (like if it was
            // quoted).
            args.add(escapeWhitespacesFromCommandLineToken(executablePath) + " " + command.getScript().trim());
        } else {
            executable = UNIX_EXECUTABLE_PATH;
            args.add(UNIX_EXECUTABLE_AUTOEXIT_FLAG);
            args.add(
                escapeWhitespacesFromCommandLineToken(executablePath) + UNIX_SCRIPT_ARG_SEPARATOR_CHAR + String.join(
                    Character.toString(UNIX_SCRIPT_ARG_SEPARATOR_CHAR),
                    new StringSplitter(UNIX_SCRIPT_ARG_SEPARATOR_CHAR, UNIX_SCRIPT_ARG_ESCAPE_CHAR).execute(
                        command.getScript().trim())));
        }

        final Set<Path> executablePaths = new HashSet<>();
        if (command.getExecutableType() != ExecutableType.NODE) {
            final Path nodeExecutableParentPath = nodeExecutablePath.getParent();
            if (nodeExecutableParentPath != null) {
                executablePaths.add(nodeExecutableParentPath);
            }
        }

        return new ExecutionSettings(command.getPackageJsonDirectoryPath(), executablePaths, executable, args);
    }

    private String escapeWhitespacesFromCommandLineToken(Path path) {
        return '"' + path.toString() + '"';
    }
}
