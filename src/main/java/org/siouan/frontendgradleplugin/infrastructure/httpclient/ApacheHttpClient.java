package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.net.URL;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.HttpResponse;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.usecase.AbstractHttpClient;

/**
 * A HTTP client implementation relying on the Apache low-level HTTP client implementation, and on a short-circuit
 * capability to handle the "file" protocol and support "downloading" local distributions.
 *
 * @since 4.0.1
 */
public class ApacheHttpClient extends AbstractHttpClient {

    @Override
    @Nonnull
    protected HttpResponse getRemoteResource(@Nonnull final URL resourceUrl, @Nullable final Credentials credentials,
        @Nullable final ProxySettings proxySettings) throws IOException {

        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        final HttpClientContext httpClientContext = HttpClientContext.create();
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        if (credentials != null) {
            // Basic authentication for distribution server
            final HttpHost serverHost = new HttpHost(resourceUrl.getHost(), resourceUrl.getPort());
            final BasicScheme basicScheme = new BasicScheme();
            registerCredentials(credentialsProvider, basicScheme, serverHost, credentials.getUsername(),
                credentials.getPassword());
            httpClientContext.resetAuthExchange(serverHost, basicScheme);
        }

        // Proxy management
        if (proxySettings != null) {
            final HttpHost proxyServerHost = new HttpHost(proxySettings.getProxyHost(), proxySettings.getProxyPort());
            final Credentials proxyCredentials = proxySettings.getCredentials();
            if (proxyCredentials != null) {
                final BasicScheme basicScheme = new BasicScheme();
                registerCredentials(credentialsProvider, basicScheme, proxyServerHost, proxyCredentials.getUsername(),
                    proxyCredentials.getPassword());
                httpClientContext.resetAuthExchange(proxyServerHost, basicScheme);
            }
            requestConfigBuilder.setProxy(proxyServerHost);
        }

        final HttpGet httpGet = new HttpGet(resourceUrl.toString());
        httpGet.setConfig(requestConfigBuilder.build());

        final CloseableHttpClient httpClient = HttpClients
            .custom()
            .setDefaultCredentialsProvider(credentialsProvider)
            .build();
        try {
            return new ApacheHttpResponse(httpClient, httpClient.execute(httpGet, httpClientContext));
        } catch (final IOException e) {
            httpClient.close();
            throw e;
        }
    }

    private void registerCredentials(@Nonnull final BasicCredentialsProvider credentialsProvider,
        @Nonnull final BasicScheme basicScheme, @Nonnull final HttpHost host, @Nonnull final String username,
        @Nonnull final String password) {
        final UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(username,
            password.toCharArray());
        credentialsProvider.setCredentials(new AuthScope(host), usernamePasswordCredentials);
        basicScheme.initPreemptive(usernamePasswordCredentials);
    }
}
