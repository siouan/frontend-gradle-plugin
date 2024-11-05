package org.siouan.frontendgradleplugin.domain;

import static org.siouan.frontendgradleplugin.domain.SystemPropertiesFixture.getSystemJvmArch;
import static org.siouan.frontendgradleplugin.domain.SystemPropertiesFixture.getSystemOsName;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlatformFixture {

    public static final Platform LOCAL_PLATFORM = aPlatform(getSystemJvmArch(), getSystemOsName());

    public static final Platform ANY_WINDOWS_PLATFORM = aPlatformWithOsName("Windows NT");

    public static final Platform ANY_UNIX_PLATFORM = aPlatformWithOsName("Linux");

    public static final Platform ANY_NON_WINDOWS_PLATFORM = ANY_UNIX_PLATFORM;

    public static Platform aPlatform() {
        return aPlatformWithOsName("Linux");
    }

    public static Platform aPlatformWithOsName(final String osName) {
        return aPlatform(getSystemJvmArch(), osName);
    }

    public static Platform aPlatformWithJvmArch(final String jvmArch) {
        return aPlatform(jvmArch, getSystemOsName());
    }

    public static Platform aPlatform(final String jvmArch, final String osName) {
        return Platform.builder().jvmArch(jvmArch).osName(osName).build();
    }
}
