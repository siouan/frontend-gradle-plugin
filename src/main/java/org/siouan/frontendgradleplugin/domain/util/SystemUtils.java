package org.siouan.frontendgradleplugin.domain.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class provides utilities to get system information.
 *
 * @since 2.0.0
 */
public final class SystemUtils {

    private static final String JVM_ARCH_PROPERTY = "os.arch";

    private static final String HTTP_PROXY_HOST = "http.proxyHost";

    private static final String HTTP_PROXY_PORT = "http.proxyPort";

    private static final String HTTPS_PROXY_HOST = "https.proxyHost";

    private static final String HTTPS_PROXY_PORT = "https.proxyPort";

    private static final String NON_PROXY_HOSTS = "http.nonProxyHosts";

    private static final String NON_PROXY_HOSTS_SPLIT_PATTERN = Pattern.quote("|");

    private static final String OS_NAME_PROPERTY = "os.name";

    private SystemUtils() {
    }

    /**
     * Gets a list of hosts that should be reached directly, bypassing the proxy.
     *
     * @return List of patterns separated by '|'. The patterns may start or end with a '*' for wildcards. Any host
     * matching one of these patterns will be reached through a direct connection instead of through a proxy.
     */
    @Nonnull
    public static Set<String> getNonProxyHosts() {
        final String nonProxyHosts = System.getProperty(NON_PROXY_HOSTS);
        return ((nonProxyHosts == null) || nonProxyHosts.trim().isEmpty()) ? Collections.emptySet()
            : new HashSet<>(Arrays.asList(nonProxyHosts.split(NON_PROXY_HOSTS_SPLIT_PATTERN)));
    }

    /**
     * Gets the host name of the proxy server for HTTP requests.
     *
     * @return Host name.
     */
    @Nullable
    public static String getHttpProxyHost() {
        return System.getProperty(HTTP_PROXY_HOST);
    }

    /**
     * Gets the port number of the proxy server for HTTP requests.
     *
     * @return Port number.
     */
    public static Optional<Integer> getHttpProxyPort() {
        final String httpProxyPort = System.getProperty(HTTP_PROXY_PORT);
        return Optional.ofNullable(httpProxyPort).filter(port -> !port.trim().isEmpty()).map(Integer::parseInt);
    }

    /**
     * Gets the host name of the proxy server for HTTPS requests.
     *
     * @return Host name.
     */
    @Nullable
    public static String getHttpsProxyHost() {
        return System.getProperty(HTTPS_PROXY_HOST);
    }

    /**
     * Gets the port number of the proxy server for HTTPS requests.
     *
     * @return Port number.
     */
    public static Optional<Integer> getHttpsProxyPort() {
        final String httpsProxyPort = System.getProperty(HTTPS_PROXY_PORT);
        return Optional.ofNullable(httpsProxyPort).filter(port -> !port.trim().isEmpty()).map(Integer::parseInt);
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
