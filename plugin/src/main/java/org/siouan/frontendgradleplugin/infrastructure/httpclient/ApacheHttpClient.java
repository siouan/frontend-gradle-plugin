package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

import org.apache.hc.client5.http.ContextBuilder;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.siouan.frontendgradleplugin.domain.installer.AbstractHttpClient;
import org.siouan.frontendgradleplugin.domain.installer.Credentials;
import org.siouan.frontendgradleplugin.domain.installer.HttpResponse;
import org.siouan.frontendgradleplugin.domain.installer.ProxySettings;

/**
 * A HTTP client implementation relying on the Apache low-level HTTP client implementation, and on a short-circuit
 * capability to handle the "file" protocol and support "downloading" local distributions.
 *
 * @since 4.0.1
 */
public class ApacheHttpClient extends AbstractHttpClient {

    @Override
    protected HttpResponse getRemoteResource(final URL resourceUrl, final Credentials credentials,
        final ProxySettings proxySettings) throws IOException {
        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        final ContextBuilder contextBuilder = ContextBuilder.create();

        if (credentials != null) {
            // Basic authentication for the distribution server
            registerCredentials(contextBuilder,
                new HttpHost(resourceUrl.getProtocol(), resourceUrl.getHost(), resourceUrl.getPort()),
                credentials.getUsername(), credentials.getPassword());
        }

        // Proxy management
        final HttpHost proxyServerHost;
        if (proxySettings.getProxyType() == Proxy.Type.DIRECT) {
            proxyServerHost = null;
        } else {
            proxyServerHost = new HttpHost(proxySettings.getProxyHost(), proxySettings.getProxyPort());
            final Credentials proxyCredentials = proxySettings.getCredentials();
            if (proxyCredentials != null) {
                registerCredentials(contextBuilder, proxyServerHost, proxyCredentials.getUsername(),
                    proxyCredentials.getPassword());
            }
        }

        final HttpGet httpGet = new HttpGet(resourceUrl.toString());
        httpGet.setConfig(requestConfigBuilder.build());

        // By default, the HTTP client is closed automatically when the caller closes the response. However, in case of
        // error, it must be closed to release any low-level resource allocated and restore a clean state to the caller.
        final CloseableHttpClient httpClient = HttpClients.custom().setProxy(proxyServerHost).build();
        try {
            return new ApacheHttpResponse(httpClient, httpClient.executeOpen(null, httpGet, contextBuilder.build()));
        } catch (final IOException e) {
            httpClient.close();
            throw e;
        }
    }

    private void registerCredentials(final ContextBuilder contextBuilder, final HttpHost host, final String username,
        final String password) {
        contextBuilder.preemptiveBasicAuth(host, new UsernamePasswordCredentials(username, password.toCharArray()));
    }
}
