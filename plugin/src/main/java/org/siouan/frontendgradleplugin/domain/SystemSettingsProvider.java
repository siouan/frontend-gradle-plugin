package org.siouan.frontendgradleplugin.domain;

import java.util.Set;

/**
 * System-wide settings provider.
 *
 * @since 5.2.0
 */
public interface SystemSettingsProvider {

    /**
     * Gets a list of hosts that should be reached directly, bypassing the proxy.
     *
     * @return Set of patterns separated by '|'. The patterns may start or end with a '*' for wildcards. Any host
     * matching one of these patterns will be reached through a direct connection instead of through a proxy.
     */
    Set<String> getNonProxyHosts();

    /**
     * Gets the host name of the proxy server for HTTP requests.
     *
     * @return Host name.
     */
    String getHttpProxyHost();

    /**
     * Gets the port number of the proxy server for HTTP requests.
     *
     * @return Port number.
     */
    int getHttpProxyPort();

    /**
     * Gets the host name of the proxy server for HTTPS requests.
     *
     * @return Host name.
     */
    String getHttpsProxyHost();

    /**
     * Gets the port number of the proxy server for HTTPS requests.
     *
     * @return Port number.
     */
    int getHttpsProxyPort();

    /**
     * Gets the current JVM architecture.
     *
     * @return String describing the JVM architecture.
     */
    String getSystemJvmArch();

    /**
     * Gets the current O/S name.
     *
     * @return String describing the O/S.
     */
    String getSystemOsName();
}
