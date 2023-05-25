package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.stream.Collectors.joining;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;

import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Action;
import org.gradle.process.ExecSpec;
import org.siouan.frontendgradleplugin.domain.ExecutionSettings;

/**
 * Action that configures a {@link ExecSpec} instance to run a script with Gradle.
 */
@Builder
@Getter
public class ExecSpecAction implements Action<ExecSpec> {

    /**
     * Execution settings.
     */
    private final ExecutionSettings executionSettings;

    /**
     * A consumer called once the exec specification has been configured.
     */
    private final Consumer<ExecSpec> afterConfiguredConsumer;

    /**
     * Configures an execute specification to run the script with a npm/Yarn command line.
     *
     * @param execSpec Execute specification.
     */
    @Override
    public void execute(final ExecSpec execSpec) {
        execSpec.setWorkingDir(executionSettings.getWorkingDirectoryPath().toString());

        // Prepend directories containing the Node and Yarn executables to the 'PATH' environment variable.
        // npm is in the same directory than Node, do nothing for it.
        final Map<String, Object> environment = execSpec.getEnvironment();
        final String pathVariable = findPathVariable(environment);
        final String executablePaths = executionSettings
            .getAdditionalExecutablePaths()
            .stream()
            .map(Path::toString)
            .collect(joining(File.pathSeparator));
        final StringBuilder pathValue = new StringBuilder();
        if (!executablePaths.isEmpty()) {
            pathValue.append(executablePaths);
            pathValue.append(File.pathSeparatorChar);
        }
        pathValue.append((String) environment.getOrDefault(pathVariable, ""));

        execSpec.environment(pathVariable, pathValue.toString());
        execSpec.setExecutable(executionSettings.getExecutablePath());
        execSpec.setArgs(executionSettings.getArguments());
        afterConfiguredConsumer.accept(execSpec);
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
