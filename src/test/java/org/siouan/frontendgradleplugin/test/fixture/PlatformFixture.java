package org.siouan.frontendgradleplugin.test.fixture;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

public class PlatformFixture {

    public static final Platform LOCAL_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(),
        SystemUtils.getSystemOsName());

    public static final Platform ANY_WINDOWS_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(), "Windows NT");

    public static final Platform ANY_UNIX_PLATFORM = new Platform(SystemUtils.getSystemJvmArch(), "Linux");

    public static final Platform ANY_NON_WINDOWS_PLATFORM = ANY_UNIX_PLATFORM;
}
