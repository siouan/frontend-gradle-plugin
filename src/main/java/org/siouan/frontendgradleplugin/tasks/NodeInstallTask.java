package org.siouan.frontendgradleplugin.tasks;

import java.io.File;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.DistributionInstaller;
import org.siouan.frontendgradleplugin.core.DistributionInstallerException;
import org.siouan.frontendgradleplugin.core.DistributionInstallerSettings;
import org.siouan.frontendgradleplugin.core.DownloaderImpl;
import org.siouan.frontendgradleplugin.core.FileHasherImpl;
import org.siouan.frontendgradleplugin.core.NodeDistributionChecksumReaderImpl;
import org.siouan.frontendgradleplugin.core.NodeDistributionUrlResolver;
import org.siouan.frontendgradleplugin.core.NodeDistributionValidator;
import org.siouan.frontendgradleplugin.core.Utils;
import org.siouan.frontendgradleplugin.core.archivers.ArchiverFactoryImpl;

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
        loggingLevel = getProject().getObjects().property(LogLevel.class);
        nodeVersion = getProject().getObjects().property(String.class);
        nodeInstallDirectory = getProject().getObjects().directoryProperty();
        nodeDistributionUrl = getProject().getObjects().property(String.class);
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
     * @throws NoSuchAlgorithmException If the hashing algorithm used to check the distribution integrity is not
     * supported.
     */
    @TaskAction
    public void execute() throws DistributionInstallerException, NoSuchAlgorithmException {
        final String version = nodeVersion.get();
        final String distributionUrl = nodeDistributionUrl.getOrNull();
        final Path installDirectory = nodeInstallDirectory.getAsFile().map(File::toPath).get();
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(this, loggingLevel.get(),
            Utils.getSystemOsName(), getTemporaryDir().toPath(),
            new NodeDistributionUrlResolver(version, distributionUrl), new DownloaderImpl(getTemporaryDir().toPath()),
            new NodeDistributionValidator(this, loggingLevel.get(), new DownloaderImpl(getTemporaryDir().toPath()),
                new NodeDistributionChecksumReaderImpl(), new FileHasherImpl(), getTemporaryDir().toPath()),
            new ArchiverFactoryImpl(), installDirectory);
        new DistributionInstaller(settings).install();
    }
}
