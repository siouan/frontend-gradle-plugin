package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gradle.api.internal.provider.DefaultProviderFactory;
import org.gradle.api.provider.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.SystemProperties;

@ExtendWith(MockitoExtension.class)
class SystemProvidersTest {

    @Mock
    private Provider<String> httpProxyHostProvider;

    @Mock
    private Provider<String> httpProxyPortProvider;

    @Mock
    private Provider<String> httpsProxyHostProvider;

    @Mock
    private Provider<String> httpsProxyPortProvider;

    @Mock
    private Provider<String> nonProxyHosts;

    @Mock
    private Provider<String> jvmArchProvider;

    @Mock
    private Provider<String> osNameProvider;

    @Mock
    private DefaultProviderFactory providerFactory;

    private SystemProviders systemProviders;

    @BeforeEach
    void setUp() {
        when(providerFactory.systemProperty(SystemProperties.HTTP_PROXY_HOST)).thenReturn(httpProxyHostProvider);
        when(providerFactory.systemProperty(SystemProperties.HTTP_PROXY_PORT)).thenReturn(httpProxyPortProvider);
        when(providerFactory.systemProperty(SystemProperties.HTTPS_PROXY_HOST)).thenReturn(httpsProxyHostProvider);
        when(providerFactory.systemProperty(SystemProperties.HTTPS_PROXY_PORT)).thenReturn(httpsProxyPortProvider);
        when(providerFactory.systemProperty(SystemProperties.NON_PROXY_HOSTS)).thenReturn(nonProxyHosts);
        when(providerFactory.systemProperty(SystemProperties.JVM_ARCH)).thenReturn(jvmArchProvider);
        when(providerFactory.systemProperty(SystemProperties.OS_NAME)).thenReturn(osNameProvider);
        systemProviders = new SystemProviders(providerFactory);
    }

    @Test
    void should_return_system_properties() {
        assertThat(systemProviders.getHttpProxyHost()).isEqualTo(httpProxyHostProvider);
        assertThat(systemProviders.getHttpProxyPort()).isEqualTo(httpProxyPortProvider);
        assertThat(systemProviders.getHttpsProxyHost()).isEqualTo(httpsProxyHostProvider);
        assertThat(systemProviders.getHttpsProxyPort()).isEqualTo(httpsProxyPortProvider);
        assertThat(systemProviders.getNonProxyHosts()).isEqualTo(nonProxyHosts);
        assertThat(systemProviders.getJvmArch()).isEqualTo(jvmArchProvider);
        assertThat(systemProviders.getOsName()).isEqualTo(osNameProvider);

        verifyNoMoreInteractions(httpProxyHostProvider, httpProxyPortProvider, httpsProxyHostProvider,
            httpsProxyPortProvider, nonProxyHosts, jvmArchProvider, osNameProvider);
    }
}
