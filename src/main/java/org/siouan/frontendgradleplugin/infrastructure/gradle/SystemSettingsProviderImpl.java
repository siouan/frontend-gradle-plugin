package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.provider.Provider;
import org.siouan.frontendgradleplugin.domain.model.SystemProperty;
import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;

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

    private final Provider<String> nodejsHomePath;

    private final Provider<String> yarnHomePath;

    private final int defaultHttpProxyPort;

    private final int defaultHttpsProxyPort;

    public SystemSettingsProviderImpl(@Nonnull final SystemExtension systemExtension, final int defaultHttpProxyPort,
        final int defaultHttpsProxyPort) {
        this.httpProxyHost = systemExtension.getHttpProxyHost();
        this.httpProxyPort = systemExtension.getHttpProxyPort();
        this.httpsProxyHost = systemExtension.getHttpsProxyHost();
        this.httpsProxyPort = systemExtension.getHttpsProxyPort();
        this.nonProxyHosts = systemExtension.getNonProxyHosts();
        this.jvmArch = systemExtension.getJvmArch();
        this.osName = systemExtension.getOsName();
        this.nodejsHomePath = systemExtension.getNodejsHomePath();
        this.yarnHomePath = systemExtension.getYarnHomePath();
        this.defaultHttpProxyPort = defaultHttpProxyPort;
        this.defaultHttpsProxyPort = defaultHttpsProxyPort;
    }

    @Nullable
    @Override
    public String getHttpProxyHost() {
        return httpProxyHost.getOrNull();
    }

    @Override
    public int getHttpProxyPort() {
        return Optional
            .ofNullable(httpProxyPort.getOrNull())
            .filter(port -> !port.trim().isEmpty())
            .map(Integer::parseInt)
            .orElse(defaultHttpProxyPort);
    }

    @Nullable
    @Override
    public String getHttpsProxyHost() {
        return httpsProxyHost.getOrNull();
    }

    @Override
    public int getHttpsProxyPort() {
        return Optional
            .ofNullable(httpsProxyPort.getOrNull())
            .filter(port -> !port.trim().isEmpty())
            .map(Integer::parseInt)
            .orElse(defaultHttpsProxyPort);
    }

    @Nonnull
    @Override
    public Set<String> getNonProxyHosts() {
        return new HashSet<>(Optional
            .ofNullable(nonProxyHosts.getOrNull())
            .filter(v -> !v.trim().isEmpty())
            .map(hosts -> Arrays.asList(hosts.split(SystemProperty.NON_PROXY_HOSTS_SPLIT_PATTERN)))
            .orElseGet(Collections::emptyList));
    }

    @Nonnull
    @Override
    public String getSystemJvmArch() {
        return jvmArch.get();
    }

    @Nonnull
    @Override
    public String getSystemOsName() {
        return osName.get();
    }

    @Nullable
    @Override
    public Path getNodejsHomePath() {
        return toPath(nodejsHomePath.getOrNull());
    }

    @Nullable
    @Override
    public Path getYarnHomePath() {
        return toPath(yarnHomePath.getOrNull());
    }

    /**
     * Gets the value of an environment variable.
     *
     * @param value Value.
     * @return Path
     */
    @Nullable
    private Path toPath(@Nullable final String value) {
        return Optional.ofNullable(value).filter(v -> !v.trim().isEmpty()).map(Paths::get).orElse(null);
    }
}
