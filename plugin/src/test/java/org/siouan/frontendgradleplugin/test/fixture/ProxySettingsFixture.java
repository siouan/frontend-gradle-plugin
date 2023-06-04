package org.siouan.frontendgradleplugin.test.fixture;

import java.net.Proxy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.siouan.frontendgradleplugin.domain.installer.ProxySettings;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProxySettingsFixture {

    public static ProxySettings someProxySettings() {
        return ProxySettings
            .builder()
            .proxyType(Proxy.Type.HTTP)
            .proxyHost("example.com")
            .proxyPort(443)
            .credentials(null)
            .build();
    }
}
