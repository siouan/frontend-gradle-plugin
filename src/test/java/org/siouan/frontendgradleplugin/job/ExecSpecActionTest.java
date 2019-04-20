package org.siouan.frontendgradleplugin.job;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.gradle.process.ExecSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link ExecSpecAction} class.
 */
public class ExecSpecActionTest {

    private static final String SCRIPT = " run script ";

    private static final File NODE_INSTALL_DIRECTORY = new File("/usr/lib/node");

    private static final File YARN_INSTALL_DIRECTORY = new File("/usr/lib/yarn");

    private static final String NODE_INSTALL_PATH = NODE_INSTALL_DIRECTORY.getAbsolutePath() + File.pathSeparatorChar;

    private static final String YARN_INSTALL_PATH =
        YARN_INSTALL_DIRECTORY.getAbsolutePath() + File.separatorChar + "bin" + File.pathSeparatorChar;

    private static final String PATH_ENVIRONMENT = "/usr/bin:/usr/lib";

    @TempDir
    protected File temporaryDirectory;

    @Mock
    private ExecSpec execSpec;

    @Mock
    private Consumer<ExecSpec> afterConfigured;

    @Captor
    private ArgumentCaptor<List<String>> argsCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConfigureNpmCommandWithWindowsCmd() {
        final String script = SCRIPT;
        final String pathEnvironment = PATH_ENVIRONMENT;
        final ExecSpecAction action = new ExecSpecAction(false, NODE_INSTALL_DIRECTORY, YARN_INSTALL_DIRECTORY, script,
            afterConfigured, "Windows NT");
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", pathEnvironment));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add("/c");
        expectedArgs.add(String.join(" ", ExecSpecAction.NPM_EXECUTABLE, script.trim()));
        assertExecSpecWith(ExecSpecAction.CMD_EXECUTABLE, expectedArgs, pathEnvironment, true, false);
    }

    @Test
    public void shouldConfigureYarnCommandWithWindowsCmd() {
        final String script = SCRIPT;
        final String pathEnvironment = PATH_ENVIRONMENT;
        final ExecSpecAction action = new ExecSpecAction(true, NODE_INSTALL_DIRECTORY, YARN_INSTALL_DIRECTORY, script,
            afterConfigured, "Windows NT");
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("PATH", pathEnvironment));

        action.execute(execSpec);

        final List<String> expectedArgs = new ArrayList<>();
        expectedArgs.add("/c");
        expectedArgs.add(String.join(" ", ExecSpecAction.YARN_EXECUTABLE, script.trim()));
        assertExecSpecWith(ExecSpecAction.CMD_EXECUTABLE, expectedArgs, pathEnvironment, true, true);
    }

    @Test
    public void shouldConfigureNpmCommandWithUnixShell() {
        final String script = SCRIPT;
        final String pathEnvironment = PATH_ENVIRONMENT;
        final ExecSpecAction action = new ExecSpecAction(false, NODE_INSTALL_DIRECTORY, YARN_INSTALL_DIRECTORY, script,
            afterConfigured, "Linux");
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("Path", pathEnvironment));

        action.execute(execSpec);

        assertExecSpecWith(ExecSpecAction.NPM_EXECUTABLE, Arrays.asList(script.trim().split("\\s+")), pathEnvironment,
            true, false);
    }

    @Test
    public void shouldConfigureYarnCommandWithUnixShell() {
        final String script = SCRIPT;
        final String pathEnvironment = PATH_ENVIRONMENT;
        final ExecSpecAction action = new ExecSpecAction(true, NODE_INSTALL_DIRECTORY, YARN_INSTALL_DIRECTORY, script,
            afterConfigured, "Mac OS X");
        when(execSpec.getEnvironment()).thenReturn(Collections.singletonMap("PATH", pathEnvironment));

        action.execute(execSpec);

        assertExecSpecWith(ExecSpecAction.YARN_EXECUTABLE, Arrays.asList(script.trim().split("\\s+")), pathEnvironment,
            true, true);
    }

    private void assertExecSpecWith(final String expectedExecutable, final List<String> expectedArgs,
        final String initialPathEnvironment, final boolean nodeInstallPathIncluded,
        final boolean yarnInstallPathIncluded) {
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
        assertThat(pathValue).contains(initialPathEnvironment);
        if (nodeInstallPathIncluded) {
            assertThat(pathValue).contains(NODE_INSTALL_PATH);
        } else {
            assertThat(pathValue).doesNotContain(NODE_INSTALL_PATH);
        }
        if (yarnInstallPathIncluded) {
            assertThat(pathValue).contains(YARN_INSTALL_PATH);
        } else {
            assertThat(pathValue).doesNotContain(YARN_INSTALL_PATH);
        }
        verifyNoMoreInteractions(execSpec);
        verify(afterConfigured).accept(execSpec);
        verifyNoMoreInteractions(afterConfigured);
    }
}
