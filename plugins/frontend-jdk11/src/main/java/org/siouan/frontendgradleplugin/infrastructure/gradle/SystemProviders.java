package org.siouan.frontendgradleplugin.infrastructure.gradle;

import lombok.Getter;
import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.siouan.frontendgradleplugin.domain.SystemProperties;

/**
 * Providers for system properties.
 *
 * @since 8.1.0
 */
@Getter
public class SystemProviders {

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

    /**
     * List of hosts that should be reached directly, bypassing the proxy.
     */
    private final Provider<String> nonProxyHosts;

    private final Provider<String> jvmArch;

    private final Provider<String> osName;

    public SystemProviders(final ProviderFactory providerFactory) {
        httpProxyHost = providerFactory.systemProperty(SystemProperties.HTTP_PROXY_HOST);
        httpProxyPort = providerFactory.systemProperty(SystemProperties.HTTP_PROXY_PORT);
        httpsProxyHost = providerFactory.systemProperty(SystemProperties.HTTPS_PROXY_HOST);
        httpsProxyPort = providerFactory.systemProperty(SystemProperties.HTTPS_PROXY_PORT);
        nonProxyHosts = providerFactory.systemProperty(SystemProperties.NON_PROXY_HOSTS);
        jvmArch = providerFactory.systemProperty(SystemProperties.JVM_ARCH);
        osName = providerFactory.systemProperty(SystemProperties.OS_NAME);
    }
}
