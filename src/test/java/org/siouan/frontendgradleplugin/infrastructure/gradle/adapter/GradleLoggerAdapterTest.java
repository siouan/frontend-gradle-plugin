package org.siouan.frontendgradleplugin.infrastructure.gradle.adapter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        adapter = new GradleLoggerAdapter(logger, LOGGING_LEVEL, PREFIX);
    }

    @Test
    void shouldDelegateDebugToGradleLogger() {
        adapter.debug(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).debug(PREFIX + MESSAGE, new Object[] {PARAMETER_1, PARAMETER_2});
        verifyNoMoreInteractions(logger);
    }

    @Test
    void shouldDelegateDefaultLogToGradleLogger() {
        adapter.log(MESSAGE, PARAMETER_1, PARAMETER_2);

        verify(logger).log(LOGGING_LEVEL, PREFIX + MESSAGE, PARAMETER_1, PARAMETER_2);
        verifyNoMoreInteractions(logger);
    }
}
