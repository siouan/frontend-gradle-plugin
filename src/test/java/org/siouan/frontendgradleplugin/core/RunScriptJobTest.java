package org.siouan.frontendgradleplugin.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.process.ExecResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link RunScriptJob} class.
 */
@ExtendWith(MockitoExtension.class)
class RunScriptJobTest {

    private static final String SCRIPT = "script";

    @TempDir
    File temporaryDirectory;

    @Mock
    private Task task;

    @Mock
    private Project project;

    @BeforeEach
    void setUp() {
        when(task.getProject()).thenReturn(project);
    }

    @Test
    void shouldRunScript() throws ExecutableNotFoundException, IOException {
        Files.createFile(temporaryDirectory.toPath().resolve("node.exe"));
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        Files.createFile(binDirectory.resolve("yarn.cmd"));
        final boolean yarnEnabled = true;
        final String script = SCRIPT;
        final RunScriptJob job = new RunScriptJob(task, true, temporaryDirectory, temporaryDirectory, script,
            "Windows NT");
        final ExecResult execResult = mock(ExecResult.class);
        when(project.exec(any(ExecSpecAction.class))).thenReturn(execResult);
        when(execResult.rethrowFailure()).thenReturn(execResult);

        job.run();

        verify(project)
            .exec(argThat(new ExecSpecActionMatcher(yarnEnabled, temporaryDirectory, temporaryDirectory, script)));
    }

    private static class ExecSpecActionMatcher implements ArgumentMatcher<ExecSpecAction> {

        private final boolean yarnEnabled;

        private final File nodeInstallDirectory;

        private final File yarnInstallDirectory;

        private final String script;

        ExecSpecActionMatcher(final boolean yarnEnabled, final File nodeInstallDirectory,
            final File yarnInstallDirectory, final String script) {
            this.yarnEnabled = yarnEnabled;
            this.nodeInstallDirectory = nodeInstallDirectory;
            this.yarnInstallDirectory = yarnInstallDirectory;
            this.script = script;
        }

        @Override
        public boolean matches(final ExecSpecAction action) {
            return (yarnEnabled == action.isYarnEnabled()) && nodeInstallDirectory
                .equals(action.getNodeInstallDirectory()) && yarnInstallDirectory
                .equals(action.getYarnInstallDirectory()) && script.equals(action.getScript());
        }
    }
}
