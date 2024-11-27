package org.siouan.frontendgradleplugin.domain.installer;

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

    public boolean execute(final IsNonProxyHostCommand command) {
        final String hostNameOrIpAddress = command.getHostNameOrIpAddress();
        return command.getNonProxyHosts().stream().anyMatch(nonProxyHost ->
            // @formatter:off
            ((nonProxyHost.charAt(0) == WILDCARD_CHARACTER) && hostNameOrIpAddress.endsWith(nonProxyHost.substring(1)))
            || ((nonProxyHost.charAt(nonProxyHost.length() - 1) == WILDCARD_CHARACTER)
                && hostNameOrIpAddress.startsWith(nonProxyHost.substring(0, nonProxyHost.length() - 1)))
            || nonProxyHost.equals(hostNameOrIpAddress));
            // @formatter:on
    }
}
