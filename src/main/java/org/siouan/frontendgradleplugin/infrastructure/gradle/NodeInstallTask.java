package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionIdException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.usecase.InstallNodeDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;

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
     * URL pattern to download the Node.js distribution.
     */
    private final Property<String> nodeDistributionUrlPattern;

    /**
     * Username to authenticate on the server providing Node.js distributions.
     */
    private final Property<String> nodeDistributionServerUsername;

    /**
     * Password to authenticate on the server providing Node.js distributions.
     */
    private final Property<String> nodeDistributionServerPassword;

    /**
     * Proxy host used to download resources.
     *
     * @since 2.1.0
     */
    private final Property<String> proxyHost;

    /**
     * Proxy port used to download resources.
     *
     * @since 2.1.0
     */
    private final Property<Integer> proxyPort;

    public NodeInstallTask() {
        this.nodeVersion = getProject().getObjects().property(String.class);
        this.nodeInstallDirectory = getProject().getObjects().directoryProperty();
        this.nodeDistributionUrlPattern = getProject().getObjects().property(String.class);
        this.nodeDistributionServerUsername = getProject().getObjects().property(String.class);
        this.nodeDistributionServerPassword = getProject().getObjects().property(String.class);
        this.proxyHost = getProject().getObjects().property(String.class);
        this.proxyPort = getProject().getObjects().property(Integer.class);
    }

    @Input
    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    @Input
    public Property<String> getNodeDistributionUrlPattern() {
        return nodeDistributionUrlPattern;
    }

    @OutputDirectory
    @Optional
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Internal
    public Property<String> getNodeDistributionServerUsername() {
        return nodeDistributionServerUsername;
    }

    @Internal
    public Property<String> getNodeDistributionServerPassword() {
        return nodeDistributionServerPassword;
    }

    @Internal
    public Property<String> getProxyHost() {
        return proxyHost;
    }

    @Internal
    public Property<Integer> getProxyPort() {
        return proxyPort;
    }

    /**
     * Installs a Node.js distribution.
     *
     * @throws BeanRegistryException If a component cannot be instanciated.
     * @throws UnsupportedDistributionIdException If the type of distribution to install is not supported.
     * @throws UnsupportedDistributionArchiveException If the distribution file type is not supported.
     * @throws UnsupportedPlatformException If the underlying platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is not valid.
     * @throws DistributionValidatorException If the downloaded distribution file is not valid.
     * @throws ArchiverException If an error occurs in the archiver exploding the distribution.
     * @throws IOException If an I/O error occurs.
     */
    @TaskAction
    public void execute() throws BeanRegistryException, ArchiverException, UnsupportedDistributionArchiveException,
        UnsupportedPlatformException, UnsupportedDistributionIdException, InvalidDistributionUrlException,
        DistributionValidatorException, IOException {
        final Credentials credentials = nodeDistributionServerUsername
            .map(
                username -> new Credentials(nodeDistributionServerUsername.get(), nodeDistributionServerPassword.get()))
            .getOrNull();
        final Proxy proxy = proxyHost
            .map(host -> new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, proxyPort.get())))
            .getOrElse(Proxy.NO_PROXY);
        Beans
            .getBean(InstallNodeDistribution.class)
            .execute(
                new InstallSettings(Beans.getBean(Platform.class), nodeVersion.get(), nodeDistributionUrlPattern.get(),
                    credentials, proxy, getTemporaryDir().toPath(), nodeInstallDirectory.getAsFile().get().toPath()));
    }
}
