package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.gradle.api.provider.Provider;
import org.siouan.frontendgradleplugin.domain.SystemProperties;
import org.siouan.frontendgradleplugin.domain.SystemSettingsProvider;

/**
 * System-wide proxy settings.
 *
 * @since 5.2.0
 */
public class SystemSettingsProviderImpl implements SystemSettingsProvider {

    private final Provider<String> httpProxyHost;

    private final Provider<String> httpProxyPort;

    private final Provider<String> httpsProxyHost;

    private final Provider<String> httpsProxyPort;

    private final Provider<String> nonProxyHosts;

    private final Provider<String> jvmArch;

    private final Provider<String> osName;

    private final int defaultHttpProxyPort;

    private final int defaultHttpsProxyPort;

    public SystemSettingsProviderImpl(final SystemExtension systemExtension, final int defaultHttpProxyPort,
        final int defaultHttpsProxyPort) {
        this.httpProxyHost = systemExtension.getHttpProxyHost();
        this.httpProxyPort = systemExtension.getHttpProxyPort();
        this.httpsProxyHost = systemExtension.getHttpsProxyHost();
        this.httpsProxyPort = systemExtension.getHttpsProxyPort();
        this.nonProxyHosts = systemExtension.getNonProxyHosts();
        this.jvmArch = systemExtension.getJvmArch();
        this.osName = systemExtension.getOsName();
        this.defaultHttpProxyPort = defaultHttpProxyPort;
        this.defaultHttpsProxyPort = defaultHttpsProxyPort;
    }

    @Override
    public String getHttpProxyHost() {
        return httpProxyHost.getOrNull();
    }

    @Override
    public int getHttpProxyPort() {
        return Optional
            .ofNullable(httpProxyPort.getOrNull())
            .filter(port -> !port.isBlank())
            .map(Integer::parseInt)
            .orElse(defaultHttpProxyPort);
    }

    @Override
    public String getHttpsProxyHost() {
        return httpsProxyHost.getOrNull();
    }

    @Override
    public int getHttpsProxyPort() {
        return Optional
            .ofNullable(httpsProxyPort.getOrNull())
            .filter(port -> !port.isBlank())
            .map(Integer::parseInt)
            .orElse(defaultHttpsProxyPort);
    }

    @Override
    public Set<String> getNonProxyHosts() {
        return Optional
            .ofNullable(nonProxyHosts.getOrNull())
            .filter(Predicate.not(String::isBlank))
            .map(hosts -> hosts.split(SystemProperties.NON_PROXY_HOSTS_SPLIT_PATTERN))
            .map(Set::of)
            .orElseGet(Set::of);
    }

    @Override
    public String getSystemJvmArch() {
        return jvmArch.get();
    }

    @Override
    public String getSystemOsName() {
        return osName.get();
    }
}
