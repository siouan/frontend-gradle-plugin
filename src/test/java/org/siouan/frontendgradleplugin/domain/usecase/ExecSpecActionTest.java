package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.gradle.process.ExecSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction;

@ExtendWith(MockitoExtension.class)
class ExecSpecActionTest {

    private static final String SCRIPT = " run script ";

    private static final String PATH_ENVIRONMENT = "/usr/bin:/usr/lib";

    @TempDir
    Path temporaryDirectory;

    @Mock
    private ExecSpec execSpec;

    @Mock
    private Consumer<ExecSpec> afterConfigured;

    @Captor
    private ArgumentCaptor<List<String>> argsCaptor;

    private Path workingDirectory;

    private Path nodeInstallDirectory;

    private Path yarnInstallDirectory;

    @BeforeEach
    void setUp() throws IOException {
        workingDirectory = temporaryDirectory;
        nodeInstallDirectory = Files.createDirectory(temporaryDirectory.resolve("node"));
        yarnInstallDirectory = Files.createDirectory(temporaryDirectory.resolve("yarn"));
    }

    @Test
    void shouldFailBuildingActionWhenNodeExecutableCannotBeFound() {
        assertThatThrownBy(
            () -> new ExecSpecAction(workingDirectory, ExecutableType.NODE, nodeInstallDirectory, null, new Platform(null, ""), SCRIPT,
                afterConfigured))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableNotFoundException.NODE);
    }

    @Test
    void shouldFailBuildingActionWhenNpmExecutableCannotBeFound() throws IOException {
        Files.createFile(nodeInstallDirectory.resolve("node.exe"));
        assertThatThrownBy(
            () -> new ExecSpecAction(workingDirectory, ExecutableType.NPM, nodeInstallDirectory, null, new Platform(null, "Windows NT"),
                SCRIPT, afterConfigured))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableNotFoundException.NPM);
    }

    @Test
    void shouldFailBuildingActionWhenYarnExecutableCannotBeFound() throws IOException {
        Files.createFile(Files.createDirectory(nodeInstallDirectory.resolve("bin")).resolve("node"));
        assertThatThrownBy(
            () -> new ExecSpecAction(workingDirectory, ExecutableType.YARN, nodeInstallDirectory, yarnInstallDirectory,
                new Platform(null, "Mac OS X"), SCRIPT, afterConfigured))
            .isInstanceOf(ExecutableNotFoundException.class)
            .hasMessage(ExecutableNotFoundException.YARN);
    }

    @Test
    void shouldConfigureNodeCommandWithWindowsCmd() throws ExecutableNotFoundException, IOException {
        final Path nodeExecutable = Files.createFile(nodeInstallDirectory.resolve("node.exe"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(workingDirectory, ExecutableType.NODE, nodeInstallDirectory,
            null, new Platform(null, "Windows NT"), script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add(ExecSpecAction.CMD_RUN_EXIT_FLAG);
        expectedArgs.add(String.join(" ", '"' + nodeExecutable.toString() + '"', script.trim()));
        assertExecSpecWith(workingDirectory, ExecSpecAction.CMD_EXECUTABLE, expectedArgs, nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureNpmCommandWithWindowsCmd() throws ExecutableNotFoundException, IOException {
        final Path nodeExecutable = Files.createFile(nodeInstallDirectory.resolve("node.exe"));
        final Path npmExecutable = Files.createFile(nodeInstallDirectory.resolve("npm.cmd"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(workingDirectory, ExecutableType.NPM, nodeInstallDirectory,
            null, new Platform(null, "Windows NT"), script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add(ExecSpecAction.CMD_RUN_EXIT_FLAG);
        expectedArgs.add(String.join(" ", '"' + npmExecutable.toString() + '"', script.trim()));
        assertExecSpecWith(workingDirectory, ExecSpecAction.CMD_EXECUTABLE, expectedArgs, nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureYarnCommandWithWindowsCmd() throws ExecutableNotFoundException, IOException {
        final Path nodeExecutable = Files.createFile(nodeInstallDirectory.resolve("node.exe"));
        final Path binDirectory = Files.createDirectory(yarnInstallDirectory.resolve("bin"));
        final Path yarnExecutable = Files.createFile(binDirectory.resolve("yarn.cmd"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(temporaryDirectory, ExecutableType.YARN, nodeInstallDirectory,
            yarnInstallDirectory, new Platform(null, "Windows NT"), script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("PATH", PATH_ENVIRONMENT));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add(ExecSpecAction.CMD_RUN_EXIT_FLAG);
        expectedArgs.add(String.join(" ", '"' + yarnExecutable.toString() + '"', script.trim()));
        assertExecSpecWith(temporaryDirectory, ExecSpecAction.CMD_EXECUTABLE, expectedArgs, nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureNodeCommandWithUnixShell() throws ExecutableNotFoundException, IOException {
        final Path binDirectory = Files.createDirectory(nodeInstallDirectory.resolve("bin"));
        final Path nodeExecutable = Files.createFile(binDirectory.resolve("node"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(temporaryDirectory, ExecutableType.NODE, nodeInstallDirectory,
            null, new Platform(null, "Linux"), script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        assertExecSpecWith(temporaryDirectory, nodeExecutable.toString(), asList(script.trim().split("\\s+")),
            nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureNpmCommandWithUnixShell() throws ExecutableNotFoundException, IOException {
        final Path binDirectory = Files.createDirectory(nodeInstallDirectory.resolve("bin"));
        final Path nodeExecutable = Files.createFile(binDirectory.resolve("node"));
        final Path npmExecutable = Files.createFile(binDirectory.resolve("npm"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(temporaryDirectory, ExecutableType.NPM, nodeInstallDirectory,
            null, new Platform(null, "Linux"), script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", PATH_ENVIRONMENT));

        action.execute(execSpec);

        assertExecSpecWith(temporaryDirectory, npmExecutable.toString(), asList(script.trim().split("\\s+")),
            nodeExecutable.getParent());
    }

    @Test
    void shouldConfigureYarnCommandWithUnixShell() throws ExecutableNotFoundException, IOException {
        final Path nodeBinDirectory = Files.createDirectory(nodeInstallDirectory.resolve("bin"));
        Files.createFile(nodeBinDirectory.resolve("node"));
        final Path yarnBinDirectory = Files.createDirectory(yarnInstallDirectory.resolve("bin"));
        final Path yarnExecutable = Files.createFile(yarnBinDirectory.resolve("yarn"));
        final String script = SCRIPT;
        final ExecSpecAction action = new ExecSpecAction(temporaryDirectory, ExecutableType.YARN, nodeInstallDirectory,
            yarnInstallDirectory, new Platform(null, "Mac OS X"), script, afterConfigured);
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("PATH", PATH_ENVIRONMENT));

        action.execute(execSpec);

        assertExecSpecWith(temporaryDirectory, yarnExecutable.toString(), asList(script.trim().split("\\s+")),
            yarnBinDirectory.getParent());
    }

    private void assertExecSpecWith(final Path expectedWorkingDirectory, final String expectedExecutable,
        final List<String> expectedArgs, final Path expectedExecutableDirectory) {
        verify(execSpec).setWorkingDir(expectedWorkingDirectory);
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
        assertThat(pathValue).contains(expectedExecutableDirectory.toString());
        verifyNoMoreInteractions(execSpec);
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(afterConfigured);
    }
}
