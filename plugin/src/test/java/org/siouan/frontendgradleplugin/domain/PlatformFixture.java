package org.siouan.frontendgradleplugin.domain;

import static org.siouan.frontendgradleplugin.domain.SystemPropertiesFixture.getSystemJvmArch;
import static org.siouan.frontendgradleplugin.domain.SystemPropertiesFixture.getSystemOsName;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlatformFixture {

    public static final Platform LOCAL_PLATFORM = aPlatform(getSystemJvmArch(), getSystemOsName());

    public static final Platform ANY_WINDOWS_PLATFORM = aPlatform(getSystemJvmArch(), "Windows NT");

    public static final Platform ANY_UNIX_PLATFORM = aPlatform(getSystemJvmArch(), "Linux");

    public static final Platform ANY_NON_WINDOWS_PLATFORM = ANY_UNIX_PLATFORM;

    public static Platform aPlatform() {
        return aPlatform(getSystemJvmArch(), "Linux");
    }

    public static Platform aPlatform(final String jvmArch, final String osName) {
        return Platform.builder().jvmArch(jvmArch).osName(osName).build();
    }
}
