package org.siouan.frontendgradleplugin.tasks;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.DistributionInstallJob;
import org.siouan.frontendgradleplugin.core.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.core.DownloadException;
import org.siouan.frontendgradleplugin.core.DownloaderImpl;
import org.siouan.frontendgradleplugin.core.FileHasherImpl;
import org.siouan.frontendgradleplugin.core.InvalidDistributionException;
import org.siouan.frontendgradleplugin.core.NodeDistributionChecksumReaderImpl;
import org.siouan.frontendgradleplugin.core.NodeDistributionUrlResolver;
import org.siouan.frontendgradleplugin.core.NodeDistributionValidator;
import org.siouan.frontendgradleplugin.core.UnsupportedDistributionArchiveException;

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

    @Input
    @Optional
    public Property<String> getNodeDistributionUrl() {
        return nodeDistributionUrl;
    }

    @OutputDirectory
    @Optional
    public Property<File> getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    /**
     * Executes the task: downloads and installs the distribution.
     *
     * @throws IOException If an I/O error occured.
     * @throws InvalidDistributionException If the downloaded distribution is invalid.
     * @throws DistributionUrlResolverException If the URL to download the distribution cannot be downloaded.
     * @throws UnsupportedDistributionArchiveException If the type of the distribution is not supported.
     * @throws DownloadException If a download error occured.
     * @throws NoSuchAlgorithmException If the hashing algorithm used to check the distribution integrity is not
     * supported.
     */
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
