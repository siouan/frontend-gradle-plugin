package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gradle.api.logging.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GradleLoggerAdapterTest {

    private static final LogLevel LOGGING_LEVEL = LogLevel.WARN;

    private static final String MESSAGE = "{} message {}";

    private static final Object PARAMETER_1 = new Object();

    private static final Object PARAMETER_2 = new Object();

    private static final String PREFIX = "prefix";

    @Mock
    private org.gradle.api.logging.Logger logger;

    private GradleLoggerAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new GradleLoggerAdapter();
    }

    @Test
    void should_not_log_debug_message_when_not_initialized() {
        adapter.debug(MESSAGE, PARAMETER_1, PARAMETER_2);

        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_not_log_debug_message_when_not_level_not_enabled() {
        adapter.init(logger, LOGGING_LEVEL, false, PREFIX);
        when(logger.isDebugEnabled()).thenReturn(false);

        adapter.debug(MESSAGE, PARAMETER_1, PARAMETER_2);

        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_delegate_debug_logging_to_gradle_logger() {
        adapter.init(logger, LOGGING_LEVEL, false, PREFIX);
        when(logger.isDebugEnabled()).thenReturn(true);

        adapter.debug(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).debug(PREFIX + MESSAGE, new Object[] {PARAMETER_1, PARAMETER_2});
        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_not_log_info_message_when_not_initialized() {
        adapter.info(MESSAGE, PARAMETER_1, PARAMETER_2);

        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_delegate_info_logging_to_gradle_logger_with_same_level_when_verbose_mode_is_not_enabled() {
        adapter.init(logger, LOGGING_LEVEL, false, PREFIX);

        adapter.info(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).log(LogLevel.INFO, PREFIX + MESSAGE, PARAMETER_1, PARAMETER_2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_delegate_info_logging_to_gradle_logger_with_current_level_when_verbose_mode_is_enabled() {
        adapter.init(logger, LOGGING_LEVEL, true, PREFIX);

        adapter.info(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).log(LOGGING_LEVEL, PREFIX + MESSAGE, PARAMETER_1, PARAMETER_2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_not_log_warn_message_when_not_initialized() {
        adapter.warn(MESSAGE, PARAMETER_1, PARAMETER_2);

        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_delegate_warn_logging_to_gradle_logger_with_same_level_when_verbose_mode_is_not_enabled() {
        adapter.init(logger, LOGGING_LEVEL, false, PREFIX);

        adapter.warn(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).log(LogLevel.WARN, PREFIX + MESSAGE, PARAMETER_1, PARAMETER_2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_delegate_warn_logging_to_gradle_logger_with_current_level_when_verbose_mode_is_enabled() {
        adapter.init(logger, LOGGING_LEVEL, true, PREFIX);

        adapter.warn(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).log(LOGGING_LEVEL, PREFIX + MESSAGE, PARAMETER_1, PARAMETER_2);
        verifyNoMoreInteractions(logger);
    }

    @Test
    void should_log_message_without_prefix() {
        adapter.init(logger, LOGGING_LEVEL, false, null);
        when(logger.isDebugEnabled()).thenReturn(true);

        adapter.debug(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).debug(MESSAGE, new Object[] {PARAMETER_1, PARAMETER_2});
        verifyNoMoreInteractions(logger);
    }
}
