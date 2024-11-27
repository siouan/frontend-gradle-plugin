package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction.PATH_VARIABLE_NAME;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.gradle.process.ExecSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.ExecutionSettings;

@ExtendWith(MockitoExtension.class)
class ExecSpecActionTest {

    private static final Path WORKING_DIRECTORY_PATH = Paths.get("/home");

    private static final Path EXECUTABLE_FILE_PATH = Paths.get("/opt/executable");

    private static final Path ADDITIONAL_EXECUTABLE_PATH = Paths.get("/bin");

    private static final List<String> ARGUMENTS = List.of("arg1", "argument 2");

    @Mock
    private ExecSpec execSpec;

    @Mock
    private Consumer<ExecSpec> afterConfigured;

    @Test
    void should_configure_exec_spec_with_no_user_environment_and_no_path_override_and_no_additional_executable_paths_and_no_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of())
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of())
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of());

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_no_path_override_and_no_additional_executable_paths_and_no_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of())
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of());

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_path_override_and_no_additional_executable_paths_and_no_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of())
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value", "PATH", "/usr/bin"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of());

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(execSpec).environment(PATH_VARIABLE_NAME, "/usr/bin");
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_no_path_override_and_additional_executable_paths_and_no_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of(ADDITIONAL_EXECUTABLE_PATH))
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of());

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(execSpec).environment(PATH_VARIABLE_NAME, ADDITIONAL_EXECUTABLE_PATH.toString());
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_path_override_and_additional_executable_paths_and_no_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of(ADDITIONAL_EXECUTABLE_PATH))
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value", "PATH", "/usr/bin"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of());

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(execSpec).environment(PATH_VARIABLE_NAME, ADDITIONAL_EXECUTABLE_PATH + File.pathSeparator + "/usr/bin");
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_no_path_override_and_no_additional_executable_paths_and_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of())
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of("Path", "/system/bin"));

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(execSpec).environment(PATH_VARIABLE_NAME, "/system/bin");
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_no_path_override_and_additional_executable_paths_and_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of(EXECUTABLE_FILE_PATH))
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of("Path", "/system/bin"));

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(execSpec).environment(PATH_VARIABLE_NAME, EXECUTABLE_FILE_PATH + File.pathSeparator + "/system/bin");
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }

    @Test
    void should_configure_exec_spec_with_user_environment_and_path_override_and_additional_executable_paths_and_system_path() {
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(WORKING_DIRECTORY_PATH)
            .additionalExecutablePaths(Set.of(EXECUTABLE_FILE_PATH))
            .executablePath(EXECUTABLE_FILE_PATH)
            .arguments(ARGUMENTS)
            .environmentVariables(Map.of("VARIABLE", "value", "PATH", "/usr/bin"))
            .build();
        final ExecSpecAction action = new ExecSpecAction(executionSettings, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Map.of("Path", "/system/bin"));

        action.execute(execSpec);

        verify(execSpec).setWorkingDir(WORKING_DIRECTORY_PATH.toString());
        verify(execSpec).setExecutable(EXECUTABLE_FILE_PATH.toString());
        verify(execSpec).setArgs(ARGUMENTS);
        verify(execSpec).environment(Map.of("VARIABLE", "value"));
        verify(execSpec).environment(PATH_VARIABLE_NAME, EXECUTABLE_FILE_PATH + File.pathSeparator + "/usr/bin");
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(execSpec, afterConfigured);
    }
}
