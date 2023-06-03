package org.siouan.frontendgradleplugin.domain.installer;

import java.net.Proxy;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Settings to download a resource through a proxy server.
 *
 * @since 3.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProxySettings {

    /**
     * Proxy host.
     */
    @EqualsAndHashCode.Include
    private final String proxyHost;

    /**
     * Proxy port.
     */
    @EqualsAndHashCode.Include
    private final int proxyPort;

    /**
     * Proxy type.
     */
    @EqualsAndHashCode.Include
    private final Proxy.Type proxyType;

    /**
     * Credentials to authenticate on the proxy server.
     */
    @EqualsAndHashCode.Include
    private final Credentials credentials;
}
