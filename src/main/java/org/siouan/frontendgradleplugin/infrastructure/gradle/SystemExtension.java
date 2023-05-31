package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.siouan.frontendgradleplugin.domain.SystemProperties;

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

    public SystemExtension(final ProviderFactory providerFactory) {
        this.httpProxyHost = providerFactory.systemProperty(SystemProperties.HTTP_PROXY_HOST);
        this.httpProxyPort = providerFactory.systemProperty(SystemProperties.HTTP_PROXY_PORT);
        this.httpsProxyHost = providerFactory.systemProperty(SystemProperties.HTTPS_PROXY_HOST);
        this.httpsProxyPort = providerFactory.systemProperty(SystemProperties.HTTPS_PROXY_PORT);
        this.nonProxyHosts = providerFactory.systemProperty(SystemProperties.NON_PROXY_HOSTS);
        this.jvmArch = providerFactory.systemProperty(SystemProperties.JVM_ARCH_PROPERTY);
        this.osName = providerFactory.systemProperty(SystemProperties.OS_NAME_PROPERTY);
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
}
