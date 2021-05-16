package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.URL;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;

/**
 * Resolves proxy settings for a given URL.
 *
 * @since 5.0.0
 */
public class ResolveProxySettingsByUrl {

    private static final String FILE_PROTOCOL = "file";

    private static final String HTTP_PROTOCOL = "http";

    private static final String HTTPS_PROTOCOL = "https";

    private final SystemSettingsProvider systemSettingsProvider;

    private final IsNonProxyHost isNonProxyHost;

    private final SelectProxySettings selectProxySettings;

    public ResolveProxySettingsByUrl(@Nonnull final SystemSettingsProvider systemSettingsProvider,
        @Nonnull final IsNonProxyHost isNonProxyHost, @Nonnull final SelectProxySettings selectProxySettings) {
        this.systemSettingsProvider = systemSettingsProvider;
        this.isNonProxyHost = isNonProxyHost;
        this.selectProxySettings = selectProxySettings;
    }

    @Nullable
    public ProxySettings execute(@Nullable final String httpProxyHost, final int httpProxyPort,
        @Nullable final Credentials httpProxyCredentials, @Nullable final String httpsProxyHost,
        final int httpsProxyPort, @Nullable final Credentials httpsProxyCredentials, @Nonnull final URL resourceUrl) {

        final String resourceProtocol = resourceUrl.getProtocol();
        if (resourceProtocol.equals(HTTP_PROTOCOL) || resourceProtocol.equals(HTTPS_PROTOCOL)) {
            if (isNonProxyHost.execute(systemSettingsProvider.getNonProxyHosts(), resourceUrl.getHost())) {
                return null;
            } else if (resourceProtocol.equals(HTTPS_PROTOCOL)) {
                return selectProxySettings.execute(systemSettingsProvider.getHttpsProxyHost(),
                    systemSettingsProvider.getHttpsProxyPort(), httpsProxyHost, httpsProxyPort, httpsProxyCredentials);
            } else {
                return selectProxySettings.execute(systemSettingsProvider.getHttpProxyHost(),
                    systemSettingsProvider.getHttpProxyPort(), httpProxyHost, httpProxyPort, httpProxyCredentials);
            }
        } else if (resourceProtocol.equals(FILE_PROTOCOL)) {
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported protocol: " + resourceUrl.getProtocol());
        }
    }
}
