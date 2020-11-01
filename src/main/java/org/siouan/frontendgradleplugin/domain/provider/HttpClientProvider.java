package org.siouan.frontendgradleplugin.domain.provider;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.HttpClient;

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
    @Nonnull
    HttpClient getInstance();
}
