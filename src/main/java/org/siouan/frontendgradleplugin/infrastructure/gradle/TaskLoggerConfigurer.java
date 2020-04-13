package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.gradle.api.GradleException;
import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionListener;
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
            .debug("Configuring logger for task '{}' with default level: {}", task.getName(),
                extension.getLoggingLevel().get());
        try {
            beanRegistry
                .getBean(GradleLoggerAdapter.class)
                .init(task.getLogger(), extension.getLoggingLevel().get(), '[' + task.getName() + "] ");
        } catch (final BeanRegistryException e) {
            throw new GradleException("Cannot get instance of bean registry", e);
        }
    }

    @Override
    public void afterExecute(@Nonnull final Task task, @Nonnull final TaskState state) {
        // Event not used
    }
}
