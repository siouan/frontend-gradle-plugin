package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;

import org.gradle.api.GradleException;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.bean.Beans;

/**
 * This task installs the relevant package manager for the current project (by executing command
 * {@code corepack enable <package-manager>}).
 *
 * @since 7.0.0
 */
public class InstallPackageManagerTask extends AbstractRunCommandTask {

    private static final String COREPACK_ENABLE_COMMAND = "enable";

    /**
     * Type of package manager.
     */
    private final RegularFileProperty packageManagerNameFile;

    /**
     * File that will contain information about the package manager.
     */
    private final RegularFileProperty packageManagerExecutableFile;

    @Inject
    public InstallPackageManagerTask(final ProjectLayout projectLayout, final ObjectFactory objectFactory,
        final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
        this.packageManagerNameFile = objectFactory.fileProperty();
        this.executableType.set(ExecutableType.COREPACK);
        this.script.set(packageManagerNameFile.getAsFile().map(f -> {
            try {
                return String.join(" ", COREPACK_ENABLE_COMMAND,
                    Beans.getBean(beanRegistryId, FileManager.class).readString(f.toPath(), StandardCharsets.UTF_8));
            } catch (final BeanRegistryException | IOException e) {
                throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }));
        this.packageManagerExecutableFile = objectFactory.fileProperty();
    }

    @InputFile
    public RegularFileProperty getPackageManagerNameFile() {
        return packageManagerNameFile;
    }

    @OutputFile
    public RegularFileProperty getPackageManagerExecutableFile() {
        return packageManagerExecutableFile;
    }
}
