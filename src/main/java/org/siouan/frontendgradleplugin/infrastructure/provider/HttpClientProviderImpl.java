package org.siouan.frontendgradleplugin.infrastructure.provider;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.HttpClient;
import org.siouan.frontendgradleplugin.domain.provider.HttpClientProvider;
import org.siouan.frontendgradleplugin.infrastructure.httpclient.ApacheHttpClient;

/**
 * A provider returning a HTTP client based on the low-level Apache HTTP client.
 *
 * @since 4.0.1
 */
public class HttpClientProviderImpl implements HttpClientProvider {

    private static final HttpClient INSTANCE = new ApacheHttpClient();

    @Override
    @Nonnull
    public HttpClient getInstance() {
        return INSTANCE;
    }
}
