package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.Proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.test.fixture.CredentialsFixture;

@ExtendWith(MockitoExtension.class)
class SelectProxySettingsTest {

    private static final String PLUGIN_PROXY_HOST = "foo.bar.baz";

    private static final int PLUGIN_PROXY_PORT = 843;

    private static final String SYSTEM_PROXY_HOST = "proxy.local";

    private static final int SYSTEM_PROXY_PORT = 6375;

    @InjectMocks
    private SelectProxySettings usecase;

    @Test
    void shouldReturnNullIfSystemProxyHostAndPluginProxyHostAreNull() {
        assertThat(usecase.execute(null, SYSTEM_PROXY_PORT, null, PLUGIN_PROXY_PORT, null)).isNull();
    }

    @Test
    void shouldReturnSystemProxyIfSystemProxyHostIsDefinedAndPluginProxyHostIsNull() {
        assertThat(usecase.execute(SYSTEM_PROXY_HOST, SYSTEM_PROXY_PORT, null, PLUGIN_PROXY_PORT, null)).satisfies(
            proxySettings -> {
                assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
                assertThat(proxySettings.getProxyHost()).isEqualTo(SYSTEM_PROXY_HOST);
                assertThat(proxySettings.getProxyPort()).isEqualTo(SYSTEM_PROXY_PORT);
                assertThat(proxySettings.getCredentials()).isNull();
            });
    }

    @Test
    void shouldReturnSystemProxyWithCredentialsIfSystemProxyHostIsDefinedAndPluginProxyHostIsNull() {
        final Credentials credentials = CredentialsFixture.someCredentials();

        assertThat(
            usecase.execute(SYSTEM_PROXY_HOST, SYSTEM_PROXY_PORT, null, PLUGIN_PROXY_PORT, credentials)).satisfies(
            proxySettings -> {
                assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
                assertThat(proxySettings.getProxyHost()).isEqualTo(SYSTEM_PROXY_HOST);
                assertThat(proxySettings.getProxyPort()).isEqualTo(SYSTEM_PROXY_PORT);
                assertThat(proxySettings.getCredentials()).isEqualTo(credentials);
            });
    }

    @Test
    void shouldReturnPluginProxyIfSystemProxyHostIsNullAndPluginProxyHostIsDefined() {
        assertThat(usecase.execute(null, SYSTEM_PROXY_PORT, PLUGIN_PROXY_HOST, PLUGIN_PROXY_PORT, null)).satisfies(
            proxySettings -> {
                assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
                assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
                assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
                assertThat(proxySettings.getCredentials()).isNull();
            });
    }

    @Test
    void shouldReturnPluginProxyWithCredentialsIfSystemProxyHostIsNullAndPluginProxyHostIsDefined() {
        final Credentials credentials = CredentialsFixture.someCredentials();

        assertThat(usecase.execute(null, SYSTEM_PROXY_PORT, PLUGIN_PROXY_HOST, PLUGIN_PROXY_PORT, credentials)).satisfies(
            proxySettings -> {
                assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
                assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
                assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
                assertThat(proxySettings.getCredentials()).isEqualTo(credentials);
            });
    }

    @Test
    void shouldReturnPluginProxyIfSystemProxyHostAndPluginProxyHostAreDefined() {
        assertThat(usecase.execute(SYSTEM_PROXY_HOST, SYSTEM_PROXY_PORT, PLUGIN_PROXY_HOST, PLUGIN_PROXY_PORT, null)).satisfies(
            proxySettings -> {
                assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
                assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
                assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
                assertThat(proxySettings.getCredentials()).isNull();
            });
    }

    @Test
    void shouldReturnPluginProxyWithCredentialsIfSystemProxyHostAndPluginProxyHostAreDefined() {
        final Credentials credentials = CredentialsFixture.someCredentials();

        assertThat(usecase.execute(SYSTEM_PROXY_HOST, SYSTEM_PROXY_PORT, PLUGIN_PROXY_HOST, PLUGIN_PROXY_PORT, credentials)).satisfies(
            proxySettings -> {
                assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
                assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
                assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
                assertThat(proxySettings.getCredentials()).isEqualTo(credentials);
            });
    }
}
