package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.installer.ProxySettingsFixture.someProxySettings;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolveProxySettingsByUrlTest {

    private static final String HOST = "example.org";

    private static final int PORT = 8554;

    private static final String PLUGIN_PROXY_HOST = "foo.bar.bar";

    private static final int PLUGIN_PROXY_PORT = 2564;

    private static final String FILE_RESOURCE_URL = "file:///file.txt";

    @SuppressWarnings("HttpUrlsUsage")
    private static final String HTTP_RESOURCE_URL = "http://" + HOST + ':' + PORT;

    private static final String HTTPS_RESOURCE_URL = "https://" + HOST + ':' + PORT;

    private static final String SYSTEM_PROXY_HOST = "example.org";

    private static final int SYSTEM_PROXY_PORT = 233;

    @Mock
    private IsNonProxyHost isNonProxyHost;

    @Mock
    private SelectProxySettings selectProxySettings;

    @InjectMocks
    private ResolveProxySettingsByUrl usecase;

    @Test
    void should_fail_when_url_uses_unsupported_protocol() throws MalformedURLException {
        final URL resourceUrl = URI.create("ftp://" + HOST + ':' + PORT + "/").toURL();
        final ResolveProxySettingsByUrlCommand command = ResolveProxySettingsByUrlCommand
            .builder()
            .httpProxyPort(80)
            .httpsProxyPort(443)
            .resourceUrl(resourceUrl)
            .build();

        assertThatThrownBy(() -> usecase.execute(command)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(isNonProxyHost, selectProxySettings);
    }

    @Test
    void should_return_direct_connection_when_url_uses_file_protocol() throws MalformedURLException {
        assertThat(usecase
            .execute(ResolveProxySettingsByUrlCommand
                .builder()
                .httpProxyPort(80)
                .httpsProxyPort(443)
                .resourceUrl(URI.create(FILE_RESOURCE_URL).toURL())
                .build())
            .getProxyType()).isEqualTo(Proxy.Type.DIRECT);

        verifyNoMoreInteractions(isNonProxyHost, selectProxySettings);
    }

    @Test
    void should_return_direct_connection_when_url_uses_non_proxy_host() throws MalformedURLException {
        final Set<String> nonProxyHosts = Set.of(PLUGIN_PROXY_HOST);
        final URL resourceUrl = URI.create(HTTP_RESOURCE_URL).toURL();
        when(isNonProxyHost.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(nonProxyHosts)
            .hostNameOrIpAddress(resourceUrl.getHost())
            .build())).thenReturn(true);

        assertThat(usecase
            .execute(ResolveProxySettingsByUrlCommand
                .builder()
                .httpProxyPort(80)
                .httpsProxyPort(443)
                .resourceUrl(resourceUrl)
                .systemNonProxyHosts(nonProxyHosts)
                .build())
            .getProxyType()).isEqualTo(Proxy.Type.DIRECT);

        verifyNoMoreInteractions(isNonProxyHost, selectProxySettings);
    }

    @Test
    void should_return_http_proxy_settings_when_url_uses_non_secure_http_protocol() throws MalformedURLException {
        final Set<String> nonProxyHosts = Set.of();
        final URL resourceUrl = URI.create(HTTP_RESOURCE_URL).toURL();
        when(isNonProxyHost.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(nonProxyHosts)
            .hostNameOrIpAddress(resourceUrl.getHost())
            .build())).thenReturn(false);
        final String systemProxyHost = SYSTEM_PROXY_HOST;
        final int systemProxyPort = SYSTEM_PROXY_PORT;
        final String proxyHost = PLUGIN_PROXY_HOST;
        final int proxyPort = PLUGIN_PROXY_PORT;
        final ProxySettings proxySettings = someProxySettings();
        final Credentials proxyCredentials = CredentialsFixture.someCredentials();
        when(selectProxySettings.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyHost(systemProxyHost)
            .systemProxyPort(systemProxyPort)
            .proxyHost(proxyHost)
            .proxyPort(proxyPort)
            .proxyCredentials(proxyCredentials)
            .build())).thenReturn(proxySettings);

        assertThat(usecase.execute(ResolveProxySettingsByUrlCommand
            .builder()
            .resourceUrl(resourceUrl)
            .httpProxyHost(proxyHost)
            .httpProxyPort(proxyPort)
            .httpProxyCredentials(proxyCredentials)
            .systemNonProxyHosts(nonProxyHosts)
            .systemHttpProxyHost(systemProxyHost)
            .systemHttpProxyPort(systemProxyPort)
            .build())).isEqualTo(proxySettings);

        verifyNoMoreInteractions(isNonProxyHost, selectProxySettings);
    }

    @Test
    void should_return_https_proxy_settings_when_url_uses_secure_http_protocol() throws MalformedURLException {
        final Set<String> nonProxyHosts = Set.of();
        final URL resourceUrl = URI.create(HTTPS_RESOURCE_URL).toURL();
        when(isNonProxyHost.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(nonProxyHosts)
            .hostNameOrIpAddress(resourceUrl.getHost())
            .build())).thenReturn(false);
        final String systemProxyHost = SYSTEM_PROXY_HOST;
        final int systemProxyPort = SYSTEM_PROXY_PORT;
        final String proxyHost = PLUGIN_PROXY_HOST;
        final int proxyPort = PLUGIN_PROXY_PORT;
        final ProxySettings proxySettings = someProxySettings();
        final Credentials proxyCredentials = CredentialsFixture.someCredentials();
        when(selectProxySettings.execute(SelectProxySettingsCommand
            .builder()
            .systemProxyHost(systemProxyHost)
            .systemProxyPort(systemProxyPort)
            .proxyHost(proxyHost)
            .proxyPort(proxyPort)
            .proxyCredentials(proxyCredentials)
            .build())).thenReturn(proxySettings);

        assertThat(usecase.execute(ResolveProxySettingsByUrlCommand
            .builder()
            .resourceUrl(resourceUrl)
            .httpsProxyHost(proxyHost)
            .httpsProxyPort(proxyPort)
            .httpsProxyCredentials(proxyCredentials)
            .systemNonProxyHosts(nonProxyHosts)
            .systemHttpsProxyHost(systemProxyHost)
            .systemHttpsProxyPort(systemProxyPort)
            .build())).isEqualTo(proxySettings);

        verifyNoMoreInteractions(isNonProxyHost, selectProxySettings);
    }
}
