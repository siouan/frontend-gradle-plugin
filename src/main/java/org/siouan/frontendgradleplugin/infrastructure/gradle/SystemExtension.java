package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.domain.model.SystemProperty;

/**
 * Extension providing system settings.
 *
 * @since 5.2.0
 */
public class SystemExtension {

    /**
     * Proxy host used to download resources with HTTP protocol.
     */
    private final Provider<String> httpProxyHost;

    /**
     * Proxy port used to download resources with HTTP protocol.
     */
    private final Provider<String> httpProxyPort;

    /**
     * Proxy host used to download resources with HTTPS protocol.
     */
    private final Provider<String> httpsProxyHost;

    /**
     * Proxy port used to download resources with HTTPS protocol.
     */
    private final Provider<String> httpsProxyPort;

    private final Provider<String> nonProxyHosts;

    private final Provider<String> jvmArch;

    private final Provider<String> osName;

    private final Provider<String> nodejsHomePath;

    public SystemExtension(@Nonnull final ProviderFactory providerFactory) {
        this.httpProxyHost = providerFactory.systemProperty(SystemProperty.HTTP_PROXY_HOST);
        this.httpProxyPort = providerFactory.systemProperty(SystemProperty.HTTP_PROXY_PORT);
        this.httpsProxyHost = providerFactory.systemProperty(SystemProperty.HTTPS_PROXY_HOST);
        this.httpsProxyPort = providerFactory.systemProperty(SystemProperty.HTTPS_PROXY_PORT);
        this.nonProxyHosts = providerFactory.systemProperty(SystemProperty.NON_PROXY_HOSTS);
        this.jvmArch = providerFactory.systemProperty(SystemProperty.JVM_ARCH_PROPERTY);
        this.osName = providerFactory.systemProperty(SystemProperty.OS_NAME_PROPERTY);
        this.nodejsHomePath = providerFactory.environmentVariable(FrontendGradlePlugin.NODEJS_HOME_ENV_VAR);
    }

    public Provider<String> getHttpProxyHost() {
        return httpProxyHost;
    }

    public Provider<String> getHttpProxyPort() {
        return httpProxyPort;
    }

    public Provider<String> getHttpsProxyHost() {
        return httpsProxyHost;
    }

    public Provider<String> getHttpsProxyPort() {
        return httpsProxyPort;
    }

    public Provider<String> getNonProxyHosts() {
        return nonProxyHosts;
    }

    public Provider<String> getJvmArch() {
        return jvmArch;
    }

    public Provider<String> getOsName() {
        return osName;
    }

    public Provider<String> getNodejsHomePath() {
        return nodejsHomePath;
    }
}
