package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleScriptRunnerAdapter;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.ScriptProperties;

/**
 * This abstract class provides the reusable logic to run a script with an executable. Sub-classes must expose inputs
 * and outputs.
 */
public abstract class AbstractRunScriptTask extends DefaultTask {

    /**
     * Directory where the 'package.json' file is located.
     */
    final Property<File> packageJsonDirectory;

    /**
     * Directory where the Node distribution is installed.
     */
    final DirectoryProperty nodeInstallDirectory;

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    final Property<Boolean> yarnEnabled;

    /**
     * Directory where the Yarn distribution is installed.
     */
    final DirectoryProperty yarnInstallDirectory;

    /**
     * The command to execute.
     */
    final Property<String> script;

    AbstractRunScriptTask() {
        packageJsonDirectory = getProject().getObjects().property(File.class);
        nodeInstallDirectory = getProject().getObjects().directoryProperty();
        yarnEnabled = getProject().getObjects().property(Boolean.class);
        yarnInstallDirectory = getProject().getObjects().directoryProperty();
        script = getProject().getObjects().property(String.class);
    }

    @Input
    @Optional
    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    @InputDirectory
    @Optional
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @InputDirectory
    @Optional
    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    @Internal
    protected abstract ExecutableType getExecutableType();

    /**
     * Executes the task. If a command has been provided, it is run with the selected type of executable. Otherwise, the
     * task does nothing.
     *
     * @throws ExecutableNotFoundException When the executable cannot be found (Node, NPX, NPM, Yarn).
     */
    @TaskAction
    public void execute()
        throws ExecutableNotFoundException, BeanInstanciationException, TooManyCandidateBeansException,
        ZeroOrMultiplePublicConstructorsException {
        if (script.isPresent()) {
            Beans
                .getBean(GradleScriptRunnerAdapter.class)
                .execute(new ScriptProperties(getProject(), packageJsonDirectory.map(File::toPath).get(),
                    getExecutableType(), nodeInstallDirectory.getAsFile().map(File::toPath).get(),
                    yarnInstallDirectory.getAsFile().map(File::toPath).getOrNull(), script.get(),
                    Beans.getBean(Platform.class)));
        }
    }
}
