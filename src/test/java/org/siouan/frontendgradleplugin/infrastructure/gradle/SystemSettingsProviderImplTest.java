package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.fixture.SystemPropertiesFixture.getSystemJvmArch;
import static org.siouan.frontendgradleplugin.test.fixture.SystemPropertiesFixture.getSystemOsName;

import org.gradle.api.provider.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.SystemProperties;

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
    void setUp() {
        when(systemExtension.getJvmArch()).thenReturn(stringProvider);
        when(systemExtension.getOsName()).thenReturn(stringProvider);
        when(systemExtension.getHttpProxyHost()).thenReturn(stringProvider);
        when(systemExtension.getHttpProxyPort()).thenReturn(stringProvider);
        when(systemExtension.getHttpsProxyHost()).thenReturn(stringProvider);
        when(systemExtension.getHttpsProxyPort()).thenReturn(stringProvider);
        when(systemExtension.getNonProxyHosts()).thenReturn(stringProvider);
        systemSettingsProvider = new SystemSettingsProviderImpl(systemExtension, DEFAULT_HTTP_PROXY_PORT,
            DEFAULT_HTTPS_PROXY_PORT);
    }

    @Test
    void should_return_jvm_arch_from_extension() {
        final String jvmArch = getSystemJvmArch();
        when(stringProvider.get()).thenReturn(jvmArch);

        assertThat(systemSettingsProvider.getSystemJvmArch()).isEqualTo(jvmArch);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    void should_return_os_name_from_extension() {
        final String osName = getSystemOsName();
        when(stringProvider.get()).thenReturn(osName);

        assertThat(systemSettingsProvider.getSystemOsName()).isEqualTo(osName);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTP_PROXY_HOST)
    void should_return_http_proxy_host_from_extension() {
        final String httpProxyHost = "http-proxy";
        when(stringProvider.getOrNull()).thenReturn(httpProxyHost);

        assertThat(systemSettingsProvider.getHttpProxyHost()).isEqualTo(httpProxyHost);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTP_PROXY_HOST)
    void should_return_no_http_proxy_port_from_extension() {
        when(stringProvider.getOrNull()).thenReturn(null);

        assertThat(systemSettingsProvider.getHttpProxyPort()).isEqualTo(DEFAULT_HTTP_PROXY_PORT);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTP_PROXY_PORT)
    void should_return_http_proxy_port_when_non_blank() {
        final String httpProxyPort = "743";
        when(stringProvider.getOrNull()).thenReturn(httpProxyPort);

        assertThat(systemSettingsProvider.getHttpProxyPort()).hasToString(httpProxyPort);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTP_PROXY_PORT)
    void should_return_no_http_non_proxy_hosts_when_system_property_is_null_or_blank() {
        when(stringProvider.getOrNull()).thenReturn(null);

        assertThat(systemSettingsProvider.getNonProxyHosts()).isEmpty();

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.NON_PROXY_HOSTS)
    void should_return_http_non_proxy_hosts_when_system_property_is_defined() {
        when(stringProvider.getOrNull()).thenReturn("localhost|127.*|[::1]");

        assertThat(systemSettingsProvider.getNonProxyHosts()).containsExactlyInAnyOrder("localhost", "127.*", "[::1]");

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTPS_PROXY_HOST)
    void should_return_https_proxy_host_from_extension() {
        final String httpsProxyHost = "https-proxy";
        when(stringProvider.getOrNull()).thenReturn(httpsProxyHost);

        assertThat(systemSettingsProvider.getHttpsProxyHost()).isEqualTo(httpsProxyHost);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTPS_PROXY_PORT)
    void should_return_no_https_proxy_port_from_extension() {
        when(stringProvider.getOrNull()).thenReturn(null);

        assertThat(systemSettingsProvider.getHttpsProxyPort()).isEqualTo(DEFAULT_HTTPS_PROXY_PORT);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }

    @Test
    @ClearSystemProperty(key = SystemProperties.HTTPS_PROXY_PORT)
    void should_return_https_proxy_port_when_non_blank() {
        final String httpsProxyPort = "1988";
        when(stringProvider.getOrNull()).thenReturn(httpsProxyPort);

        assertThat(systemSettingsProvider.getHttpsProxyPort()).hasToString(httpsProxyPort);

        verifyNoMoreInteractions(systemExtension, stringProvider);
    }
}
