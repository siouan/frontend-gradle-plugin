package org.siouan.frontendgradleplugin.test.fixture;

import java.net.Proxy;

import org.siouan.frontendgradleplugin.domain.model.ProxySettings;

public final class ProxySettingsFixture {

    private ProxySettingsFixture() {
    }

    public static ProxySettings someProxySettings() {
        return new ProxySettings(Proxy.Type.HTTP, "example.org", 443, null);
    }
}
