package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.LoggingManager;
import org.gradle.api.provider.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskLoggerConfigurerTest {

    private static final String TASK_NAME = "task";

    private static final LogLevel LOGGING_LEVEL = LogLevel.WARN;

    @Mock
    private Property<Boolean> verboseModeEnabled;

    @Mock
    private Logger gradleLogger;

    @Mock
    private Task task;

    @Mock
    private LoggingManager taskLoggingManager;

    @Mock
    private GradleLoggerAdapter adapter;

    @Mock
    private FrontendExtension extension;

    @Mock
    private GradleSettings gradleSettings;

    @InjectMocks
    private TaskLoggerConfigurer taskLoggerConfigurer;

    @Test
    void should_init_logger_before_task_execution_with_task_level() {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(task.getLogging()).thenReturn(taskLoggingManager);
        when(taskLoggingManager.getLevel()).thenReturn(LOGGING_LEVEL);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(true);

        taskLoggerConfigurer.initLoggerAdapter(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), eq(true), anyString());
        verifyNoMoreInteractions(task, taskLoggingManager, adapter, gradleSettings);
    }

    @Test
    void should_init_logger_before_task_execution_with_project_level() {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(task.getLogging()).thenReturn(taskLoggingManager);
        when(gradleSettings.getProjectLogLevel()).thenReturn(LOGGING_LEVEL);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(true);

        taskLoggerConfigurer.initLoggerAdapter(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), eq(true), anyString());
        verify(taskLoggingManager).getLevel();
        verifyNoMoreInteractions(task, taskLoggingManager, adapter, gradleSettings);
    }

    @Test
    void should_init_logger_before_task_execution_with_gradle_start_level() {
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getLogger()).thenReturn(gradleLogger);
        when(task.getLogging()).thenReturn(taskLoggingManager);
        when(gradleSettings.getProjectLogLevel()).thenReturn(null);
        when(gradleSettings.getCommandLineLogLevel()).thenReturn(LOGGING_LEVEL);
        when(extension.getVerboseModeEnabled()).thenReturn(verboseModeEnabled);
        when(verboseModeEnabled.get()).thenReturn(true);

        taskLoggerConfigurer.initLoggerAdapter(task);

        verify(adapter).init(eq(gradleLogger), eq(LOGGING_LEVEL), eq(true), anyString());
        verify(taskLoggingManager).getLevel();
        verifyNoMoreInteractions(task, taskLoggingManager, adapter, gradleSettings);
    }
}
