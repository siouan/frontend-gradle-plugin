package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionListener;
import org.gradle.api.tasks.TaskState;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleLoggerAdapter;

/**
 * Class injecting in the bean registry a logger forwarding messages to the logger of a Gradle task.
 *
 * @since 2.0.0
 */
public class TaskLoggerInjector implements TaskExecutionListener {

    private final FrontendExtension extension;

    public TaskLoggerInjector(@Nonnull final FrontendExtension extension) {
        this.extension = extension;
    }

    @Override
    public void beforeExecute(@Nonnull final Task task) {
        task
            .getLogger()
            .debug("Injecting logger for task '{}' with default level: {}", task.getName(),
                extension.getLoggingLevel().get());
        Beans.registerBean(
            new GradleLoggerAdapter(task.getLogger(), extension.getLoggingLevel().get(), '[' + task.getName() + "] "));
    }

    @Override
    public void afterExecute(@Nonnull final Task task, @Nonnull final TaskState state) {
        // Event not used
    }
}
