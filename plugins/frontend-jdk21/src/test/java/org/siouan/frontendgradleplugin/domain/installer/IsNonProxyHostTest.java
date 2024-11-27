package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IsNonProxyHostTest {

    private static final String HOSTNAME = "foo.bar.baz";

    private static final String IP_ADDRESS = "63.52.85.101";

    @InjectMocks
    private IsNonProxyHost usecase;

    @Test
    void should_return_false_if_non_proxy_hosts_parameter_is_empty() {
        assertThat(usecase.execute(
            IsNonProxyHostCommand.builder().nonProxyHosts(Set.of()).hostNameOrIpAddress(HOSTNAME).build())).isFalse();
    }

    @Test
    void should_return_false_if_hostname_does_not_match_non_proxy_hosts() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of("*.bar.baz2", "foo2.bar.*", "*.bar.*"))
            .hostNameOrIpAddress(HOSTNAME)
            .build())).isFalse();
    }

    @Test
    void should_return_false_if_ip_address_does_not_match_non_proxy_hosts() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of("*.52.85.102", "64.52.85.*", "*.52.85.*"))
            .hostNameOrIpAddress(IP_ADDRESS)
            .build())).isFalse();
    }

    @Test
    void should_return_true_if_hostname_equals_non_proxy_host() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of(HOSTNAME))
            .hostNameOrIpAddress(HOSTNAME)
            .build())).isTrue();
    }

    @Test
    void should_return_true_if_ip_address_equals_non_proxy_host() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of(IP_ADDRESS))
            .hostNameOrIpAddress(IP_ADDRESS)
            .build())).isTrue();
    }

    @Test
    void should_return_true_if_hostname_matches_non_proxy_host_prefix() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of("*.baz"))
            .hostNameOrIpAddress(HOSTNAME)
            .build())).isTrue();
    }

    @Test
    void should_return_true_if_ip_address_matches_non_proxy_host_prefix() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of("*.85.101"))
            .hostNameOrIpAddress(IP_ADDRESS)
            .build())).isTrue();
    }

    @Test
    void should_return_true_if_hostname_matches_non_proxy_host_sduffix() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of("foo.*"))
            .hostNameOrIpAddress(HOSTNAME)
            .build())).isTrue();
    }

    @Test
    void should_return_true_if_ip_address_matches_non_proxy_host_suffix() {
        assertThat(usecase.execute(IsNonProxyHostCommand
            .builder()
            .nonProxyHosts(Set.of("63.52.*"))
            .hostNameOrIpAddress(IP_ADDRESS)
            .build())).isTrue();
    }
}
