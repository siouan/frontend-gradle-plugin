package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.stream.Collectors.joining;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

import org.gradle.api.Action;
import org.gradle.process.ExecSpec;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;

/**
 * Action that configures a {@link ExecSpec} instance to run a frontend script (Node/NPM/Yarn) with Gradle.
 */
public class ExecSpecAction implements Action<ExecSpec> {

    private final ExecutionSettings executionSettings;

    /**
     * A consumer called once the exec specification has been configured.
     */
    private final Consumer<ExecSpec> afterConfiguredConsumer;

    /**
     * Builds an action to execute the given settings, and call the consumer after configuration.
     *
     * @param executionSettings Execution settings.
     * @param afterConfiguredConsumer Consumer called after the action is configured.
     */
    public ExecSpecAction(@Nonnull final ExecutionSettings executionSettings,
        @Nonnull final Consumer<ExecSpec> afterConfiguredConsumer) {
        this.executionSettings = executionSettings;
        this.afterConfiguredConsumer = afterConfiguredConsumer;
    }

    public ExecutionSettings getExecutionSettings() {
        return executionSettings;
    }

    public Consumer<ExecSpec> getAfterConfiguredConsumer() {
        return afterConfiguredConsumer;
    }

    /**
     * Configures an execute specification to run the script with a NPM/Yarn command line.
     *
     * @param execSpec Execute specification.
     */
    @Override
    public void execute(@Nonnull final ExecSpec execSpec) {
        execSpec.setWorkingDir(executionSettings.getWorkingDirectoryPath().toString());

        // Prepend directories containing the Node and Yarn executables to the 'PATH' environment variable.
        // NPM is in the same directory than Node, do nothing for it.
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
