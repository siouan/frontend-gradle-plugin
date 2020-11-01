package org.siouan.frontendgradleplugin.domain.model;

import java.io.IOException;
import java.net.URL;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.HttpClientException;

/**
 * This class represents a HTTP client used to download resources.
 *
 * @since 4.0.1
 */
public interface HttpClient {

    /**
     * Sends a HTTP GET request and returns the HTTP response received. If the resource URL uses the "file" protocol,
     * the client may not send a real HTTP request, and rather a HTTP response using the local file system instead.
     *
     * @param resourceUrl Resource URL.
     * @param credentials Optional credentials for BASIC authentication server-side.
     * @param proxySettings Optional proxy settings.
     * @return The HTTP response (must be retrieved in a {@code try-with-resources} block, or closed manually when
     * processing is completed).
     * @throws IOException If an error occurs while sending the request, during connection, or when the response is
     * received.
     * @throws HttpClientException If an error occurs during a client opertion.
     */
    @Nonnull
    HttpResponse sendGetRequest(URL resourceUrl, Credentials credentials, ProxySettings proxySettings)
        throws IOException, HttpClientException;
}
