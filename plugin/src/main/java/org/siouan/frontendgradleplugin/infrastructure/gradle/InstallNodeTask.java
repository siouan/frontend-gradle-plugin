package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.SetProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.FrontendException;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.installer.Credentials;
import org.siouan.frontendgradleplugin.domain.installer.InstallNodeDistribution;
import org.siouan.frontendgradleplugin.domain.installer.InstallNodeDistributionCommand;
import org.siouan.frontendgradleplugin.domain.installer.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.installer.ProxySettings;
import org.siouan.frontendgradleplugin.domain.installer.ResolveProxySettingsByUrl;
import org.siouan.frontendgradleplugin.domain.installer.ResolveProxySettingsByUrlCommand;
import org.siouan.frontendgradleplugin.domain.installer.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.installer.RetrySettings;
import org.siouan.frontendgradleplugin.domain.installer.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverException;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;

/**
 * Task that downloads and installs a Node distribution.
 */
public class InstallNodeTask extends DefaultTask {

    /**
     * Bean registry service provider.
     *
     * @since 8.1.0
     */
    protected final Property<BeanRegistryBuildService> beanRegistryBuildService;

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

    /**
     * Maximum number of attempts to download a file.
     *
     * @since 7.1.0
     */
    private final Property<Integer> maxDownloadAttempts;

    /**
     * HTTP statuses that should trigger another download attempt.
     *
     * @since 7.1.0
     */
    private final SetProperty<Integer> retryHttpStatuses;

    /**
     * Interval between the first download attempt and an eventual retry.
     *
     * @since 7.1.0
     */
    private final Property<Integer> retryInitialIntervalMs;

    /**
     * Multiplier used to compute the intervals between retry attempts.
     *
     * @since 7.1.0
     */
    private final Property<Double> retryIntervalMultiplier;

    /**
     * Whether the task should produce log messages for the end-user.
     *
     * @since 8.1.0
     */
    private final Property<Boolean> verboseModeEnabled;

    /**
     * Maximum interval between retry attempts.
     *
     * @since 7.1.0
     */
    private final Property<Integer> retryMaxIntervalMs;

    /**
     * System-level configuration of the HTTP proxy host.
     *
     * @since 8.1.0
     */
    private final Property<String> systemHttpProxyHost;

    /**
     * System-level configuration of the HTTP proxy port.
     *
     * @since 8.1.0
     */
    private final Property<Integer> systemHttpProxyPort;

    /**
     * System-level configuration of the HTTPS proxy host.
     *
     * @since 8.1.0
     */
    private final Property<String> systemHttpsProxyHost;

    /**
     * System-level configuration of the HTTPS proxy port
     *
     * @since 8.1.0
     */
    private final Property<Integer> systemHttpsProxyPort;

    /**
     * System-level configuration of hosts for which direct connection is requested.
     *
     * @since 8.1.0
     */
    private final SetProperty<String> systemNonProxyHosts;

    /**
     * Architecture of the underlying JVM.
     *
     * @since 8.1.0
     */
    private final Property<String> systemJvmArch;

    /**
     * System name of the O/S.
     *
     * @since 8.1.0
     */
    private final Property<String> systemOsName;

    @Inject
    public InstallNodeTask(final ObjectFactory objectFactory) {
        this.beanRegistryBuildService = objectFactory.property(BeanRegistryBuildService.class);
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
        this.maxDownloadAttempts = objectFactory.property(Integer.class);
        this.retryHttpStatuses = objectFactory.setProperty(Integer.class);
        this.retryInitialIntervalMs = objectFactory.property(Integer.class);
        this.retryIntervalMultiplier = objectFactory.property(Double.class);
        this.retryMaxIntervalMs = objectFactory.property(Integer.class);
        this.verboseModeEnabled = objectFactory.property(Boolean.class);
        this.systemHttpProxyHost = objectFactory.property(String.class);
        this.systemHttpProxyPort = objectFactory.property(Integer.class);
        this.systemHttpsProxyHost = objectFactory.property(String.class);
        this.systemHttpsProxyPort = objectFactory.property(Integer.class);
        this.systemNonProxyHosts = objectFactory.setProperty(String.class);
        this.systemJvmArch = objectFactory.property(String.class);
        this.systemOsName = objectFactory.property(String.class);
    }

