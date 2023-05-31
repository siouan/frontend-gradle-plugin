package org.siouan.frontendgradleplugin.domain.installer;

import java.net.Proxy;

/**
 * Select applicable proxy settings: if the plugin provides its own settings, then they are applied. Otherwise, system
 * settings are applied, if any.
 *
 * @since 5.0.0
 */
public class SelectProxySettings {

    public ProxySettings execute(final SelectProxySettingsCommand command) {
        final String resolvedProxyHost;
        final int resolvedProxyPort;
        if (command.getProxyHost() == null) {
            resolvedProxyHost = command.getSystemProxyHost();
            resolvedProxyPort = command.getSystemProxyPort();
        } else {
            resolvedProxyHost = command.getProxyHost();
            resolvedProxyPort = command.getProxyPort();
        }
        return (resolvedProxyHost == null) ? null : ProxySettings
            .builder()
            .proxyType(Proxy.Type.HTTP)
            .proxyHost(resolvedProxyHost)
            .proxyPort(resolvedProxyPort)
            .credentials(command.getProxyCredentials())
            .build();
    }
}
