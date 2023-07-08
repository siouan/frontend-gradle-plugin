package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.LoggingManager;

/**
 * Class injecting in the bean registry a logger forwarding messages to the logger of a Gradle task.
 *
 * @since 2.0.0
 */
public class TaskLoggerConfigurer {

    private final FrontendExtension extension;

    private final GradleLoggerAdapter gradleLoggerAdapter;

    private final GradleSettings gradleSettings;

    public TaskLoggerConfigurer(final FrontendExtension extension, final GradleLoggerAdapter gradleLoggerAdapter,
        final GradleSettings gradleSettings) {
        this.extension = extension;
        this.gradleLoggerAdapter = gradleLoggerAdapter;
        this.gradleSettings = gradleSettings;
    }

    public void initLoggerAdapter(final Task task) {
        task
            .getLogger()
            .debug("Configuring logger for task '{}': verboseModeEnabled={}", task.getName(),
                extension.getVerboseModeEnabled().get());
        gradleLoggerAdapter.init(task.getLogger(), resolveLogLevel(task), extension.getVerboseModeEnabled().get(),
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
    private LogLevel resolveLogLevel(final Task task) {
        LogLevel loggingLevel = task.getLogging().getLevel();
        if (loggingLevel != null) {
            return loggingLevel;
        }

        loggingLevel = gradleSettings.projectLogLevel();
        if (loggingLevel != null) {
            return loggingLevel;
        }

        return gradleSettings.commandLineLogLevel();
    }
}
