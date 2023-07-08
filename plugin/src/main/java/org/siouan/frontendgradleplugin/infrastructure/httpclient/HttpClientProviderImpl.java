package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import org.siouan.frontendgradleplugin.domain.installer.HttpClient;
import org.siouan.frontendgradleplugin.domain.installer.HttpClientProvider;

/**
 * A provider returning a HTTP client based on the low-level Apache HTTP client.
 *
 * @since 4.0.1
 */
public class HttpClientProviderImpl implements HttpClientProvider {

    private static final HttpClient INSTANCE = new ApacheHttpClient();

    @Override
    public HttpClient getInstance() {
        return INSTANCE;
    }
}
