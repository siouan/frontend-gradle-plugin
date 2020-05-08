package org.siouan.frontendgradleplugin.domain.provider;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import javax.annotation.Nonnull;

/**
 * Provider of Java {@link URLConnection}.
 *
 * @since 3.0.0
 */
public interface URLConnectionProvider {

    /**
     * Creates a connection on the given URL. The connection is not established yet.
     *
     * @param url URL.
     * @param proxy Proxy.
     * @return Connection.
     * @throws IOException In case an I/O error occurs.
     */
    @Nonnull
    URLConnection openConnection(@Nonnull URL url, @Nonnull Proxy proxy) throws IOException;
}
