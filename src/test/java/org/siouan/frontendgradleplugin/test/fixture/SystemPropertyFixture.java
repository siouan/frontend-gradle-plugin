package org.siouan.frontendgradleplugin.test.fixture;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.SystemProperty;

/**
 * This class provides utilities to get system information.
 *
 * @since 2.0.0
 */
public final class SystemPropertyFixture {

    private SystemPropertyFixture() {
    }

    /**
     * Gets a list of hosts that should be reached directly, bypassing the proxy.
     *
     * @return List of patterns separated by '|'. The patterns may start or end with a '*' for wildcards. Any host
     * matching one of these patterns will be reached through a direct connection instead of through a proxy.
     */
    @Nonnull
    public static Set<String> getNonProxyHosts() {
        final String nonProxyHosts = System.getProperty(SystemProperty.NON_PROXY_HOSTS);
        return ((nonProxyHosts == null) || nonProxyHosts.isBlank()) ? Collections.emptySet()
            : Set.of(nonProxyHosts.split(SystemProperty.NON_PROXY_HOSTS_SPLIT_PATTERN));
    }

    /**
     * Gets the host name of the proxy server for HTTP requests.
     *
     * @return Host name.
     */
    @Nullable
    public static String getHttpProxyHost() {
        return System.getProperty(SystemProperty.HTTP_PROXY_HOST);
    }

    /**
     * Gets the port number of the proxy server for HTTP requests.
     *
     * @return Port number.
     */
    public static Optional<Integer> getHttpProxyPort() {
        final String httpProxyPort = System.getProperty(SystemProperty.HTTP_PROXY_PORT);
        return Optional.ofNullable(httpProxyPort).filter(port -> !port.isBlank()).map(Integer::parseInt);
    }

    /**
     * Gets the host name of the proxy server for HTTPS requests.
     *
     * @return Host name.
     */
    @Nullable
    public static String getHttpsProxyHost() {
        return System.getProperty(SystemProperty.HTTPS_PROXY_HOST);
    }

    /**
     * Gets the port number of the proxy server for HTTPS requests.
     *
     * @return Port number.
     */
    public static Optional<Integer> getHttpsProxyPort() {
        final String httpsProxyPort = System.getProperty(SystemProperty.HTTPS_PROXY_PORT);
        return Optional.ofNullable(httpsProxyPort).filter(port -> !port.isBlank()).map(Integer::parseInt);
    }

    /**
     * Gets the current JVM architecture.
     *
     * @return String describing the JVM architecture.
     */
    @Nonnull
    public static String getSystemJvmArch() {
        return getPropertyAndAssertNotNull(SystemProperty.JVM_ARCH_PROPERTY);
    }

    /**
     * Gets the current O/S name.
     *
     * @return String describing the O/S.
     */
    @Nonnull
    public static String getSystemOsName() {
        return getPropertyAndAssertNotNull(SystemProperty.OS_NAME_PROPERTY);
    }

    @Nonnull
    private static String getPropertyAndAssertNotNull(@Nonnull final String property) {
        return Objects.requireNonNull(System.getProperty(property),
            "Unexpected <null> value when reading system property: " + property);
    }
}
