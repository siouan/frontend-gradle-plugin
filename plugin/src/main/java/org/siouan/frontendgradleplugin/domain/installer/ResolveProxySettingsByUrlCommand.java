package org.siouan.frontendgradleplugin.domain.installer;

import java.net.URL;

import lombok.Builder;
import lombok.Getter;

/**
 * Parameters to resolve proxy settings.
 *
 * @since 7.0.0
 */
@Builder
@Getter
public class ResolveProxySettingsByUrlCommand {

    private final String httpProxyHost;

    private final int httpProxyPort;

    private final Credentials httpProxyCredentials;

    private final String httpsProxyHost;

    private final int httpsProxyPort;

    private final Credentials httpsProxyCredentials;

    private final URL resourceUrl;
}
