package org.siouan.frontendgradleplugin.domain.model;

import java.util.Collections;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * System-wide proxy settings.
 *
 * @since 5.0.0
 */
public class SystemProxySettings {

    private final String httpProxyHost;

    private final int httpProxyPort;

    private final String httpsProxyHost;

    private final int httpsProxyPort;

    private final Set<String> nonProxyHosts;

    public SystemProxySettings(@Nullable final String httpProxyHost, final int httpProxyPort,
        @Nullable final String httpsProxyHost, final int httpsProxyPort, @Nonnull final Set<String> nonProxyHosts) {
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
        this.httpsProxyHost = httpsProxyHost;
        this.httpsProxyPort = httpsProxyPort;
        this.nonProxyHosts = Collections.unmodifiableSet(nonProxyHosts);
    }

    @Nullable
    public String getHttpProxyHost() {
        return httpProxyHost;
    }

    public int getHttpProxyPort() {
        return httpProxyPort;
    }

    @Nullable
    public String getHttpsProxyHost() {
        return httpsProxyHost;
    }

    public int getHttpsProxyPort() {
        return httpsProxyPort;
    }

    @Nonnull
    public Set<String> getNonProxyHosts() {
        return nonProxyHosts;
    }

    @Override
    public String toString() {
        return SystemProxySettings.class.getSimpleName() + " {httpProxyHost=" + httpProxyHost + ", httpProxyPort="
            + httpProxyPort + ", httpsProxyHost=" + httpsProxyHost + ", httpsProxyPort=" + httpsProxyPort
            + ", nonProxyHosts=" + nonProxyHosts + '}';
    }
}
