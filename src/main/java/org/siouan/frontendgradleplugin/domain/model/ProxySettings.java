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

    private final Proxy proxy;

    private final Credentials serverCredentials;

    /**
     * Builds proxy settings.
     *
     * @param proxy Proxy details.
     * @param serverCredentials Credentials to authenticate on the proxy server.
     */
    public ProxySettings(@Nonnull final Proxy proxy, @Nullable final Credentials serverCredentials) {
        this.proxy = proxy;
        this.serverCredentials = serverCredentials;
    }

    /**
     * Gets the proxy details.
     *
     * @return Proxy details.
     */
    @Nonnull
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Gets credentials used to authenticate on the proxy server.
     *
     * @return Credentials.
     */
    @Nullable
    public Credentials getServerCredentials() {
        return serverCredentials;
    }
}
