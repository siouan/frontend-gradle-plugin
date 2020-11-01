package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
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
        return httpResponse.getStatusLine().getProtocolVersion().getProtocol();
    }

    @Override
    @Nonnull
    public String getVersion() {
        return httpResponse.getStatusLine().getProtocolVersion().getMajor() + "." + httpResponse
            .getStatusLine()
            .getProtocolVersion()
            .getMinor();
    }

    @Override
    public int getStatusCode() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    @Override
    @Nonnull
    public String getReasonPhrase() {
        return httpResponse.getStatusLine().getReasonPhrase();
    }

    @Override
    @Nonnull
    public InputStream getInputStream() throws IOException {
        final HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            return new ByteArrayInputStream(new byte[] {});
        }

        return httpEntity.getContent();
    }

    @Override
    public void close() throws IOException {
        httpResponse.close();
        httpClient.close();
    }
}
