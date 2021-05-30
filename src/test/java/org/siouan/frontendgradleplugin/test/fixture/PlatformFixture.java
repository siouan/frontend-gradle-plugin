package org.siouan.frontendgradleplugin.test.fixture;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;

public final class PlatformFixture {

    public static final Platform LOCAL_PLATFORM = aPlatform(SystemPropertyFixture.getSystemJvmArch(),
        SystemPropertyFixture.getSystemOsName());

    public static final Platform ANY_WINDOWS_PLATFORM = aPlatform(SystemPropertyFixture.getSystemJvmArch(),
        "Windows NT");

    public static final Platform ANY_UNIX_PLATFORM = aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");

    public static final Platform ANY_NON_WINDOWS_PLATFORM = ANY_UNIX_PLATFORM;

    private PlatformFixture() {
    }

    public static Platform aPlatform() {
        return aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
    }

    public static Platform aPlatform(@Nonnull final String jvmArch, @Nonnull final String osName) {
        return new Platform(jvmArch, osName, EnvironmentFixture.EMPTY_ENVIRONMENT);
    }
}
