package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.Collections.emptySet;
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
    void shouldReturnFalseIfNonProxyHostsIsEmpty() {
        assertThat(usecase.execute(emptySet(), HOSTNAME)).isFalse();
    }

    @Test
    void shouldReturnFalseIfHostnameDoesNotMatchNonProxyHosts() {
        assertThat(usecase.execute(Set.of("*.bar.baz2", "foo2.bar.*", "*.bar.*"), HOSTNAME)).isFalse();
    }

    @Test
    void shouldReturnFalseIfIpAddressDoesNotMatchNonProxyHosts() {
        assertThat(usecase.execute(Set.of("*.52.85.102", "64.52.85.*", "*.52.85.*"), IP_ADDRESS)).isFalse();
    }

    @Test
    void shouldReturnTrueIfHostnameEqualsNonProxyHost() {
        assertThat(usecase.execute(Set.of(HOSTNAME), HOSTNAME)).isTrue();
    }

    @Test
    void shouldReturnTrueIfIpAddressEqualsNonProxyHost() {
        assertThat(usecase.execute(Set.of(IP_ADDRESS), IP_ADDRESS)).isTrue();
    }

    @Test
    void shouldReturnTrueIfHostnameMatchesNonProxyHostPrefix() {
        assertThat(usecase.execute(Set.of("*.baz"), HOSTNAME)).isTrue();
    }

    @Test
    void shouldReturnTrueIfIpAddressMatchesNonProxyHostPrefix() {
        assertThat(usecase.execute(Set.of("*.85.101"), IP_ADDRESS)).isTrue();
    }

    @Test
    void shouldReturnTrueIfHostnameMatchesNonProxyHostSuffix() {
        assertThat(usecase.execute(Set.of("foo.*"), HOSTNAME)).isTrue();
    }

    @Test
    void shouldReturnTrueIfIpAddressMatchesNonProxyHostSuffix() {
        assertThat(usecase.execute(Set.of("63.52.*"), IP_ADDRESS)).isTrue();
    }
}
