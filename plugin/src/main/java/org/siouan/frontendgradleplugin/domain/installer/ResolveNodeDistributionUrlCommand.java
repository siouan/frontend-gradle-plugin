package org.siouan.frontendgradleplugin.domain.installer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Distribution definition.
 *
 * @since 2.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResolveNodeDistributionUrlCommand {

    /**
     * Underlying platform.
     */
    @EqualsAndHashCode.Include
    private final Platform platform;

    /**
     * Version of distribution.
     */
    @EqualsAndHashCode.Include
    private final String version;

    /**
     * URL root part to build the exact URL to download the distribution.
     */
    @EqualsAndHashCode.Include
    private final String downloadUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the distribution.
     */
    @EqualsAndHashCode.Include
    private final String downloadUrlPathPattern;
}
