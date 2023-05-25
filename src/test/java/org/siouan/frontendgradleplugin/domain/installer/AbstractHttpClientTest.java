package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void should_return_local_file_http_response_when_protocol_is_file() throws IOException {
        assertThat(httpClient.sendGetRequest(temporaryDirectoryPath.toUri().toURL(), null, null)).isInstanceOf(
            LocalFileHttpResponse.class);
    }

    @Test
    void should_return_real_http_response_when_protocol_is_not_file() throws IOException {
        assertThat(httpClient.sendGetRequest(new URL("http://localhost"), null, null)).isEqualTo(httpResponse);
    }

    private static class HttpClientImpl extends AbstractHttpClient {

        private final HttpResponse httpResponse;

        public HttpClientImpl(final HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
        }

        @Override
        protected HttpResponse getRemoteResource(final URL resourceUrl, final Credentials credentials,
            final ProxySettings proxySettings) {
            return httpResponse;
        }
    }
}
