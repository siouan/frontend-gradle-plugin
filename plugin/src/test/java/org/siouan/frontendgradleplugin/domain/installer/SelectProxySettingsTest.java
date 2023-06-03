package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.siouan.frontendgradleplugin.test.fixture.CredentialsFixture.someCredentials;

import java.net.Proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelectProxySettingsTest {

    private static final String PLUGIN_PROXY_HOST = "foo.bar.baz";

    private static final int PLUGIN_PROXY_PORT = 843;

    private static final String SYSTEM_PROXY_HOST = "proxy.local";

    private static final int SYSTEM_PROXY_PORT = 6375;

    @InjectMocks
    private SelectProxySettings usecase;

    @Test
    void should_return_null_if_system_proxy_host_and_plugin_proxy_host_are_null() {
        assertThat(usecase.execute(SelectProxySettingsCommand.builder().build())).isNull();
    }

    @Test
    void should_return_system_proxy_if_system_proxy_host_is_defined_and_plugin_proxy_host_is_null() {
        assertThat(usecase.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyHost(SYSTEM_PROXY_HOST)
            .systemProxyPort(SYSTEM_PROXY_PORT)
            .build())).satisfies(proxySettings -> {
            assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
            assertThat(proxySettings.getProxyHost()).isEqualTo(SYSTEM_PROXY_HOST);
            assertThat(proxySettings.getProxyPort()).isEqualTo(SYSTEM_PROXY_PORT);
            assertThat(proxySettings.getCredentials()).isNull();
        });
    }

    @Test
    void should_return_system_proxy_with_credentials_if_system_proxy_host_is_defined_and_plugin_proxy_host_is_null() {
        final Credentials proxyCredentials = someCredentials();

        assertThat(usecase.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyHost(SYSTEM_PROXY_HOST)
            .systemProxyPort(SYSTEM_PROXY_PORT)
            .proxyCredentials(proxyCredentials)
            .build())).satisfies(proxySettings -> {
            assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
            assertThat(proxySettings.getProxyHost()).isEqualTo(SYSTEM_PROXY_HOST);
            assertThat(proxySettings.getProxyPort()).isEqualTo(SYSTEM_PROXY_PORT);
            assertThat(proxySettings.getCredentials()).isEqualTo(proxyCredentials);
        });
    }

    @Test
    void should_return_plugin_proxy_if_system_proxy_host_is_null_and_plugin_proxy_host_is_defined() {
        assertThat(usecase.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyPort(SYSTEM_PROXY_PORT)
            .proxyHost(PLUGIN_PROXY_HOST)
            .proxyPort(PLUGIN_PROXY_PORT)
            .build())).satisfies(proxySettings -> {
            assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
            assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
            assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
            assertThat(proxySettings.getCredentials()).isNull();
        });
    }

    @Test
    void should_return_plugin_proxy_with_credentials_if_system_proxy_host_is_null_and_plugin_proxy_host_is_defined() {
        final Credentials proxyCredentials = someCredentials();

        assertThat(usecase.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyPort(SYSTEM_PROXY_PORT)
            .proxyHost(PLUGIN_PROXY_HOST)
            .proxyPort(PLUGIN_PROXY_PORT)
            .proxyCredentials(proxyCredentials)
            .build())).satisfies(proxySettings -> {
            assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
            assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
            assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
            assertThat(proxySettings.getCredentials()).isEqualTo(proxyCredentials);
        });
    }

    @Test
    void should_return_plugin_proxy_if_system_proxy_host_and_plugin_proxy_host_are_defined() {
        assertThat(usecase.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyHost(SYSTEM_PROXY_HOST)
            .systemProxyPort(SYSTEM_PROXY_PORT)
            .proxyHost(PLUGIN_PROXY_HOST)
            .proxyPort(PLUGIN_PROXY_PORT)
            .build())).satisfies(proxySettings -> {
            assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
            assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
            assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
            assertThat(proxySettings.getCredentials()).isNull();
        });
    }

    @Test
    void should_return_plugin_proxy_with_credentials_if_system_proxy_host_and_plugin_proxy_host_are_defined() {
        final Credentials proxyCredentials = someCredentials();

        assertThat(usecase.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyHost(SYSTEM_PROXY_HOST)
            .systemProxyPort(SYSTEM_PROXY_PORT)
            .proxyHost(PLUGIN_PROXY_HOST)
            .proxyPort(PLUGIN_PROXY_PORT)
            .proxyCredentials(proxyCredentials)
            .build())).satisfies(proxySettings -> {
            assertThat(proxySettings.getProxyType()).isEqualTo(Proxy.Type.HTTP);
            assertThat(proxySettings.getProxyHost()).isEqualTo(PLUGIN_PROXY_HOST);
            assertThat(proxySettings.getProxyPort()).isEqualTo(PLUGIN_PROXY_PORT);
            assertThat(proxySettings.getCredentials()).isEqualTo(proxyCredentials);
        });
    }
}
