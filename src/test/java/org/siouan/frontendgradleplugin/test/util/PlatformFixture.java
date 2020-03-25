package org.siouan.frontendgradleplugin.test.util;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

public class PlatformFixture {

    private static final Platform LOCAL_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(),
        SystemUtils.getSystemOsName());

    public static Platform getLocalPlatform() {
        return LOCAL_PLATFORM;
    }
}
