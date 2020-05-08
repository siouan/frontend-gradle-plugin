package org.siouan.frontendgradleplugin.infrastructure.provider;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.provider.URLConnectionProvider;

/**
 * A provider of {@link URLConnection} instances.
 *
 * @since 3.0.0
 */
public class URLConnectionProviderImpl implements URLConnectionProvider {

    @Nonnull
    @Override
    public URLConnection openConnection(@Nonnull final URL url, @Nonnull final Proxy proxy) throws IOException {
        return url.openConnection(proxy);
    }
}
