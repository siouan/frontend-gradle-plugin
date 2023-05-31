package org.siouan.frontendgradleplugin.domain.installer;

import java.net.URL;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.SystemSettingsProvider;

/**
 * Resolves proxy settings for a given URL.
 *
 * @since 5.0.0
 */
@RequiredArgsConstructor
public class ResolveProxySettingsByUrl {

    private static final String FILE_PROTOCOL = "file";

    private static final String HTTP_PROTOCOL = "http";

    private static final String HTTPS_PROTOCOL = "https";

    private final SystemSettingsProvider systemSettingsProvider;

    private final IsNonProxyHost isNonProxyHost;

    private final SelectProxySettings selectProxySettings;

    public ProxySettings execute(final ResolveProxySettingsByUrlCommand command) {
        final URL resourceUrl = command.getResourceUrl();
        final String resourceProtocol = resourceUrl.getProtocol();
        if (resourceProtocol.equals(HTTP_PROTOCOL) || resourceProtocol.equals(HTTPS_PROTOCOL)) {
            if (isNonProxyHost.execute(IsNonProxyHostCommand
                .builder()
                .nonProxyHosts(systemSettingsProvider.getNonProxyHosts())
                .hostNameOrIpAddress(resourceUrl.getHost())
                .build())) {
                return null;
            } else {
                final SelectProxySettingsCommand.SelectProxySettingsCommandBuilder selectProxySettingsCommandBuilder = SelectProxySettingsCommand.builder();
                if (resourceProtocol.equals(HTTPS_PROTOCOL)) {
                    selectProxySettingsCommandBuilder
                        .systemProxyHost(systemSettingsProvider.getHttpsProxyHost())
                        .systemProxyPort(systemSettingsProvider.getHttpsProxyPort())
                        .proxyHost(command.getHttpsProxyHost())
                        .proxyPort(command.getHttpsProxyPort())
                        .proxyCredentials(command.getHttpsProxyCredentials());
                } else {
                    selectProxySettingsCommandBuilder
                        .systemProxyHost(systemSettingsProvider.getHttpProxyHost())
                        .systemProxyPort(systemSettingsProvider.getHttpProxyPort())
                        .proxyHost(command.getHttpProxyHost())
                        .proxyPort(command.getHttpProxyPort())
                        .proxyCredentials(command.getHttpProxyCredentials());
                }
                return selectProxySettings.execute(selectProxySettingsCommandBuilder.build());
            }
        } else if (resourceProtocol.equals(FILE_PROTOCOL)) {
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported protocol: " + resourceUrl.getProtocol());
        }
    }
}
