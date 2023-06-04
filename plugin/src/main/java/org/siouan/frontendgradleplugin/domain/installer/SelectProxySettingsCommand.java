package org.siouan.frontendgradleplugin.domain.installer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Parameters to select the relevant proxy settings.
 *
 * @since 7.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SelectProxySettingsCommand {

    @EqualsAndHashCode.Include
    private final String systemProxyHost;

    @EqualsAndHashCode.Include
    private final int systemProxyPort;

    @EqualsAndHashCode.Include
    private final String proxyHost;

    @EqualsAndHashCode.Include
    private final int proxyPort;

    @EqualsAndHashCode.Include
    private final Credentials proxyCredentials;
}
