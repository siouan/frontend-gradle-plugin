package org.siouan.frontendgradleplugin.domain.util;

import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * This class provides utilities to get system information.
 *
 * @since 2.0.0
 */
public final class SystemUtils {

    private static final String JVM_ARCH_PROPERTY = "os.arch";

    private static final String OS_NAME_PROPERTY = "os.name";

    private SystemUtils() {
    }

    /**
     * Gets the current JVM architecture.
     *
     * @return String describing the JVM architecture.
     */
    @Nonnull
    public static String getSystemJvmArch() {
        return getPropertyAndAssertNotNull(JVM_ARCH_PROPERTY);
    }

    /**
     * Gets the current O/S name.
     *
     * @return String describing the O/S.
     */
    @Nonnull
    public static String getSystemOsName() {
        return getPropertyAndAssertNotNull(OS_NAME_PROPERTY);
    }

    @Nonnull
    private static String getPropertyAndAssertNotNull(@Nonnull final String property) {
        return Objects.requireNonNull(System.getProperty(property),
            "Unexpected <null> value when reading system property: " + property);
    }
}
