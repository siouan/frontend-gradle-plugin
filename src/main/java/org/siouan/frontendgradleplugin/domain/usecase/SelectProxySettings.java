package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.Proxy;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;

/**
 * Select applicable proxy settings: if the plugin provides its own settings, then they are applied. Otherwise, system
 * settings are applied, if any.
 *
 * @since 5.0.0
 */
public class SelectProxySettings {

    @Nullable
    public ProxySettings execute(@Nullable final String systemProxyHost, final int systemProxyPort,
        @Nullable final String proxyHost, final int proxyPort, @Nullable final Credentials proxyCredentials) {
        final String resolvedProxyHost;
        if (proxyHost == null) {
            resolvedProxyHost = systemProxyHost;
        } else {
            resolvedProxyHost = proxyHost;
        }
        final int resolvedProxyPort;
        if (proxyHost == null) {
            resolvedProxyPort = systemProxyPort;
        } else {
            resolvedProxyPort = proxyPort;
        }
        return (resolvedProxyHost == null) ? null
            : new ProxySettings(Proxy.Type.HTTP, resolvedProxyHost, resolvedProxyPort, proxyCredentials);
    }
}
