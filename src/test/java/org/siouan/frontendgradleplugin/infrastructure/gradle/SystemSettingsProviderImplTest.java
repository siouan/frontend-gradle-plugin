package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gradle.api.provider.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.test.fixture.SystemPropertyFixture;

@ExtendWith(MockitoExtension.class)
class SystemSettingsProviderImplTest {

    private static final int DEFAULT_HTTP_PROXY_PORT = 80;

    private static final int DEFAULT_HTTPS_PROXY_PORT = 443;

    @Mock
    private Provider<String> stringProvider;

    @Mock
    private SystemExtension systemExtension;

    private SystemSettingsProviderImpl systemSettingsProvider;

    @BeforeEach
    void clearNetworkProperties() {
        when(systemExtension.getHttpProxyHost()).thenReturn(stringProvider);
        when(systemExtension.getHttpProxyPort()).thenReturn(stringProvider);
        when(systemExtension.getHttpsProxyHost()).thenReturn(stringProvider);
        when(systemExtension.getHttpsProxyPort()).thenReturn(stringProvider);
        when(systemExtension.getJvmArch()).thenReturn(stringProvider);
        when(systemExtension.getNodejsHomePath()).thenReturn(stringProvider);
        when(systemExtension.getNonProxyHosts()).thenReturn(stringProvider);
        when(systemExtension.getOsName()).thenReturn(stringProvider);
        when(systemExtension.getYarnHomePath()).thenReturn(stringProvider);
        systemSettingsProvider = new SystemSettingsProviderImpl(systemExtension, DEFAULT_HTTP_PROXY_PORT,
            DEFAULT_HTTPS_PROXY_PORT);
    }

    @Test
    void shouldReturnJvmArchFromExtension() {
        final String jvmArch = SystemPropertyFixture.getSystemJvmArch();
        when(stringProvider.get()).thenReturn(jvmArch);

        assertThat(systemSettingsProvider.getSystemJvmArch()).isEqualTo(jvmArch);
    }

    @Test
    void shouldReturnOsNameFromExtension() {
        final String osName = SystemPropertyFixture.getSystemOsName();
        when(stringProvider.get()).thenReturn(osName);

        assertThat(systemSettingsProvider.getSystemOsName()).isEqualTo(osName);
    }

    @Test
    void shouldReturnHttpProxyHostFromExtension() {
        final String httpProxyHost = "http-proxy";
        when(stringProvider.getOrNull()).thenReturn(httpProxyHost);

        assertThat(systemSettingsProvider.getHttpProxyHost()).isEqualTo(httpProxyHost);
    }

    @Test
    void shouldReturnNoHttpProxyPortFromExtension() {
        when(stringProvider.getOrNull()).thenReturn(null);

        assertThat(systemSettingsProvider.getHttpProxyPort()).isEqualTo(DEFAULT_HTTP_PROXY_PORT);

        verifyNoMoreInteractions(systemExtension);
    }

    @Test
    void shouldReturnHttpProxyPortWhenNonBlank() {
        final String httpProxyPort = "743";
        when(stringProvider.getOrNull()).thenReturn(httpProxyPort);

        assertThat(systemSettingsProvider.getHttpProxyPort()).hasToString(httpProxyPort);
    }

    @Test
    void shouldReturnNoHttpNonProxyHostsWhenSystemPropertyIsNullOrBlank() {
        when(stringProvider.getOrNull()).thenReturn(null);

        assertThat(systemSettingsProvider.getNonProxyHosts()).isEmpty();
    }

    @Test
    void shouldReturnHttpNonProxyHostsWhenSystemPropertyIsDefined() {
        when(stringProvider.getOrNull()).thenReturn("localhost|127.*|[::1]");

        assertThat(systemSettingsProvider.getNonProxyHosts()).containsExactlyInAnyOrder("localhost", "127.*", "[::1]");
    }

    @Test
    void shouldReturnHttpsProxyHostFromExtension() {
        final String httpsProxyHost = "https-proxy";
        when(stringProvider.getOrNull()).thenReturn(httpsProxyHost);

        assertThat(systemSettingsProvider.getHttpsProxyHost()).isEqualTo(httpsProxyHost);
    }

    @Test
    void shouldReturnNoHttpsProxyPortFromExtension() {
        when(stringProvider.getOrNull()).thenReturn(null);

        assertThat(systemSettingsProvider.getHttpsProxyPort()).isEqualTo(DEFAULT_HTTPS_PROXY_PORT);
    }

    @Test
    void shouldReturnHttpsProxyPortWhenNonBlank() {
        final String httpsProxyPort = "1988";
        when(stringProvider.getOrNull()).thenReturn(httpsProxyPort);

        assertThat(systemSettingsProvider.getHttpsProxyPort()).hasToString(httpsProxyPort);
    }
}
