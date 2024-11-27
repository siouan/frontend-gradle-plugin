package org.siouan.frontendgradleplugin.domain.installer;

/**
 * Provider of HTTP clients.
 *
 * @since 4.0.1
 */
public interface HttpClientProvider {

    /**
     * Gets an HTTP client.
     *
     * @return HTTP client.
     */
    HttpClient getInstance();
}
