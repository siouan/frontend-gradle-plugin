package org.siouan.frontendgradleplugin.domain.installer;

import java.net.Proxy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
