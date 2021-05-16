package org.siouan.frontendgradleplugin.test.fixture;

import java.util.Set;

import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;

public final class SystemProxySettingsFixture {

    private SystemProxySettingsFixture() {
    }

    public static SystemSettingsProvider defaultSystemProxySettings() {
        return null;//new SystemSettingsProvider(null, 80, null, 443, Set.of("localhost", "127.*", "[::1]"));
    }
}
