package org.siouan.frontendgradleplugin.test.fixture;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

public final class PlatformFixture {

    public static final Platform LOCAL_PLATFORM = aDefaultPlatform(SystemUtils.getSystemJvmArch(),
        SystemUtils.getSystemOsName());

    public static final Platform ANY_WINDOWS_PLATFORM = aDefaultPlatform(SystemUtils.getSystemJvmArch(), "Windows NT");

    public static final Platform ANY_UNIX_PLATFORM = aDefaultPlatform(SystemUtils.getSystemJvmArch(), "Linux");

    public static final Platform ANY_NON_WINDOWS_PLATFORM = ANY_UNIX_PLATFORM;

    private PlatformFixture() {
    }

    public static Platform aDefaultPlatform(@Nonnull final String jvmArch, @Nonnull final String osName) {
        return new Platform(jvmArch, osName, EnvironmentFixture.EMPTY_ENVIRONMENT,
            SystemProxySettingsFixture.defaultSystemProxySettings());
    }
}
