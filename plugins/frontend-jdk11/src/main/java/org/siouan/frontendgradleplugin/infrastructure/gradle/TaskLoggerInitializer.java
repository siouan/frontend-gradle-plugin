package org.siouan.frontendgradleplugin.infrastructure.gradle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.LoggingManager;

/**
 * Class injecting in the bean registry a logger forwarding messages to the logger of a Gradle task.
 *
 * @since 2.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskLoggerInitializer {

    public static void initAdapter(final Task task, final boolean verboseModeEnabled,
        final GradleLoggerAdapter gradleLoggerAdapter, final GradleSettings gradleSettings) {
        task
            .getLogger()
            .debug("Configuring logger for task '{}': verboseModeEnabled={}", task.getName(), verboseModeEnabled);
        gradleLoggerAdapter.init(task.getLogger(), resolveLogLevel(task, gradleSettings), verboseModeEnabled,
            '[' + task.getName() + "] ");
    }

    /**
     * Resolves the logging level currently active for a given task. This method allows to deal with Gradle's
     * limitations when we need to get the applicable logging level for a task. Actually Gradle does not populate
     * automatically the level in the task's {@link LoggingManager} with the level from the command line by default, and
     * the only way to get it is to look at the start parameter.
     *
     * @param task Task.
     * @return Logging level.
     */
    private static LogLevel resolveLogLevel(final Task task, final GradleSettings gradleSettings) {
        LogLevel loggingLevel = task.getLogging().getLevel();
        if (loggingLevel != null) {
            return loggingLevel;
        }

        loggingLevel = gradleSettings.getProjectLogLevel();
        if (loggingLevel != null) {
            return loggingLevel;
        }

        return gradleSettings.getCommandLineLogLevel();
    }
}
