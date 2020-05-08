package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;
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
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.usecase.AbstractInstallDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;

/**
 * Task that downloads and installs a distribution.
 */
public abstract class AbstractDistributionInstallTask extends DefaultTask {

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

    /**
     * Username to authenticate on the proxy server.
     *
     * @since 3.0.0
     */
    private final Property<String> proxyUsername;

    /**
     * Password to authenticate on the proxy server.
     *
     * @since 3.0.0
     */
    private final Property<String> proxyPassword;

    public AbstractDistributionInstallTask() {
        this.proxyHost = getProject().getObjects().property(String.class);
        this.proxyPort = getProject().getObjects().property(Integer.class);
        this.proxyUsername = getProject().getObjects().property(String.class);
        this.proxyPassword = getProject().getObjects().property(String.class);
    }

    @Internal
    public Property<String> getProxyHost() {
        return proxyHost;
    }

    @Internal
    public Property<Integer> getProxyPort() {
        return proxyPort;
    }

    @Internal
    public Property<String> getProxyUsername() {
        return proxyUsername;
    }

    @Internal
    public Property<String> getProxyPassword() {
        return proxyPassword;
    }

    /**
     * Installs a distribution.
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
        final Credentials distributionServerCredentials = getDistributionServerCredentials();
        final Proxy proxy;
        final Credentials proxyServerCredentials;
        if (proxyHost.isPresent()) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost.get(), proxyPort.get()));
            proxyServerCredentials =
                proxyUsername.isPresent() ? new Credentials(proxyUsername.get(), proxyPassword.get()) : null;
        } else {
            proxy = Proxy.NO_PROXY;
            proxyServerCredentials = null;
        }
        Beans
            .getBean(getInstallDistributionClass())
            .execute(getInstallSettings(Beans.getBean(Platform.class), distributionServerCredentials,
                new ProxySettings(proxy, proxyServerCredentials)));
    }

    /**
     * Gets credentials to authenticate on the distribution server.
     *
     * @return Credentials.
     */
    @Nullable
    protected abstract Credentials getDistributionServerCredentials();

    /**
     * Gets the concrete class implementing the install process.
     *
     * @return Class.
     */
    @Nonnull
    protected abstract Class<? extends AbstractInstallDistribution> getInstallDistributionClass();

    /**
     * Gets the install settings.
     *
     * @param platform Underlying platform.
     * @param distributionServerCredentials Credentials to authenticate on the distribution server.
     * @param proxySettings Proxy settings.
     * @return Install settings.
     */
    @Nonnull
    protected abstract InstallSettings getInstallSettings(@Nonnull Platform platform,
        @Nullable Credentials distributionServerCredentials, @Nonnull ProxySettings proxySettings);
}
