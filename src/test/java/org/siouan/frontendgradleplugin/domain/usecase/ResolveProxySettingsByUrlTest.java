package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.model.SystemProxySettings;
import org.siouan.frontendgradleplugin.test.fixture.CredentialsFixture;
import org.siouan.frontendgradleplugin.test.fixture.ProxySettingsFixture;

@ExtendWith(MockitoExtension.class)
class ResolveProxySettingsByUrlTest {

    private static final String HOST = "example.org";

    private static final int PORT = 8554;

    private static final Credentials PLUGIN_HTTP_PROXY_CREDENTIALS = CredentialsFixture.someCredentials("foo");

    private static final String PLUGIN_HTTP_PROXY_HOST = "foo.bar.bar";

    private static final int PLUGIN_HTTP_PROXY_PORT = 2564;

    private static final Credentials PLUGIN_HTTPS_PROXY_CREDENTIALS = CredentialsFixture.someCredentials("john");

    private static final String PLUGIN_HTTPS_PROXY_HOST = "baz.bar";

    private static final int PLUGIN_HTTPS_PROXY_PORT = 914;

    private static final String FILE_RESOURCE_URL = "file:///file.txt";

    private static final String HTTP_RESOURCE_URL = "http://" + HOST + ':' + PORT;

    private static final String HTTPS_RESOURCE_URL = "https://" + HOST + ':' + PORT;

    private static final String SYSTEM_HTTP_PROXY_HOST = "example.org";

    private static final int SYSTEM_HTTP_PROXY_PORT = 233;

    private static final String SYSTEM_HTTPS_PROXY_HOST = "john-doe.com";

    private static final int SYSTEM_HTTPS_PROXY_PORT = 91;

    @Mock
    private SystemProxySettings systemProxySettings;

    @Mock
    private IsNonProxyHost isNonProxyHost;

    @Mock
    private SelectProxySettings selectProxySettings;

    @InjectMocks
    private ResolveProxySettingsByUrl usecase;

    @Test
    void shouldFailWhenUrlUsesUnsupportedProtocol() {
        assertThatThrownBy(
            () -> usecase.execute(null, 80, null, null, 443, null, new URL("ftp", HOST, PORT, "/"))).isInstanceOf(
            IllegalArgumentException.class);

        verifyNoMoreInteractions(systemProxySettings, isNonProxyHost, selectProxySettings);
    }

    @Test
    void shouldReturnNullWhenUrlUsesFileProtocol() throws MalformedURLException {
        assertThat(usecase.execute(null, 80, null, null, 443, null, new URL(FILE_RESOURCE_URL))).isNull();

        verifyNoMoreInteractions(systemProxySettings, isNonProxyHost, selectProxySettings);
    }

    @Test
    void shouldReturnNullWhenUrlUsesNonProxyHost() throws MalformedURLException {
        final Set<String> nonProxyHosts = Collections.singleton(PLUGIN_HTTP_PROXY_HOST);
        when(systemProxySettings.getNonProxyHosts()).thenReturn(nonProxyHosts);
        final URL resourceUrl = new URL(HTTP_RESOURCE_URL);
        when(isNonProxyHost.execute(nonProxyHosts, resourceUrl.getHost())).thenReturn(true);

        assertThat(usecase.execute(null, 80, null, null, 443, null, resourceUrl)).isNull();

        verifyNoMoreInteractions(systemProxySettings, isNonProxyHost, selectProxySettings);
    }

    @Test
    void shouldReturnHttpProxySettingsWhenUrlUsesNonSecureHttpProtocol() throws MalformedURLException {
        final Set<String> nonProxyHosts = Collections.emptySet();
        when(systemProxySettings.getNonProxyHosts()).thenReturn(nonProxyHosts);
        final URL resourceUrl = new URL(HTTP_RESOURCE_URL);
        when(isNonProxyHost.execute(nonProxyHosts, resourceUrl.getHost())).thenReturn(false);
        when(systemProxySettings.getHttpProxyHost()).thenReturn(SYSTEM_HTTP_PROXY_HOST);
        when(systemProxySettings.getHttpProxyPort()).thenReturn(SYSTEM_HTTP_PROXY_PORT);
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        when(selectProxySettings.execute(SYSTEM_HTTP_PROXY_HOST, SYSTEM_HTTP_PROXY_PORT, PLUGIN_HTTP_PROXY_HOST,
            PLUGIN_HTTP_PROXY_PORT, PLUGIN_HTTP_PROXY_CREDENTIALS)).thenReturn(proxySettings);

        assertThat(usecase.execute(PLUGIN_HTTP_PROXY_HOST, PLUGIN_HTTP_PROXY_PORT, PLUGIN_HTTP_PROXY_CREDENTIALS,
            PLUGIN_HTTPS_PROXY_HOST, PLUGIN_HTTPS_PROXY_PORT, PLUGIN_HTTPS_PROXY_CREDENTIALS, resourceUrl)).isEqualTo(
            proxySettings);

        verifyNoMoreInteractions(systemProxySettings, isNonProxyHost, selectProxySettings);
    }

    @Test
    void shouldReturnHttpsProxySettingsWhenUrlUsesSecureHttpProtocol() throws MalformedURLException {
        final Set<String> nonProxyHosts = Collections.emptySet();
        when(systemProxySettings.getNonProxyHosts()).thenReturn(nonProxyHosts);
        final URL resourceUrl = new URL(HTTPS_RESOURCE_URL);
        when(isNonProxyHost.execute(nonProxyHosts, resourceUrl.getHost())).thenReturn(false);
        when(systemProxySettings.getHttpsProxyHost()).thenReturn(SYSTEM_HTTPS_PROXY_HOST);
        when(systemProxySettings.getHttpsProxyPort()).thenReturn(SYSTEM_HTTPS_PROXY_PORT);
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        when(selectProxySettings.execute(SYSTEM_HTTPS_PROXY_HOST, SYSTEM_HTTPS_PROXY_PORT, PLUGIN_HTTPS_PROXY_HOST,
            PLUGIN_HTTPS_PROXY_PORT, PLUGIN_HTTPS_PROXY_CREDENTIALS)).thenReturn(proxySettings);

        assertThat(usecase.execute(PLUGIN_HTTP_PROXY_HOST, PLUGIN_HTTP_PROXY_PORT, PLUGIN_HTTP_PROXY_CREDENTIALS,
            PLUGIN_HTTPS_PROXY_HOST, PLUGIN_HTTPS_PROXY_PORT, PLUGIN_HTTPS_PROXY_CREDENTIALS, resourceUrl)).isEqualTo(
            proxySettings);

        verifyNoMoreInteractions(systemProxySettings, isNonProxyHost, selectProxySettings);
    }
}
