package org.siouan.frontendgradleplugin.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    Path temporaryDirectory;

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
        Files.createFile(temporaryDirectory.resolve("node.exe"));
        final Path binDirectory = Files.createDirectory(temporaryDirectory.resolve("bin"));
        Files.createFile(binDirectory.resolve("yarn.cmd"));
        final Executor executor = Executor.YARN;
        final String script = SCRIPT;
        final RunScriptJob job = new RunScriptJob(task, executor, temporaryDirectory, temporaryDirectory, script,
            "Windows NT");
        final ExecResult execResult = mock(ExecResult.class);
        when(project.exec(any(ExecSpecAction.class))).thenReturn(execResult);
        when(execResult.rethrowFailure()).thenReturn(execResult);

        job.run();

        verify(project)
            .exec(argThat(new ExecSpecActionMatcher(executor, temporaryDirectory, temporaryDirectory, script)));
    }

    private static class ExecSpecActionMatcher implements ArgumentMatcher<ExecSpecAction> {

        private final Executor executor;

        private final Path nodeInstallDirectory;

        private final Path yarnInstallDirectory;

        private final String script;

        ExecSpecActionMatcher(final Executor executor, final Path nodeInstallDirectory, final Path yarnInstallDirectory,
            final String script) {
            this.executor = executor;
            this.nodeInstallDirectory = nodeInstallDirectory;
            this.yarnInstallDirectory = yarnInstallDirectory;
            this.script = script;
        }

        @Override
        public boolean matches(final ExecSpecAction action) {
            return (executor == action.getExecutor()) && nodeInstallDirectory.equals(action.getNodeInstallDirectory())
                && yarnInstallDirectory.equals(action.getYarnInstallDirectory()) && script.equals(action.getScript());
        }
    }
}
