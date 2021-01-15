package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.URL;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.model.SystemProxySettings;

/**
 * Resolves proxy settings for a given URL.
 *
 * @since 5.0.0
 */
public class ResolveProxySettingsByUrl {

    private static final String FILE_PROTOCOL = "file";

    private static final String HTTP_PROTOCOL = "http";

    private static final String HTTPS_PROTOCOL = "https";

    private final SystemProxySettings systemProxySettings;

    private final IsNonProxyHost isNonProxyHost;

    private final SelectProxySettings selectProxySettings;

    public ResolveProxySettingsByUrl(@Nonnull final SystemProxySettings systemProxySettings,
        @Nonnull final IsNonProxyHost isNonProxyHost, @Nonnull final SelectProxySettings selectProxySettings) {
        this.systemProxySettings = systemProxySettings;
        this.isNonProxyHost = isNonProxyHost;
        this.selectProxySettings = selectProxySettings;
    }

    @Nullable
    public ProxySettings execute(@Nullable final String httpProxyHost, final int httpProxyPort,
        @Nullable final Credentials httpProxyCredentials, @Nullable final String httpsProxyHost,
        final int httpsProxyPort, @Nullable final Credentials httpsProxyCredentials, @Nonnull final URL resourceUrl) {

        final String resourceProtocol = resourceUrl.getProtocol();
        if (resourceProtocol.equals(HTTP_PROTOCOL) || resourceProtocol.equals(HTTPS_PROTOCOL)) {
            if (isNonProxyHost.execute(systemProxySettings.getNonProxyHosts(), resourceUrl.getHost())) {
                return null;
            } else if (resourceProtocol.equals(HTTPS_PROTOCOL)) {
                return selectProxySettings.execute(systemProxySettings.getHttpsProxyHost(),
                    systemProxySettings.getHttpsProxyPort(), httpsProxyHost, httpsProxyPort, httpsProxyCredentials);
            } else {
                return selectProxySettings.execute(systemProxySettings.getHttpProxyHost(),
                    systemProxySettings.getHttpProxyPort(), httpProxyHost, httpProxyPort, httpProxyCredentials);
            }
        } else if (resourceProtocol.equals(FILE_PROTOCOL)) {
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported protocol: " + resourceUrl.getProtocol());
        }
    }
}
