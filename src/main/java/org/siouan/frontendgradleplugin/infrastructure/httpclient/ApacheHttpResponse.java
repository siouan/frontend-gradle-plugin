package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.siouan.frontendgradleplugin.domain.model.HttpResponse;

/**
 * @since 4.0.1
 */
public class ApacheHttpResponse implements HttpResponse {

    private final CloseableHttpClient httpClient;

    private final CloseableHttpResponse httpResponse;

    public ApacheHttpResponse(@Nonnull final CloseableHttpClient httpClient,
        @Nonnull final CloseableHttpResponse httpResponse) {
        this.httpClient = httpClient;
        this.httpResponse = httpResponse;
    }

    @Override
    @Nonnull
    public String getProtocol() {
        return httpResponse.getVersion().getProtocol();
    }

    @Override
    @Nonnull
    public String getVersion() {
        return httpResponse.getVersion().getMajor() + "." + httpResponse.getVersion().getMinor();
    }

    @Override
    public int getStatusCode() {
        return httpResponse.getCode();
    }

    @Override
    @Nonnull
    public String getReasonPhrase() {
        return httpResponse.getReasonPhrase();
    }

    @Override
    @Nonnull
    public InputStream getInputStream() throws IOException {
        final HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            return InputStream.nullInputStream();
        }

        return httpEntity.getContent();
    }

    @Override
    public void close() throws IOException {
        httpResponse.close();
        httpClient.close();
    }
}