    @Internal
    public Property<BeanRegistryBuildService> getBeanRegistryBuildService() {
        return beanRegistryBuildService;
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

    @Internal
    public Property<Integer> getMaxDownloadAttempts() {
        return maxDownloadAttempts;
    }

    @Internal
    public SetProperty<Integer> getRetryHttpStatuses() {
        return retryHttpStatuses;
    }

    @Internal
    public Property<Integer> getRetryInitialIntervalMs() {
        return retryInitialIntervalMs;
    }

    @Internal
    public Property<Double> getRetryIntervalMultiplier() {
        return retryIntervalMultiplier;
    }

    @Internal
    public Property<Integer> getRetryMaxIntervalMs() {
        return retryMaxIntervalMs;
    }

    @Internal
    public Property<Boolean> getVerboseModeEnabled() {
        return verboseModeEnabled;
    }

    @Internal
    public Property<String> getSystemHttpProxyHost() {
        return systemHttpProxyHost;
    }

    @Internal
    public Property<Integer> getSystemHttpProxyPort() {
        return systemHttpProxyPort;
    }

    @Internal
    public Property<String> getSystemHttpsProxyHost() {
        return systemHttpsProxyHost;
    }

    @Internal
    public Property<Integer> getSystemHttpsProxyPort() {
        return systemHttpsProxyPort;
    }

    @Internal
    public SetProperty<String> getSystemNonProxyHosts() {
        return systemNonProxyHosts;
    }

    @Internal
    public Property<String> getSystemJvmArch() {
        return systemJvmArch;
    }

    @Internal
    public Property<String> getSystemOsName() {
        return systemOsName;
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
    public void execute() throws BeanRegistryException, FrontendException, IOException, URISyntaxException {
        final BeanRegistry beanRegistry = beanRegistryBuildService.get().getBeanRegistry();
        TaskLoggerInitializer.initAdapter(this, verboseModeEnabled.get(),
            beanRegistry.getBean(GradleLoggerAdapter.class), beanRegistry.getBean(GradleSettings.class));

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

        final Platform platform = Platform.builder().jvmArch(systemJvmArch.get()).osName(systemOsName.get()).build();
        getLogger().debug("Platform: {}", platform);
        final ProxySettings proxySettings = beanRegistry
            .getBean(ResolveProxySettingsByUrl.class)
            .execute(ResolveProxySettingsByUrlCommand
                .builder()
                .httpsProxyHost(httpsProxyHost.getOrNull())
                .httpsProxyPort(httpsProxyPort.get())
                .httpsProxyCredentials(httpsProxyCredentials)
                .httpProxyHost(httpProxyHost.getOrNull())
                .httpProxyPort(httpProxyPort.get())
                .httpProxyCredentials(httpProxyCredentials)
                .resourceUrl(URI.create(nodeDistributionUrlRoot.get()).toURL())
                .systemHttpProxyHost(systemHttpProxyHost.getOrNull())
                .systemHttpProxyPort(systemHttpProxyPort.get())
                .systemHttpsProxyHost(systemHttpsProxyHost.getOrNull())
                .systemHttpsProxyPort(systemHttpsProxyPort.get())
                .systemNonProxyHosts(systemNonProxyHosts.get())
                .build());
        beanRegistry
            .getBean(InstallNodeDistribution.class)
            .execute(InstallNodeDistributionCommand
                .builder()
                .platform(platform)
                .version(nodeVersion.get())
                .distributionUrlRoot(nodeDistributionUrlRoot.get())
                .distributionUrlPathPattern(nodeDistributionUrlPathPattern.get())
                .distributionServerCredentials(distributionServerCredentials)
                .proxySettings(proxySettings)
                .retrySettings(RetrySettings
                    .builder()
                    .maxDownloadAttempts(maxDownloadAttempts.get())
                    .retryHttpStatuses(retryHttpStatuses.get())
                    .retryInitialIntervalMs(retryInitialIntervalMs.get())
                    .retryIntervalMultiplier(retryIntervalMultiplier.get())
                    .retryMaxIntervalMs(retryMaxIntervalMs.get())
                    .build())
                .temporaryDirectoryPath(getTemporaryDir().toPath())
                .installDirectoryPath(nodeInstallDirectory.map(File::toPath).get())
                .build());
    }
}
