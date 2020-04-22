package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.gradle.api.GradleException;
import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionListener;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.LoggingManager;
import org.gradle.api.tasks.TaskState;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleLoggerAdapter;

/**
 * Class injecting in the bean registry a logger forwarding messages to the logger of a Gradle task.
 *
 * @since 2.0.0
 */
public class TaskLoggerConfigurer implements TaskExecutionListener {

    private final BeanRegistry beanRegistry;

    private final FrontendExtension extension;

    public TaskLoggerConfigurer(final BeanRegistry beanRegistry, final FrontendExtension extension) {
        this.beanRegistry = beanRegistry;
        this.extension = extension;
    }

    @Override
    public void beforeExecute(@Nonnull final Task task) {
        task
            .getLogger()
            .debug("Configuring logger for task '{}', verboseModeEnabled: {}", task.getName(),
                extension.getVerboseModeEnabled().get());
        try {
            beanRegistry
                .getBean(GradleLoggerAdapter.class)
                .init(task.getLogger(), resolveLogLevel(task), extension.getVerboseModeEnabled().get(),
                    '[' + task.getName() + "] ");
        } catch (final BeanRegistryException e) {
            throw new GradleException("Cannot get instance of bean registry", e);
        }
    }

    @Override
    public void afterExecute(@Nonnull final Task task, @Nonnull final TaskState state) {
        // Event not used
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
    @Nonnull
    private LogLevel resolveLogLevel(@Nonnull final Task task) {
        LogLevel loggingLevel = task.getLogging().getLevel();
        if (loggingLevel != null) {
            return loggingLevel;
        }

        loggingLevel = task.getProject().getLogging().getLevel();
        if (loggingLevel != null) {
            return loggingLevel;
        }

        return task.getProject().getGradle().getStartParameter().getLogLevel();
    }
}
