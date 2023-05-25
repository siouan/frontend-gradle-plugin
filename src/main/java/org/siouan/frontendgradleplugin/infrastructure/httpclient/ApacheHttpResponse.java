package org.siouan.frontendgradleplugin.infrastructure.httpclient;

import java.io.IOException;
import java.io.InputStream;

import lombok.Builder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.siouan.frontendgradleplugin.domain.installer.HttpResponse;

/**
 * @since 4.0.1
 */
@Builder
public class ApacheHttpResponse implements HttpResponse {

    private final CloseableHttpClient httpClient;

    private final ClassicHttpResponse httpResponse;

    @Override
    public String getProtocol() {
        return httpResponse.getVersion().getProtocol();
    }

    @Override
    public String getVersion() {
        return httpResponse.getVersion().getMajor() + "." + httpResponse.getVersion().getMinor();
    }

    @Override
    public int getStatusCode() {
        return httpResponse.getCode();
    }

    @Override
    public String getReasonPhrase() {
        return httpResponse.getReasonPhrase();
    }

    @Override
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
