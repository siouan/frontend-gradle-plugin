package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.net.URISyntaxException;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionInstallerException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.InstallDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.usecase.InstallDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;

/**
 * Task that downloads and installs a Node distribution.
 */
public class NodeInstallTask extends DefaultTask {

    final Property<LogLevel> loggingLevel;

    /**
     * Version of the Node distribution to download.
     */
    private final Property<String> nodeVersion;

    /**
     * Directory where the Node distribution shall be installed.
     */
    private final DirectoryProperty nodeInstallDirectory;

    /**
     * URL to download the Node distribution.
     */
    private final Property<String> nodeDistributionUrl;

    public NodeInstallTask() {
        this.loggingLevel = getProject().getObjects().property(LogLevel.class);
        this.nodeVersion = getProject().getObjects().property(String.class);
        this.nodeInstallDirectory = getProject().getObjects().directoryProperty();
        this.nodeDistributionUrl = getProject().getObjects().property(String.class);
    }

    @Input
    @Optional
    public Property<LogLevel> getLoggingLevel() {
        return loggingLevel;
    }

    @Input
    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    @Input
    @Optional
    public Property<String> getNodeDistributionUrl() {
        return nodeDistributionUrl;
    }

    @OutputDirectory
    @Optional
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    /**
     * Executes the task: downloads and installs the distribution.
     *
     * @throws DistributionInstallerException If the installer failed.
     */
    @TaskAction
    public void execute() throws DistributionInstallerException, BeanInstanciationException, FrontendIOException,
        DistributionValidatorException, ArchiverException, UnsupportedDistributionArchiveException,
        DistributionUrlResolverException, UnsupportedEntryException, SlipAttackException, URISyntaxException {
        Beans
            .getBean(InstallDistribution.class)
            .execute(new InstallDistributionSettings(nodeVersion.get(), nodeDistributionUrl.getOrNull(),
                Beans.getBean(Platform.class), loggingLevel.get(), getTemporaryDir().toPath(),
                nodeInstallDirectory.getAsFile().get().toPath()));
    }
}
