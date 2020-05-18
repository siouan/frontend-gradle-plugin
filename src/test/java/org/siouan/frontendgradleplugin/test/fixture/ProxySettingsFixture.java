package org.siouan.frontendgradleplugin.test.fixture;

import java.net.Proxy;

import org.siouan.frontendgradleplugin.domain.model.ProxySettings;

public final class ProxySettingsFixture {

    public static final ProxySettings NO_PROXY_SETTINGS = new ProxySettings(Proxy.NO_PROXY, null);

    private ProxySettingsFixture() {
    }
}
