package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.HttpClient;
import org.siouan.frontendgradleplugin.domain.model.HttpResponse;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.infrastructure.httpclient.LocalFileHttpResponse;

@ExtendWith(MockitoExtension.class)
class AbstractHttpClientTest {

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private HttpResponse httpResponse;

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = new HttpClientImpl(httpResponse);
    }

    @Test
    void shouldReturnLocalFileHttpResponseWhenProtocolIsFile() throws IOException {
        assertThat(httpClient.sendGetRequest(temporaryDirectoryPath.toUri().toURL(), null, null)).isInstanceOf(
            LocalFileHttpResponse.class);
    }

    @Test
    void shouldReturnRealHttpResponseWhenProtocolIsNotFile() throws IOException {
        assertThat(httpClient.sendGetRequest(new URL("http://localhost"), null, null)).isEqualTo(httpResponse);
    }

    private static class HttpClientImpl extends AbstractHttpClient {

        private final HttpResponse httpResponse;

        public HttpClientImpl(@Nonnull final HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
        }

        @Override
        protected HttpResponse getRemoteResource(@Nonnull final URL resourceUrl,
            @Nullable final Credentials credentials, @Nullable final ProxySettings proxySettings) {
            return httpResponse;
        }
    }
}
