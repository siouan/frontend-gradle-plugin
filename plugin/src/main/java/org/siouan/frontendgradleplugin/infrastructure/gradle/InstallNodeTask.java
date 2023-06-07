package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.FrontendException;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.installer.Credentials;
import org.siouan.frontendgradleplugin.domain.installer.InstallNodeDistribution;
import org.siouan.frontendgradleplugin.domain.installer.InstallNodeDistributionCommand;
import org.siouan.frontendgradleplugin.domain.installer.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.installer.ProxySettings;
import org.siouan.frontendgradleplugin.domain.installer.ResolveProxySettingsByUrl;
import org.siouan.frontendgradleplugin.domain.installer.ResolveProxySettingsByUrlCommand;
import org.siouan.frontendgradleplugin.domain.installer.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.installer.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverException;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.bean.Beans;

/**
 * Task that downloads and installs a Node distribution.
 */
public class InstallNodeTask extends DefaultTask {

    /**
     * Bean registry ID.
     *
     * @since 5.2.0
     */
    protected final String beanRegistryId;

    /**
     * Version of the Node distribution to download.
     */
    private final Property<String> nodeVersion;

    /**
     * Directory where the Node distribution shall be installed.
     */
    private final Property<File> nodeInstallDirectory;

    /**
     * URL root part to build the exact URL to download the Node.js distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the Node.js distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionUrlPathPattern;

    /**
     * Username to authenticate on the server providing Node.js distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionServerUsername;

    /**
     * Password to authenticate on the server providing Node.js distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionServerPassword;

    /**
     * Node executable to be used once the distribution is installed.
     *
     * @since 7.0.0
     */
    private final RegularFileProperty nodeExecutableFile;

    /**
     * Proxy host used to download resources with HTTP protocol.
     *
     * @since 5.0.0
     */
    protected final Property<String> httpProxyHost;

    /**
     * Proxy port used to download resources with HTTP protocol.
     *
     * @since 5.0.0
     */
    protected final Property<Integer> httpProxyPort;

    /**
     * Username to authenticate on the proxy server for HTTP requests.
     *
     * @since 5.0.0
     */
    protected final Property<String> httpProxyUsername;

    /**
     * Password to authenticate on the proxy server for HTTP requests.
     *
     * @since 5.0.0
     */
    protected final Property<String> httpProxyPassword;

    /**
     * Proxy host used to download resources with HTTPS protocol.
     *
     * @since 2.1.0
     */
    protected final Property<String> httpsProxyHost;

    /**
     * Proxy port used to download resources with HTTPS protocol.
     *
     * @since 2.1.0
     */
    protected final Property<Integer> httpsProxyPort;

    /**
     * Username to authenticate on the proxy server for HTTPS requests.
     *
     * @since 3.0.0
     */
    protected final Property<String> httpsProxyUsername;

    /**
     * Password to authenticate on the proxy server for HTTPS requests.
     *
     * @since 3.0.0
     */
    protected final Property<String> httpsProxyPassword;

    @Inject
    public InstallNodeTask(final ProjectLayout projectLayout, final ObjectFactory objectFactory) {
        this.beanRegistryId = Beans.getBeanRegistryId(projectLayout.getProjectDirectory().toString());
        this.nodeVersion = objectFactory.property(String.class);
        this.nodeInstallDirectory = objectFactory.property(File.class);
        this.nodeDistributionUrlRoot = objectFactory.property(String.class);
        this.nodeDistributionUrlPathPattern = objectFactory.property(String.class);
        this.nodeDistributionServerUsername = objectFactory.property(String.class);
        this.nodeDistributionServerPassword = objectFactory.property(String.class);
        this.nodeExecutableFile = objectFactory.fileProperty();
        this.httpProxyHost = objectFactory.property(String.class);
        this.httpProxyPort = objectFactory.property(Integer.class);
        this.httpProxyUsername = objectFactory.property(String.class);
        this.httpProxyPassword = objectFactory.property(String.class);
        this.httpsProxyHost = objectFactory.property(String.class);
        this.httpsProxyPort = objectFactory.property(Integer.class);
        this.httpsProxyUsername = objectFactory.property(String.class);
        this.httpsProxyPassword = objectFactory.property(String.class);
    }

    @Input
    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    @Input
    public Property<String> getNodeDistributionUrlRoot() {
        return nodeDistributionUrlRoot;
    }

    @Input
    public Property<String> getNodeDistributionUrlPathPattern() {
        return nodeDistributionUrlPathPattern;
    }

    @Input
    public Property<File> getNodeInstallDirectory() {
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

    @OutputFile
    public RegularFileProperty getNodeExecutableFile() {
        return nodeExecutableFile;
    }

    /**
     * Installs a distribution.
     *
     * @throws BeanRegistryException If a component cannot be instanciated.
     * @throws UnsupportedDistributionArchiveException If the distribution file type is not supported.
     * @throws UnsupportedPlatformException If the underlying platform is not supported.
     * @throws InvalidDistributionUrlException If the URL to download the distribution is not valid.
     * @throws ArchiverException If an error occurs in the archiver exploding the distribution.
     * @throws IOException If an I/O error occurs.
     * @throws ResourceDownloadException If the distribution download failed.
     */
    @TaskAction
    public void execute() throws BeanRegistryException, FrontendException, IOException {
        Beans.getBean(beanRegistryId, TaskLoggerConfigurer.class).initLoggerAdapter(this);

        final Credentials distributionServerCredentials = nodeDistributionServerUsername
            .map(username -> Credentials
                .builder()
                .username(nodeDistributionServerUsername.get())
                .password(nodeDistributionServerPassword.get())
                .build())
            .getOrNull();
        final Credentials httpProxyCredentials = httpProxyUsername
            .map(username -> Credentials.builder().username(username).password(httpProxyPassword.get()).build())
            .getOrNull();
        final Credentials httpsProxyCredentials = httpsProxyUsername
            .map(username -> Credentials.builder().username(username).password(httpsProxyPassword.get()).build())
            .getOrNull();

        final Platform platform = Beans.getBean(beanRegistryId, PlatformProvider.class).getPlatform();
        getLogger().debug("Platform: {}", platform);
        final ProxySettings proxySettings = Beans
            .getBean(beanRegistryId, ResolveProxySettingsByUrl.class)
            .execute(ResolveProxySettingsByUrlCommand
                .builder()
                .httpsProxyHost(httpsProxyHost.getOrNull())
                .httpsProxyPort(httpsProxyPort.get())
                .httpsProxyCredentials(httpsProxyCredentials)
                .httpProxyHost(httpProxyHost.getOrNull())
                .httpProxyPort(httpProxyPort.get())
                .httpProxyCredentials(httpProxyCredentials)
                .resourceUrl(new URL(nodeDistributionUrlRoot.get()))
                .build());
        Beans
            .getBean(beanRegistryId, InstallNodeDistribution.class)
            .execute(InstallNodeDistributionCommand
                .builder()
                .platform(platform)
                .version(nodeVersion.get())
                .distributionUrlRoot(nodeDistributionUrlRoot.get())
                .distributionUrlPathPattern(nodeDistributionUrlPathPattern.get())
                .distributionServerCredentials(distributionServerCredentials)
                .proxySettings(proxySettings)
                .temporaryDirectoryPath(getTemporaryDir().toPath())
                .installDirectoryPath(nodeInstallDirectory.map(File::toPath).get())
                .build());
    }
}
