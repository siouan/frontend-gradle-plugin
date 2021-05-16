package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * System-wide settings provider.
 *
 * @since 5.2.0
 */
public interface SystemSettingsProvider {

    /**
     * Gets a list of hosts that should be reached directly, bypassing the proxy.
     *
     * @return List of patterns separated by '|'. The patterns may start or end with a '*' for wildcards. Any host
     * matching one of these patterns will be reached through a direct connection instead of through a proxy.
     */
    @Nonnull
    Set<String> getNonProxyHosts();

    /**
     * Gets the host name of the proxy server for HTTP requests.
     *
     * @return Host name.
     */
    @Nullable
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
    @Nullable
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
    @Nonnull
    String getSystemJvmArch();

    /**
     * Gets the current O/S name.
     *
     * @return String describing the O/S.
     */
    @Nonnull
    String getSystemOsName();

    Path getNodejsHomePath();

    Path getYarnHomePath();
}
