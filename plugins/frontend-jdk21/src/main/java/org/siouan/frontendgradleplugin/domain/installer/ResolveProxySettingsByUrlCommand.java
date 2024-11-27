package org.siouan.frontendgradleplugin.domain.installer;

import java.net.URL;
import java.util.Set;

import lombok.Builder;

/**
 * Parameters to resolve proxy settings.
 *
 * @since 7.0.0
 */
@Builder
public record ResolveProxySettingsByUrlCommand(String httpProxyHost, int httpProxyPort,
    Credentials httpProxyCredentials, String httpsProxyHost, int httpsProxyPort, Credentials httpsProxyCredentials,
    URL resourceUrl, String systemHttpProxyHost, int systemHttpProxyPort, String systemHttpsProxyHost,
    int systemHttpsProxyPort, Set<String> systemNonProxyHosts) {}
