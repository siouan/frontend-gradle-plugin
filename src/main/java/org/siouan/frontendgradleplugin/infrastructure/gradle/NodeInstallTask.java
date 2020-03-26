package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.net.URL;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionIdException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.usecase.InstallNodeDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;

/**
 * Task that downloads and installs a Node distribution.
 */
public class NodeInstallTask extends DefaultTask {

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
        this.nodeVersion = getProject().getObjects().property(String.class);
        this.nodeInstallDirectory = getProject().getObjects().directoryProperty();
        this.nodeDistributionUrl = getProject().getObjects().property(String.class);
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
     */
    @TaskAction
    public void execute() throws BeanInstanciationException, ArchiverException, UnsupportedDistributionArchiveException,
        UnsupportedPlatformException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException,
        UnsupportedDistributionIdException, InvalidDistributionUrlException, DistributionValidatorException,
        IOException {
        final URL distributionUrl = nodeDistributionUrl.isPresent() ? new URL(nodeDistributionUrl.get()) : null;
        Beans
            .getBean(InstallNodeDistribution.class)
            .execute(new InstallSettings(Beans.getBean(Platform.class), nodeVersion.get(), distributionUrl,
                getTemporaryDir().toPath(), nodeInstallDirectory.getAsFile().get().toPath()));
    }
}
