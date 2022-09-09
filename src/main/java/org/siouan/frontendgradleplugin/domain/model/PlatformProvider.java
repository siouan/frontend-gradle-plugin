package org.siouan.frontendgradleplugin.domain.model;

import javax.annotation.Nonnull;

/**
 * A provider of {@link Platform} instances.
 *
 * @since 7.0.0
 */
public interface PlatformProvider {

    /**
     * Gets a descriptor of the underlying platform.
     *
     * @return Platform.
     */
    @Nonnull
    Platform getPlatform();
}
