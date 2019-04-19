package org.siouan.frontendgradleplugin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.File;
import java.util.List;

import org.gradle.process.ExecSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siouan.frontendgradleplugin.job.ExecSpecAction;
import org.siouan.frontendgradleplugin.util.CaseInsensitiveStringMatcher;

/**
 * Unit tests for the {@link ExecSpecAction} class.
 */
public class ExecSpecActionTest {

    private static final String SCRIPT = "script";

    private File nodeInstallDirectory;

    private File yarnInstallDirectory;

    private boolean configured;

    @Mock
    private ExecSpec execSpec;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        nodeInstallDirectory = new File("/node");
        yarnInstallDirectory = new File("/yarn");
        configured = false;
    }

    @Test
    public void shouldConfigureWithCmdTermAndNpmWhenWindowsOsAndYarnDisabled() {
        final ExecSpecAction action = new ExecSpecAction(false, nodeInstallDirectory, yarnInstallDirectory, SCRIPT,
            this::afterConfigured, "Windows NT");

        action.execute(execSpec);

        assertThat(configured).isTrue();
        final ArgumentCaptor<String> executableCaptor = ArgumentCaptor.forClass(String.class);
        verify(execSpec).setExecutable(executableCaptor.capture());
        assertThat(executableCaptor.getValue()).isEqualTo(ExecSpecAction.CMD_EXECUTABLE);
        final ArgumentCaptor<List<String>> argsCaptor = ArgumentCaptor.forClass(List.class);
        verify(execSpec).setArgs(argsCaptor.capture());
        assertThat(argsCaptor.getValue()).containsExactly("/c", ExecSpecAction.NPM_EXECUTABLE + ' ' + SCRIPT);
        verify(execSpec).getEnvironment();
        final ArgumentCaptor<Object> pathCaptor = ArgumentCaptor.forClass(Object.class);
        verify(execSpec).environment(argThat(new CaseInsensitiveStringMatcher("path")), pathCaptor.capture());
        assertThat(pathCaptor.getValue().toString()).startsWith(nodeInstallDirectory.getAbsolutePath())
            .doesNotContain(yarnInstallDirectory.getAbsolutePath());
        verifyNoMoreInteractions(execSpec);
    }

    @Test
    public void shouldConfigureWithCmdTermAndYarnWhenWindowsOsAndYarnEnabled() {
        final ExecSpecAction action = new ExecSpecAction(true, nodeInstallDirectory, yarnInstallDirectory, SCRIPT,
            this::afterConfigured, "Windows NT");

        action.execute(execSpec);

        assertThat(configured).isTrue();
        final ArgumentCaptor<String> executableCaptor = ArgumentCaptor.forClass(String.class);
        verify(execSpec).setExecutable(executableCaptor.capture());
        assertThat(executableCaptor.getValue()).isEqualTo(ExecSpecAction.CMD_EXECUTABLE);
        final ArgumentCaptor<List<String>> argsCaptor = ArgumentCaptor.forClass(List.class);
        verify(execSpec).setArgs(argsCaptor.capture());
        assertThat(argsCaptor.getValue()).containsExactly("/c", ExecSpecAction.YARN_EXECUTABLE + ' ' + SCRIPT);
        verify(execSpec).getEnvironment();
        final ArgumentCaptor<Object> pathCaptor = ArgumentCaptor.forClass(Object.class);
        verify(execSpec).environment(argThat(new CaseInsensitiveStringMatcher("path")), pathCaptor.capture());
        assertThat(pathCaptor.getValue().toString()).startsWith(nodeInstallDirectory.getAbsolutePath())
            .contains(yarnInstallDirectory.getAbsolutePath());
        verifyNoMoreInteractions(execSpec);
    }

    private void afterConfigured(final ExecSpec execSpec) {
        configured = true;
    }
}
