package org.siouan.frontendgradleplugin.domain.usecase;

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
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.ScriptRunnerAdapter;

@ExtendWith(MockitoExtension.class)
class ScriptRunnerAdapterTest {

    private static final String SCRIPT = "script";

    @TempDir
    Path temporaryDirectoryPath;

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
        Files.createFile(temporaryDirectoryPath.resolve("node.exe"));
        final Path binDirectory = Files.createDirectory(temporaryDirectoryPath.resolve("bin"));
        Files.createFile(binDirectory.resolve("yarn.cmd"));
        final ExecutableType executableType = ExecutableType.YARN;
        final String script = SCRIPT;
        final ScriptRunnerAdapter job = new ScriptRunnerAdapter(null);
        //final ScriptRunnerAdapter job = new ScriptRunnerAdapter(task, LogLevel.LIFECYCLE, temporaryDirectoryPath,
        //    executableType, temporaryDirectoryPath, temporaryDirectoryPath, script, "Windows NT");
        final ExecResult execResult = mock(ExecResult.class);
        when(project.exec(any(ExecSpecAction.class))).thenReturn(execResult);
        when(execResult.rethrowFailure()).thenReturn(execResult);

        job.run(null);

        verify(project).exec(
            argThat(new ExecSpecActionMatcher(executableType, temporaryDirectoryPath, temporaryDirectoryPath, script)));
    }

    private static class ExecSpecActionMatcher implements ArgumentMatcher<ExecSpecAction> {

        private final ExecutableType executableType;

        private final Path nodeInstallDirectory;

        private final Path yarnInstallDirectory;

        private final String script;

        ExecSpecActionMatcher(final ExecutableType executableType, final Path nodeInstallDirectory,
            final Path yarnInstallDirectory, final String script) {
            this.executableType = executableType;
            this.nodeInstallDirectory = nodeInstallDirectory;
            this.yarnInstallDirectory = yarnInstallDirectory;
            this.script = script;
        }

        @Override
        public boolean matches(final ExecSpecAction action) {
            return (executableType == action.getExecutableType()) && nodeInstallDirectory.equals(
                action.getNodeInstallDirectory()) && yarnInstallDirectory.equals(action.getYarnInstallDirectory())
                && script.equals(action.getScript());
        }
    }
}
