package org.siouan.frontendgradleplugin.domain.installer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.siouan.frontendgradleplugin.infrastructure.httpclient.LocalFileHttpResponse;

/**
 * Base implementation of a HTTP client that provides a short-circuit capability to handle the "file" protocol and
 * support "downloading" local distributions.
 *
 * @since 4.0.1
 */
public abstract class AbstractHttpClient implements HttpClient {

    @Override
    public HttpResponse sendGetRequest(final URL resourceUrl, final Credentials credentials,
        final ProxySettings proxySettings) throws IOException {
        if (resourceUrl.getProtocol().equals(LocalFileHttpResponse.PROTOCOL)) {
            return getLocalResource(resourceUrl);
        }

        return getRemoteResource(resourceUrl, credentials, proxySettings);
    }

    /**
     * Builds an HTTP response for a local resource.
     *
     * @param resourceUrl Resource URL
     * @return HTTP response.
     */
    private HttpResponse getLocalResource(final URL resourceUrl) {
        try {
            return LocalFileHttpResponse.builder().localFilePath(Paths.get(resourceUrl.toURI())).build();
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Sends a HTTP GET request and returns the HTTP response received.
     *
     * @param resourceUrl Resource URL.
     * @param credentials Optional credentials for BASIC authentication server-side.
     * @param proxySettings Optional proxy settings.
     * @return The HTTP response (must be retrieved in a {@code try-with-resources} block, or closed manually when
     * processing is completed).
     * @throws IOException If an error occurs while sending the request, during connection, or when the response is
     * received.
     */
    protected abstract HttpResponse getRemoteResource(final URL resourceUrl, final Credentials credentials,
        final ProxySettings proxySettings) throws IOException;
}
