package org.siouan.frontendgradleplugin.core;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.gradle.process.ExecSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link ExecSpecAction} class.
 */
@ExtendWith(MockitoExtension.class)
class ExecSpecActionTest {

    private static final String SCRIPT = " run script ";

    private static final String PATH_ENVIRONMENT = "/usr/bin:/usr/lib";

    @TempDir
    File temporaryDirectory;

    @Mock
    private ExecSpec execSpec;

    @Mock
    private Consumer<ExecSpec> afterConfigured;

    @Captor
    private ArgumentCaptor<List<String>> argsCaptor;

    @Test
    void shouldFailBuildingActionWhenNodeExecutableCannotBeFound() {
        assertThatThrownBy(() -> new ExecSpecAction(Executor.NODE, temporaryDirectory, temporaryDirectory, "", SCRIPT,
            afterConfigured)).isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableNotFoundException.NODE);
    }

    @Test
    void shouldFailBuildingActionWhenNpmExecutableCannotBeFound() throws IOException {
        Files.createFile(temporaryDirectory.toPath().resolve("node.exe"));
        assertThatThrownBy(
            () -> new ExecSpecAction(Executor.NPM, temporaryDirectory, temporaryDirectory, "Windows NT", SCRIPT,
                afterConfigured)).isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableNotFoundException.NPM);
    }

    @Test
    void shouldFailBuildingActionWhenYarnExecutableCannotBeFound() throws IOException {
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        Files.createFile(binDirectory.resolve("node"));
        assertThatThrownBy(
            () -> new ExecSpecAction(Executor.YARN, temporaryDirectory, temporaryDirectory, "Mac OS X", SCRIPT,
                afterConfigured)).isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableNotFoundException.YARN);
    }

    @Test
    void shouldConfigureNodeCommandWithWindowsCmd() throws ExecutableNotFoundException, IOException {
        final Path nodeExecutable = Files.createFile(temporaryDirectory.toPath().resolve("node.exe"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(Executor.NODE, temporaryDirectory, temporaryDirectory,
            "Windows NT", script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add(ExecSpecAction.CMD_RUN_EXIT_FLAG);
        expectedArgs.add(String.join(" ", '"' + nodeExecutable.toString() + '"', script.trim()));
        assertExecSpecWith(ExecSpecAction.CMD_EXECUTABLE, expectedArgs, nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureNpmCommandWithWindowsCmd() throws ExecutableNotFoundException, IOException {
        final Path nodeExecutable = Files.createFile(temporaryDirectory.toPath().resolve("node.exe"));
        final Path npmExecutable = Files.createFile(temporaryDirectory.toPath().resolve("npm.cmd"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(Executor.NPM, temporaryDirectory, temporaryDirectory,
            "Windows NT", script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add(ExecSpecAction.CMD_RUN_EXIT_FLAG);
        expectedArgs.add(String.join(" ", '"' + npmExecutable.toString() + '"', script.trim()));
        assertExecSpecWith(ExecSpecAction.CMD_EXECUTABLE, expectedArgs, nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureYarnCommandWithWindowsCmd() throws ExecutableNotFoundException, IOException {
        final Path nodeExecutable = Files.createFile(temporaryDirectory.toPath().resolve("node.exe"));
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        final Path yarnExecutable = Files.createFile(binDirectory.resolve("yarn.cmd"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(Executor.YARN, temporaryDirectory, temporaryDirectory,
            "Windows NT", script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("PATH", PATH_ENVIRONMENT));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add(ExecSpecAction.CMD_RUN_EXIT_FLAG);
        expectedArgs.add(String.join(" ", '"' + yarnExecutable.toString() + '"', script.trim()));
        assertExecSpecWith(ExecSpecAction.CMD_EXECUTABLE, expectedArgs, nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureNodeCommandWithUnixShell() throws ExecutableNotFoundException, IOException {
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        final Path nodeExecutable = Files.createFile(binDirectory.resolve("node"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(Executor.NODE, temporaryDirectory, temporaryDirectory, "Linux",
            script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        assertExecSpecWith(nodeExecutable.toString(), asList(script.trim().split("\\s+")), nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureNpmCommandWithUnixShell() throws ExecutableNotFoundException, IOException {
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        final Path nodeExecutable = Files.createFile(binDirectory.resolve("node"));
        final Path npmExecutable = Files.createFile(binDirectory.resolve("npm"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(Executor.NPM, temporaryDirectory, temporaryDirectory, "Linux",
            script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        assertExecSpecWith(npmExecutable.toString(), asList(script.trim().split("\\s+")), nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureYarnCommandWithUnixShell() throws ExecutableNotFoundException, IOException {
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        final Path nodeExecutable = Files.createFile(binDirectory.resolve("node"));
        final Path yarnExecutable = Files.createFile(binDirectory.resolve("yarn"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(Executor.YARN, temporaryDirectory, temporaryDirectory,
            "Mac OS X", script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("PATH", PATH_ENVIRONMENT));

        action.execute(execSpec);

        assertExecSpecWith(yarnExecutable.toString(), asList(script.trim().split("\\s+")), nodeExecutable.getParent());
    }

    private void assertExecSpecWith(final String expectedExecutable, final List<String> expectedArgs,
        final Path nodeExecutableDirectory) {
        verify(execSpec).setExecutable(expectedExecutable);
        verify(execSpec).setArgs(argsCaptor.capture());
        final List<String> args = argsCaptor.getValue();
        assertThat(args).isEqualTo(expectedArgs);
        final ArgumentCaptor<String> pathVariableCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Object> pathValueCaptor = ArgumentCaptor.forClass(Object.class);
        verify(execSpec).getEnvironment();
        verify(execSpec).environment(pathVariableCaptor.capture(), pathValueCaptor.capture());
        assertThat(pathVariableCaptor.getValue().toLowerCase()).isEqualTo("path");
        final String pathValue = pathValueCaptor.getValue().toString();
        assertThat(pathValue).contains(PATH_ENVIRONMENT);
        assertThat(pathValue).contains(nodeExecutableDirectory.toString());
        verifyNoMoreInteractions(execSpec);
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(afterConfigured);
    }
}
