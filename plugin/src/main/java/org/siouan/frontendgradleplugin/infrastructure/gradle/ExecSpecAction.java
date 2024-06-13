package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static final String PATH_VARIABLE_NAME = "PATH";

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
        execSpec.setExecutable(executionSettings.getExecutablePath().toString());
        execSpec.setArgs(executionSettings.getArguments());

        final Map<String, String> userEnvironmentVariables = new HashMap<>(executionSettings.getEnvironmentVariables());
        final Map<String, Object> currentEnvironmentVariables = execSpec.getEnvironment();

        // Put all user-defined environment variables except an eventual PATH environment variable which is processed
        // separately.
        final Optional<Map.Entry<String, String>> userPathVariable = userEnvironmentVariables
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(PATH_VARIABLE_NAME))
            .findAny();
        userPathVariable.map(Map.Entry::getKey).ifPresent(userEnvironmentVariables::remove);

        if (!userEnvironmentVariables.isEmpty()) {
            execSpec.environment(userEnvironmentVariables);
        }

        // If the user overrides the 'PATH' environment variable, it takes precedence over the current value in the
        // environment. Otherwise, the original 'PATH' is appended, if any.
        final Optional<Map.Entry<String, Object>> systemPathVariable = currentEnvironmentVariables
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(PATH_VARIABLE_NAME))
            .findAny();
        // Let's prepare the value of the PATH variable by using the eventual override from the user, or the system
        // value.
        final Optional<String> basePathVariableValue = userPathVariable
            .map(Map.Entry::getValue)
            .or(() -> systemPathVariable.map(Map.Entry::getValue).map(Objects::toString));
        // Prepare directories containing executables to be prepended to the 'PATH' environment variable.
        final Stream.Builder<String> pathVariableBuilder = Stream.builder();
        final Set<Path> additionalExecutablePaths = executionSettings.getAdditionalExecutablePaths();
        if (!additionalExecutablePaths.isEmpty() || basePathVariableValue.isPresent()) {
            additionalExecutablePaths.forEach(
                additionalExecutablePath -> pathVariableBuilder.accept(additionalExecutablePath.toString()));
            basePathVariableValue.ifPresent(pathVariableBuilder);
            execSpec.environment(PATH_VARIABLE_NAME,
                pathVariableBuilder.build().collect(Collectors.joining(File.pathSeparator)));
        }

        afterConfiguredConsumer.accept(execSpec);
    }
}
