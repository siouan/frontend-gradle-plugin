package org.siouan.frontendgradleplugin.domain;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class provides utilities to get system information.
 *
 * @since 2.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SystemPropertiesFixture {

    /**
     * Gets the current JVM architecture.
     *
     * @return String describing the JVM architecture.
     */
    public static String getSystemJvmArch() {
        return getPropertyAndAssertNotNull(SystemProperties.JVM_ARCH_PROPERTY);
    }

    /**
     * Gets the current O/S name.
     *
     * @return String describing the O/S.
     */
    public static String getSystemOsName() {
        return getPropertyAndAssertNotNull(SystemProperties.OS_NAME_PROPERTY);
    }

    private static String getPropertyAndAssertNotNull(final String property) {
        return Objects.requireNonNull(System.getProperty(property),
            "Unexpected <null> value when reading system property: " + property);
    }
}
