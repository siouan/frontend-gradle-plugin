package org.siouan.frontendgradleplugin.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.process.ExecResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link ScriptRunJob} class.
 */
class ScriptRunJobTest {

    private static final String TASK_NAME = "task";

    private static final String SCRIPT = "script";

    @Mock
    private Task task;

    @Mock
    private Project project;

    @Mock
    private Logger logger;

    @Mock
    private ExecResult result;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(task.getProject()).thenReturn(project);
        when(task.getLogger()).thenReturn(logger);
    }

    @Test
    public void shouldRunScript() {
        final boolean yarnEnabled = true;
        final File nodeInstallDirectory = new File("node");
        final File yarnInstallDirectory = new File("yarn");
        final String script = SCRIPT;
        final ScriptRunJob job = new ScriptRunJob(task, true, nodeInstallDirectory, yarnInstallDirectory, script);
        final ExecResult execResult = mock(ExecResult.class);
        when(project.exec(any(ExecSpecAction.class))).thenReturn(execResult);
        when(execResult.rethrowFailure()).thenReturn(execResult);

        job.run();

        verify(project)
            .exec(argThat(new ExecSpecActionMatcher(yarnEnabled, nodeInstallDirectory, yarnInstallDirectory, script)));
    }

    private static class ExecSpecActionMatcher implements ArgumentMatcher<ExecSpecAction> {

        private final boolean yarnEnabled;

        private final File nodeInstallDirectory;

        private final File yarnInstallDirectory;

        private final String script;

        public ExecSpecActionMatcher(final boolean yarnEnabled, final File nodeInstallDirectory,
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
