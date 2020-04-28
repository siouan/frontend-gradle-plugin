package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

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
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.usecase.InstallYarnDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;

/**
 * Task that downloads and installs a Yarn distribution.
 */
public class YarnInstallTask extends DefaultTask {

    /**
     * Version of the distribution to download.
     */
    private final Property<String> yarnVersion;

    /**
     * Directory where the distribution shall be installed.
     */
    private final DirectoryProperty yarnInstallDirectory;

    /**
     * URL to download the distribution.
     */
    private final Property<String> yarnDistributionUrl;

    /**
     * URL pattern to download the distribution.
     */
    private final Property<String> yarnDistributionUrlPattern;

    /**
     * The Authorization header for downloading the distribution.
     */
    private final Property<String> yarnDistributionRequestAuthorization;

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

    public YarnInstallTask() {
        this.yarnVersion = getProject().getObjects().property(String.class);
        this.yarnInstallDirectory = getProject().getObjects().directoryProperty();
        this.yarnDistributionUrl = getProject().getObjects().property(String.class);
        this.yarnDistributionUrlPattern = getProject().getObjects().property(String.class);
        this.yarnDistributionRequestAuthorization = getProject().getObjects().property(String.class);
        this.proxyHost = getProject().getObjects().property(String.class);
        this.proxyPort = getProject().getObjects().property(Integer.class);
    }

    @Input
    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    @Input
    @Optional
    public Property<String> getYarnDistributionUrl() {
        return yarnDistributionUrl;
    }

    @Input
    @Optional
    public Property<String> getYarnDistributionUrlPattern() {
        return yarnDistributionUrlPattern;
    }

    @Input
    @Optional
    public Property<String> getYarnDistributionRequestAuthorization() {
        return yarnDistributionRequestAuthorization;
    }

    @OutputDirectory
    @Optional
    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
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
     * Installs a Yarn distribution.
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
        final URL distributionUrl = yarnDistributionUrl.isPresent() ? new URL(yarnDistributionUrl.get()) : null;
        final Proxy proxy = proxyHost
            .map(host -> new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, proxyPort.get())))
            .getOrElse(Proxy.NO_PROXY);
        Beans
            .getBean(InstallYarnDistribution.class)
            .execute(new InstallSettings(Beans.getBean(Platform.class), yarnVersion.get(), distributionUrl,
                yarnDistributionUrlPattern.getOrNull(), proxy, yarnDistributionRequestAuthorization.getOrNull(),
                getTemporaryDir().toPath(), yarnInstallDirectory.getAsFile().get().toPath()));
    }
}
