package org.siouan.frontendgradleplugin.domain.usecase;

import java.util.Set;
import javax.annotation.Nonnull;

/**
 * Resolves whether a host name or IP address is a member of a list of non proxy hosts. Non proxy hosts are hosts that
 * should be accessed without going through a proxy. The wildcard character '*' can be used for pattern matching. For
 * example: {@code *.foo.com} will indicate that every hosts in the {@code foo.com} domain should be accessed directly
 * even if a proxy server is specified.
 *
 * @since 5.0.0
 */
public class IsNonProxyHost {

    public static final char WILDCARD_CHARACTER = '*';

    public boolean execute(@Nonnull final Set<String> nonProxyHosts, @Nonnull final String hostnameOrIpAddress) {
        return nonProxyHosts
            .stream()
            .anyMatch(nonProxyHost ->
                ((nonProxyHost.charAt(0) == WILDCARD_CHARACTER)
                        && hostnameOrIpAddress.endsWith(nonProxyHost.substring(1)))
                    || ((nonProxyHost.charAt(nonProxyHost.length() - 1) == WILDCARD_CHARACTER)
                        && hostnameOrIpAddress.startsWith(nonProxyHost.substring(0, nonProxyHost.length() - 1)))
                    || nonProxyHost.equals(hostnameOrIpAddress));
    }
}
