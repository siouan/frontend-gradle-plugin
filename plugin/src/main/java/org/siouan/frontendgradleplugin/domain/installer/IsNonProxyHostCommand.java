package org.siouan.frontendgradleplugin.domain.installer;

import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Settings to resolve whether a hostname or IP address should be connected to directly.
 *
 * @since 7.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IsNonProxyHostCommand {

    @EqualsAndHashCode.Include
    private final Set<String> nonProxyHosts;

    @EqualsAndHashCode.Include
    private final String hostNameOrIpAddress;

    IsNonProxyHostCommand(final Set<String> nonProxyHosts, final String hostNameOrIpAddress) {
        this.nonProxyHosts = Set.copyOf(nonProxyHosts);
        this.hostNameOrIpAddress = hostNameOrIpAddress;
    }
}
