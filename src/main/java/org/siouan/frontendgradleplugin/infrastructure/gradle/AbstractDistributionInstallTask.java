package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.net.URL;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.Environment;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;
import org.siouan.frontendgradleplugin.domain.usecase.InstallNodeDistribution;
import org.siouan.frontendgradleplugin.domain.usecase.ResolveProxySettingsByUrl;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;

/**
 * Task that downloads and installs a distribution.
 */
public abstract class AbstractDistributionInstallTask extends DefaultTask {

    /**
     * Bean registry ID.
     *
     * @since 5.2.0
     */
    private final String beanRegistryId;

    /**
     * Proxy host used to download resources with HTTP protocol.
     *
     * @since 5.0.0
     */
    private final Property<String> httpProxyHost;

    /**
     * Proxy port used to download resources with HTTP protocol.
     *
     * @since 5.0.0
     */
    private final Property<Integer> httpProxyPort;

    /**
     * Username to authenticate on the proxy server for HTTP requests.
     *
     * @since 5.0.0
     */
    private final Property<String> httpProxyUsername;

    /**
     * Password to authenticate on the proxy server for HTTP requests.
     *
     * @since 5.0.0
     */
    private final Property<String> httpProxyPassword;

    /**
     * Proxy host used to download resources with HTTPS protocol.
     *
     * @since 2.1.0
     */
    private final Property<String> httpsProxyHost;

    /**
     * Proxy port used to download resources with HTTPS protocol.
     *
     * @since 2.1.0
     */
    private final Property<Integer> httpsProxyPort;

    /**
     * Username to authenticate on the proxy server for HTTPS requests.
     *
     * @since 3.0.0
     */
    private final Property<String> httpsProxyUsername;

    /**
     * Password to authenticate on the proxy server for HTTPS requests.
     *
     * @since 3.0.0
     */
    private final Property<String> httpsProxyPassword;

    protected AbstractDistributionInstallTask(@Nonnull final ProjectLayout projectLayout,
        @Nonnull final ObjectFactory objectFactory) {
        this.beanRegistryId = Beans.getBeanRegistryId(projectLayout.getProjectDirectory().toString());
        this.httpProxyHost = objectFactory.property(String.class);
        this.httpProxyPort = objectFactory.property(Integer.class);
        this.httpProxyUsername = objectFactory.property(String.class);
        this.httpProxyPassword = objectFactory.property(String.class);
        this.httpsProxyHost = objectFactory.property(String.class);
        this.httpsProxyPort = objectFactory.property(Integer.class);
        this.httpsProxyUsername = objectFactory.property(String.class);
        this.httpsProxyPassword = objectFactory.property(String.class);
    }

    @Internal
    public Property<String> getHttpProxyHost() {
        return httpProxyHost;
    }

    @Internal
    public Property<Integer> getHttpProxyPort() {
        return httpProxyPort;
    }

    @Internal
    public Property<String> getHttpProxyUsername() {
        return httpProxyUsername;
    }

    @Internal
    public Property<String> getHttpProxyPassword() {
        return httpProxyPassword;
    }

    @Internal
    public Property<String> getHttpsProxyHost() {
        return httpsProxyHost;
    }

    @Internal
    public Property<Integer> getHttpsProxyPort() {
        return httpsProxyPort;
    }

    @Internal
    public Property<String> getHttpsProxyUsername() {
        return httpsProxyUsername;
    }

    @Internal
    public Property<String> getHttpsProxyPassword() {
        return httpsProxyPassword;
    }

    /**
     * Installs a distribution.
     *
     * @throws BeanRegistryException If a component cannot be instanciated.
     * @throws UnsupportedDistributionArchiveException If the distribution file type is not supported.
     * @throws UnsupportedPlatformException If the underlying platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is not valid.
     * @throws DistributionValidatorException If the downloaded distribution file is not valid.
     * @throws ArchiverException If an error occurs in the archiver exploding the distribution.
     * @throws IOException If an I/O error occurs.
     * @throws ResourceDownloadException If the distribution download failed.
     */
    @TaskAction
    public void execute() throws BeanRegistryException, FrontendException, IOException {
        Beans.getBean(beanRegistryId, TaskLoggerConfigurer.class).initLoggerAdapter(this);

        final Credentials distributionServerCredentials = getDistributionServerCredentials();
        final Credentials httpProxyCredentials = httpProxyUsername
            .map(username -> new Credentials(username, httpProxyPassword.get()))
            .getOrNull();
        final Credentials httpsProxyCredentials = httpsProxyUsername
            .map(username -> new Credentials(username, httpsProxyPassword.get()))
            .getOrNull();

        final SystemSettingsProvider systemSettingsProvider = Beans.getBean(beanRegistryId,
            SystemSettingsProvider.class);
        final Platform platform = new Platform(systemSettingsProvider.getSystemJvmArch(),
            systemSettingsProvider.getSystemOsName(), new Environment(systemSettingsProvider.getNodejsHomePath()));
        getLogger().debug("Platform: {}", platform);
        Beans
            .getBean(beanRegistryId, getInstallDistributionClass())
            .execute(getInstallSettings(platform, distributionServerCredentials, Beans
                .getBean(beanRegistryId, ResolveProxySettingsByUrl.class)
                .execute(httpProxyHost.getOrNull(), httpProxyPort.get(), httpProxyCredentials,
                    httpsProxyHost.getOrNull(), httpsProxyPort.get(), httpsProxyCredentials,
                    new URL(getDistributionUrlRoot()))));
    }

    /**
     * Gets credentials to authenticate on the distribution server.
     *
     * @return Credentials.
     */
    @Internal
    @Nullable
    protected abstract Credentials getDistributionServerCredentials();

    /**
     * Gets the concrete class implementing the install process.
     *
     * @return Class.
     */
    @Internal
    @Nonnull
    protected abstract Class<? extends InstallNodeDistribution> getInstallDistributionClass();

    /**
     * Gets the URL root to download the distribution. This root must contain the protocol, the domain name or IP
     * address, and the port. The pathinfo spec is optional.
     *
     * @return The URL root.
     */
    @Internal
    @Nonnull
    protected abstract String getDistributionUrlRoot();

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
        @Nullable Credentials distributionServerCredentials, @Nullable ProxySettings proxySettings);
}
