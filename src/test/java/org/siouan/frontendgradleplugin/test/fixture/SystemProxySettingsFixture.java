package org.siouan.frontendgradleplugin.test.fixture;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.siouan.frontendgradleplugin.domain.model.SystemProxySettings;

public final class SystemProxySettingsFixture {

    private SystemProxySettingsFixture() {
    }

    public static SystemProxySettings defaultSystemProxySettings() {
        return new SystemProxySettings(null, 80, null, 443,
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList("localhost", "127.*", "[::1]"))));
    }
}
