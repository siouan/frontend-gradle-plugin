package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.Serializable;

import org.siouan.frontendgradleplugin.domain.installer.HttpClient;
import org.siouan.frontendgradleplugin.domain.installer.HttpClientProvider;

/**
 * A provider returning a HTTP client based on the low-level Apache HTTP client.
 *
 * @since 4.0.1
 */
public class HttpClientProviderImpl implements HttpClientProvider, Serializable {

    private static final HttpClient INSTANCE = new ApacheHttpClient();

    private static final long serialVersionUID = -5442300705570408127L;

    @Override
    public HttpClient getInstance() {
        return INSTANCE;
    }
}
