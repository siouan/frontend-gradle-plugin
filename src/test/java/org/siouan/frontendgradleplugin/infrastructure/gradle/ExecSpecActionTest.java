package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.gradle.process.ExecSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;

@ExtendWith(MockitoExtension.class)
class ExecSpecActionTest {

    private static final Path WORKING_DIRECTORY_PATH = Paths.get("/tmp");

    private static final Path EXECUTABLE_DIRECTORY_PATH = WORKING_DIRECTORY_PATH.resolve("bin");

    private static final Path EXECUTABLE_FILE_PATH = EXECUTABLE_DIRECTORY_PATH.resolve("executable");

    private static final List<String> EXECUTABLE_PATHS = asList("/usr/bin", "/usr/lib");

    private static final List<String> ARGUMENTS = asList("arg1", "argument 2");

    @Mock
    private ExecSpec execSpec;

    @Mock
    private Consumer<ExecSpec> afterConfigured;

    @Test
    void shouldConfigureExecSpecWithUppercasePathVariableAndWithoutExecutablePaths() {
        final ExecutionSettings executionSettings = new ExecutionSettings(WORKING_DIRECTORY_PATH, emptySet(),
            EXECUTABLE_FILE_PATH, ARGUMENTS);
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(
            singletonMap("PATH", String.join(File.pathSeparator, EXECUTABLE_PATHS)));

        action.execute(execSpec);

        assertExecSpecWith(executionSettings);
    }

    @Test
    void shouldConfigureExecSpecWithLowercasePathVariableAndExecutablePaths() {
        final Set<Path> executablePaths = new HashSet<>();
        executablePaths.add(Paths.get("\\Program Files\\node\\bin"));
        executablePaths.add(Paths.get("/opt/yarn"));
        final ExecutionSettings executionSettings = new ExecutionSettings(WORKING_DIRECTORY_PATH, executablePaths,
            EXECUTABLE_FILE_PATH, ARGUMENTS);
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(
            singletonMap("Path", String.join(File.pathSeparator, EXECUTABLE_PATHS)));

        action.execute(execSpec);

        assertExecSpecWith(executionSettings);
    }

    private void assertExecSpecWith(final ExecutionSettings executionSettings) {
        verify(execSpec).setWorkingDir(executionSettings.getWorkingDirectoryPath().toString());
        verify(execSpec).setExecutable(executionSettings.getExecutablePath());
        verify(execSpec).setArgs(executionSettings.getArguments());
        final ArgumentCaptor<String> pathVariableCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Object> pathValueCaptor = ArgumentCaptor.forClass(Object.class);
        verify(execSpec).environment(pathVariableCaptor.capture(), pathValueCaptor.capture());
        assertThat(pathVariableCaptor.getValue().toLowerCase()).isEqualTo("path");
        final String pathValue = pathValueCaptor.getValue().toString();
        final List<String> expectedPaths = executionSettings
            .getAdditionalExecutablePaths()
            .stream()
            .map(Path::toString)
            .collect(toCollection(ArrayList::new));
        expectedPaths.add("/usr/bin");
        expectedPaths.add("/usr/lib");
        assertThat(pathValue.split(File.pathSeparator)).containsExactlyElementsOf(expectedPaths);
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }
}
