package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertThat(SystemUtils.getSystemJvmArch()).isNotNull();
    }

    @Test
    void shouldReturnANonNullStringWhenGettingOsName() {
        assertThat(SystemUtils.getSystemOsName()).isNotNull();
    }

    @Test
    void shouldReturnHttpProxyHost() {
        System.setProperty("http.proxyHost", "http-proxy");

        assertThat(SystemUtils.getHttpProxyHost()).isEqualTo("http-proxy");
    }

    @Test
    void shouldReturnNoHttpProxyPortWhenUndefined() {
        assertThat(SystemUtils.getHttpProxyPort()).isEmpty();
    }

    @Test
    void shouldReturnHttpProxyPortWhenNonBlank() {
        System.setProperty("http.proxyPort", "743");

        assertThat(SystemUtils.getHttpProxyPort()).contains(743);
    }

    @Test
    void shouldReturnNoHttpNonProxyHostsWhenSystemPropertyIsNullOrBlank() {
        assertThat(SystemUtils.getNonProxyHosts()).isEmpty();
    }

    @Test
    void shouldReturnHttpNonProxyHostsWhenSystemPropertyIsDefined() {
        System.setProperty("http.nonProxyHosts", "localhost|127.*|[::1]");

        assertThat(SystemUtils.getNonProxyHosts()).containsExactlyInAnyOrder("localhost", "127.*", "[::1]");
    }

    @Test
    void shouldReturnHttpsProxyHost() {
        System.setProperty("https.proxyHost", "https-proxy");

        assertThat(SystemUtils.getHttpsProxyHost()).isEqualTo("https-proxy");
    }

    @Test
    void shouldReturnNoHttpsProxyPortWhenUndefined() {
        assertThat(SystemUtils.getHttpsProxyPort()).isEmpty();
    }

    @Test
    void shouldReturnHttpsProxyPortWhenNonBlank() {
        System.setProperty("https.proxyPort", "1988");

        assertThat(SystemUtils.getHttpsProxyPort()).contains(1988);
    }
}
