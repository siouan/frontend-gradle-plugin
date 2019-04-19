package org.siouan.frontendgradleplugin.node;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.DownloadException;
import org.siouan.frontendgradleplugin.DownloaderImpl;
import org.siouan.frontendgradleplugin.FileHasherImpl;
import org.siouan.frontendgradleplugin.job.DistributionInstallJob;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.job.InvalidDistributionException;
import org.siouan.frontendgradleplugin.job.UnsupportedDistributionArchiveException;

/**
 * Task that downloads and installs a Node distribution.
 */
public class NodeInstallTask extends DefaultTask {

    /**
     * Default task name.
     */
    public static final String DEFAULT_NAME = "installNode";

    /**
     * Version of the Node distribution to download.
     */
    private final Property<String> nodeVersion;

    /**
     * Directory where the Node distribution shall be installed.
     */
    private final Property<File> nodeInstallDirectory;

    /**
     * URL to download the Node distribution.
     */
    private final Property<String> nodeDistributionUrl;

    public NodeInstallTask() {
        nodeVersion = getProject().getObjects().property(String.class);
        nodeInstallDirectory = getProject().getObjects().property(File.class);
        nodeDistributionUrl = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    @OutputDirectory
    @Optional
    public Property<File> getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Input
    @Optional
    public Property<String> getNodeDistributionUrl() {
        return nodeDistributionUrl;
    }

    @TaskAction
    public void execute() throws IOException, InvalidDistributionException, DistributionUrlResolverException,
        UnsupportedDistributionArchiveException, DownloadException, NoSuchAlgorithmException {
        final String version = nodeVersion.get();
        final String distributionUrl = nodeDistributionUrl.getOrNull();
        final File installDirectory = nodeInstallDirectory.get();
        new DistributionInstallJob(this, new NodeDistributionUrlResolver(version, distributionUrl),
            new NodeDistributionValidator(this, new DownloaderImpl(getTemporaryDir()),
                new NodeDistributionChecksumReaderImpl(), new FileHasherImpl(), installDirectory), installDirectory)
            .install();
    }
}
