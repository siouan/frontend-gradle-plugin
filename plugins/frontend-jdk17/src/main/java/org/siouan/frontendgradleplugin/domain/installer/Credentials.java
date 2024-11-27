package org.siouan.frontendgradleplugin.domain.installer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Simple credentials based on a username/password tuple.
 *
 * @since 3.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Credentials {

    @EqualsAndHashCode.Include
    private final String username;

    @EqualsAndHashCode.Include
    private final String password;
}
