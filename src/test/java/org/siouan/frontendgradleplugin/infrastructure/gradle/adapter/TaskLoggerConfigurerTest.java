package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gradle.StartParameter;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.LoggingManager;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;
import org.siouan.frontendgradleplugin.infrastructure.gradle.FrontendExtension;
import org.siouan.frontendgradleplugin.infrastructure.gradle.TaskLoggerConfigurer;

@ExtendWith(MockitoExtension.class)
class TaskLoggerConfigurerTest {

    private static final String TASK_NAME = "task";

    private static final LogLevel LOGGING_LEVEL = LogLevel.WARN;

    @Mock
    private Property<Boolean> verboseModeEnabled;

    @Mock
    private BeanRegistry beanRegistry;

    @Mock
    private Logger gradleLogger;

    @Mock
    private Task task;

    @Mock
    private LoggingManager taskLoggingManager;

    @Mock
    private LoggingManager projectLoggingManager;

    @Mock
    private Project project;

    @Mock
    private Gradle gradle;

    @Mock
    private GradleLoggerAdapter adapter;

    @Mock
    private FrontendExtension extension;

    private TaskLoggerConfigurer taskLoggerConfigurer;

    @BeforeEach
    void setUp() {
        taskLoggerConfigurer = new TaskLoggerConfigurer(beanRegistry, extension);
    }

    @Test
    void shouldFailBeforeTaskExecutionWhenLoggerIsNotInstanciable()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(false);
        final ZeroOrMultiplePublicConstructorsException expectedException = mock(
            ZeroOrMultiplePublicConstructorsException.class);
        when(beanRegistry.getBean(GradleLoggerAdapter.class)).thenThrow(expectedException);

        assertThatThrownBy(() -> taskLoggerConfigurer.beforeExecute(task))
            .isInstanceOf(RuntimeException.class)
            .hasCause(expectedException);

        verifyNoMoreInteractions(beanRegistry, task, taskLoggingManager, project, projectLoggingManager, gradle,
            adapter);
    }

    @Test
    void shouldInitLoggerBeforeTaskExecutionWithTaskLevel()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(task.getLogging()).thenReturn(taskLoggingManager);
        when(taskLoggingManager.getLevel()).thenReturn(LOGGING_LEVEL);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(true);
        when(beanRegistry.getBean(GradleLoggerAdapter.class)).thenReturn(adapter);

        taskLoggerConfigurer.beforeExecute(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), eq(true), anyString());
        verifyNoMoreInteractions(beanRegistry, task, taskLoggingManager, project, projectLoggingManager, gradle,
            adapter);
    }

    @Test
    void shouldInitLoggerBeforeTaskExecutionWithProjectLevel()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(task.getLogging()).thenReturn(taskLoggingManager);
        when(task.getProject()).thenReturn(project);
        when(project.getLogging()).thenReturn(projectLoggingManager);
        when(projectLoggingManager.getLevel()).thenReturn(LOGGING_LEVEL);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(true);
        when(beanRegistry.getBean(GradleLoggerAdapter.class)).thenReturn(adapter);

        taskLoggerConfigurer.beforeExecute(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), eq(true), anyString());
        verify(taskLoggingManager).getLevel();
        verifyNoMoreInteractions(beanRegistry, task, taskLoggingManager, project, projectLoggingManager, gradle,
            adapter);
    }

    @Test
    void shouldInitLoggerBeforeTaskExecutionWithGradleStartLevel()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(task.getLogging()).thenReturn(taskLoggingManager);
        when(task.getProject()).thenReturn(project);
        when(project.getLogging()).thenReturn(projectLoggingManager);
        when(project.getGradle()).thenReturn(gradle);
        final StartParameter startParameter = new StartParameter();
        startParameter.setLogLevel(LOGGING_LEVEL);
        when(gradle.getStartParameter()).thenReturn(startParameter);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(true);
        when(beanRegistry.getBean(GradleLoggerAdapter.class)).thenReturn(adapter);

        taskLoggerConfigurer.beforeExecute(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), eq(true), anyString());
        verify(taskLoggingManager).getLevel();
        verify(projectLoggingManager).getLevel();
        verifyNoMoreInteractions(beanRegistry, task, taskLoggingManager, project, projectLoggingManager, gradle,
            adapter);
    }

    @Test
    void shouldDoNothingAfterTaskExecution() {
        taskLoggerConfigurer.afterExecute(task, mock(TaskState.class));

        verifyNoMoreInteractions(beanRegistry, task, taskLoggingManager, project, projectLoggingManager, gradle,
            adapter);
    }
}
