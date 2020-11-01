package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpHost;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.siouan.frontendgradleplugin.domain.exception.HttpClientException;
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
        @Nullable final ProxySettings proxySettings) throws IOException, HttpClientException {
        final BasicScheme basicScheme = new BasicScheme();
        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        final AuthCache authCache = new BasicAuthCache();
        final HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setAuthCache(authCache);
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        if (credentials != null) {
            // Basic authentication for distribution server
            final HttpHost serverHost = new HttpHost(resourceUrl.getHost(), resourceUrl.getPort());
            try {
                basicScheme.processChallenge(new BasicHeader(AUTH.WWW_AUTH, buildAuthorization(credentials)));
            } catch (MalformedChallengeException e) {
                throw new HttpClientException(e);
            }
            authCache.put(serverHost, basicScheme);
            registerCredentials(credentialsProvider, serverHost, credentials.getUsername(), credentials.getPassword());
        }

        // Proxy management
        if (proxySettings != null) {
            final HttpHost proxyServerHost = new HttpHost(proxySettings.getProxyHost(), proxySettings.getProxyPort());
            final Credentials proxyCredentials = proxySettings.getCredentials();
            if (proxyCredentials != null) {
                // Basic authentication for proxy server
                try {
                    basicScheme.processChallenge(
                        new BasicHeader(AUTH.PROXY_AUTH, buildAuthorization(proxyCredentials)));
                } catch (MalformedChallengeException e) {
                    throw new HttpClientException(e);
                }
                authCache.put(proxyServerHost, basicScheme);
                registerCredentials(credentialsProvider, proxyServerHost, proxyCredentials.getUsername(),
                    proxyCredentials.getPassword());
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

    private void registerCredentials(@Nonnull final CredentialsProvider credentialsProvider,
        @Nonnull final HttpHost host, @Nonnull final String username, @Nonnull final String password) {
        credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(username, password));
    }

    private String buildAuthorization(@Nonnull final Credentials credentials) {
        final String basicCredentials = credentials.getUsername() + ':' + credentials.getPassword();
        return "Basic " + Base64.getEncoder().encodeToString(basicCredentials.getBytes());
    }
}
