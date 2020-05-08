package org.siouan.frontendgradleplugin.test.fixture;

import org.siouan.frontendgradleplugin.domain.model.Environment;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

public final class PlatformFixture {

    public static final Platform LOCAL_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(),
        SystemUtils.getSystemOsName(), new Environment(null, null));

    public static final Platform ANY_WINDOWS_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT",
        new Environment(null, null));

    public static final Platform ANY_UNIX_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(), "Linux",
        new Environment(null, null));

    public static final Platform ANY_NON_WINDOWS_PLATFORM = ANY_UNIX_PLATFORM;

    private PlatformFixture() {
    }
}
