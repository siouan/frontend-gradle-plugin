package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;

import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;

/**
 * This task installs the relevant package manager for the current project (by executing command
 * {@code corepack enable <package-manager>}).
 *
 * @since 7.0.0
 */
public class InstallPackageManagerTask extends AbstractRunCommandTask {

    private static final String COREPACK_ENABLE_COMMAND = "enable";

    /**
     * File specifying the package manager used in the project. The file itself is not read, but using it as an input
     * allows to re-execute this task when its content changes (i.e. the package manager is upgraded).
     */
    private final RegularFileProperty packageManagerSpecificationFile;

    /**
     * File that will contain information about the package manager.
     */
    private final RegularFileProperty packageManagerExecutableFile;

    @Inject
    public InstallPackageManagerTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        this.executableType.set(ExecutableType.COREPACK);
        this.packageManagerSpecificationFile = objectFactory.fileProperty();
        this.packageManagerExecutableFile = objectFactory.fileProperty();
    }

    @InputFile
    public RegularFileProperty getPackageManagerSpecificationFile() {
        return packageManagerSpecificationFile;
    }

    @OutputFile
    public RegularFileProperty getPackageManagerExecutableFile() {
        return packageManagerExecutableFile;
    }

    @Override
    public void execute() throws NonRunnableTaskException, BeanRegistryException {
        final BeanRegistry beanRegistry = beanRegistryBuildService.get().getBeanRegistry();

        this.script.set(packageManagerSpecificationFile.getAsFile().map(f -> {
            try {
                return String.join(" ", COREPACK_ENABLE_COMMAND, beanRegistry
                    .getBean(FileManager.class)
                    .readString(f.toPath(), StandardCharsets.UTF_8)
                    .split("@")[0]);
            } catch (final BeanRegistryException | IOException e) {
                throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }));

        super.execute();
    }
}
