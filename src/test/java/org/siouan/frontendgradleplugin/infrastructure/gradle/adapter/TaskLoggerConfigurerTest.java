package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
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

    private static final LogLevel LOGGING_LEVEL = LogLevel.LIFECYCLE;

    @Mock
    private Property<LogLevel> loggingLevelProperty;

    @Mock
    private Task task;

    @Mock
    private BeanRegistry beanRegistry;

    @Mock
    private Logger gradleLogger;

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
        when(extension.getLoggingLevel()).thenReturn(loggingLevelProperty);
        when(loggingLevelProperty.get()).thenReturn(LOGGING_LEVEL);
        final ZeroOrMultiplePublicConstructorsException expectedException = mock(
            ZeroOrMultiplePublicConstructorsException.class);
        when(beanRegistry.getBean(GradleLoggerAdapter.class)).thenThrow(expectedException);

        assertThatThrownBy(() -> taskLoggerConfigurer.beforeExecute(task))
            .isInstanceOf(RuntimeException.class)
            .hasCause(expectedException);

        verifyNoMoreInteractions(adapter, beanRegistry);
    }

    @Test
    void shouldInitLoggerBeforeTaskExecution()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(extension.getLoggingLevel()).thenReturn(loggingLevelProperty);
        when(loggingLevelProperty.get()).thenReturn(LOGGING_LEVEL);
        when(beanRegistry.getBean(GradleLoggerAdapter.class)).thenReturn(adapter);

        taskLoggerConfigurer.beforeExecute(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), anyString());
        verifyNoMoreInteractions(adapter, beanRegistry);
    }

    @Test
    void shouldDoNothingAfterTaskExecution() {
        taskLoggerConfigurer.afterExecute(task, mock(TaskState.class));

        verifyNoMoreInteractions(adapter, beanRegistry);
    }
}
