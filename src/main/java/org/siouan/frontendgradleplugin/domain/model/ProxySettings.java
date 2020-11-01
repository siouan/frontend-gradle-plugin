package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings to download a resource through a proxy server.
 *
 * @since 3.0.0
 */
public class ProxySettings {

    private final String proxyHost;

    private final int proxyPort;

    private final Proxy.Type proxyType;

    private final Credentials credentials;

    /**
     * Builds proxy settings.
     *
     * @param proxyType Proxy type.
     * @param proxyHost Proxy host.
     * @param proxyPort Proxy port.
     * @param credentials Credentials to authenticate on the proxy server.
     */
    public ProxySettings(@Nonnull final Proxy.Type proxyType, @Nonnull final String proxyHost, final int proxyPort,
        @Nullable final Credentials credentials) {
        this.proxyType = proxyType;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.credentials = credentials;
    }

    /**
     * Gets the type of proxy.
     *
     * @return Proxy type.
     */
    @Nonnull
    public Proxy.Type getProxyType() {
        return proxyType;
    }

    /**
     * Gets the proxy host.
     *
     * @return Proxy host.
     */
    @Nonnull
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * Gets the proxy port.
     *
     * @return Proxy port.
     */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * Gets credentials used to authenticate on the proxy server.
     *
     * @return Credentials.
     */
    @Nullable
    public Credentials getCredentials() {
        return credentials;
    }
}
