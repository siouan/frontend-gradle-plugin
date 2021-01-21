package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.exception.NonRunnableTaskException;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleScriptRunnerAdapter;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.ScriptProperties;

/**
 * This abstract class provides the reusable logic to run a command with an executable. Sub-classes must expose inputs
 * and outputs.
 */
public abstract class AbstractRunCommandTask extends DefaultTask {

    /**
     * Directory where the 'package.json' file is located.
     */
    final Property<File> packageJsonDirectory;

    /**
     * Directory where the Node distribution is installed.
     */
    final DirectoryProperty nodeInstallDirectory;

    /**
     * The command to execute.
     */
    final Property<String> script;

    AbstractRunCommandTask() {
        packageJsonDirectory = getProject().getObjects().property(File.class);
        nodeInstallDirectory = getProject().getObjects().directoryProperty();
        script = getProject().getObjects().property(String.class);
    }

    @Input
    @Optional
    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    @Internal
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Internal
    protected abstract String getExecutableType();

    /**
     * Asserts this task is runnable.
     *
     * @throws NonRunnableTaskException When the task is not runnable.
     */
    protected void assertThatTaskIsRunnable() throws NonRunnableTaskException {
        if (!script.isPresent()) {
            throw new NonRunnableTaskException("Missing property 'script'.");
        }
    }

    /**
     * Executes the task. If a command has been provided, it is run with the selected type of executable. Otherwise, the
     * task does nothing.
     *
     * @throws NonRunnableTaskException When the task is not runnable.
     * @throws ExecutableNotFoundException When the executable cannot be found (Node, npx, npm, Yarn).
     * @throws BeanRegistryException If a component cannot be instanciated.
     */
    @TaskAction
    public void execute() throws NonRunnableTaskException, ExecutableNotFoundException, BeanRegistryException {
        assertThatTaskIsRunnable();

        final String beanRegistryId = getProject().getPath();
        Beans
            .getBean(beanRegistryId, GradleScriptRunnerAdapter.class)
            .execute(
                new ScriptProperties(getProject(), packageJsonDirectory.map(File::toPath).get(), getExecutableType(),
                    nodeInstallDirectory.getAsFile().map(File::toPath).getOrNull(), script.get(),
                    Beans.getBean(beanRegistryId, Platform.class)));
    }
}
