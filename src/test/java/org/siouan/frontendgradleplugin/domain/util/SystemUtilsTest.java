package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.test.fixture.SystemPropertyFixture;

@ExtendWith(MockitoExtension.class)
class SystemUtilsTest {

    @BeforeEach
    @AfterEach
    void clearNetworkProperties() {
        System.setProperty("http.proxyHost", "");
        System.setProperty("http.proxyPort", "");
        System.setProperty("http.nonProxyHosts", "");
        System.setProperty("https.proxyHost", "");
        System.setProperty("https.proxyPort", "");
    }

    @Test
    void shouldReturnANonNullStringWhenGettingJvmArch() {
        assertThat(SystemPropertyFixture.getSystemJvmArch()).isNotNull();
    }

    @Test
    void shouldReturnANonNullStringWhenGettingOsName() {
        assertThat(SystemPropertyFixture.getSystemOsName()).isNotNull();
    }

    @Test
    void shouldReturnHttpProxyHost() {
        System.setProperty("http.proxyHost", "http-proxy");

        assertThat(SystemPropertyFixture.getHttpProxyHost()).isEqualTo("http-proxy");
    }

    @Test
    void shouldReturnNoHttpProxyPortWhenUndefined() {
        assertThat(SystemPropertyFixture.getHttpProxyPort()).isEmpty();
    }

    @Test
    void shouldReturnHttpProxyPortWhenNonBlank() {
        System.setProperty("http.proxyPort", "743");

        assertThat(SystemPropertyFixture.getHttpProxyPort()).contains(743);
    }

    @Test
    void shouldReturnNoHttpNonProxyHostsWhenSystemPropertyIsNullOrBlank() {
        assertThat(SystemPropertyFixture.getNonProxyHosts()).isEmpty();
    }

    @Test
    void shouldReturnHttpNonProxyHostsWhenSystemPropertyIsDefined() {
        System.setProperty("http.nonProxyHosts", "localhost|127.*|[::1]");

        assertThat(SystemPropertyFixture.getNonProxyHosts()).containsExactlyInAnyOrder("localhost", "127.*", "[::1]");
    }

    @Test
    void shouldReturnHttpsProxyHost() {
        System.setProperty("https.proxyHost", "https-proxy");

        assertThat(SystemPropertyFixture.getHttpsProxyHost()).isEqualTo("https-proxy");
    }

    @Test
    void shouldReturnNoHttpsProxyPortWhenUndefined() {
        assertThat(SystemPropertyFixture.getHttpsProxyPort()).isEmpty();
    }

    @Test
    void shouldReturnHttpsProxyPortWhenNonBlank() {
        System.setProperty("https.proxyPort", "1988");

        assertThat(SystemPropertyFixture.getHttpsProxyPort()).contains(1988);
    }
}
