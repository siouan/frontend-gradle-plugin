package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;

/**
 * Implementation of a platform provider.
 *
 * @since 7.0.0
 */
public class PlatformProviderImpl implements PlatformProvider {

    private final SystemSettingsProvider systemSettingsProvider;

    public PlatformProviderImpl(@Nonnull final SystemSettingsProvider systemSettingsProvider) {
        this.systemSettingsProvider = systemSettingsProvider;
    }

    @Nonnull
    @Override
    public Platform getPlatform() {
        return new Platform(systemSettingsProvider.getSystemJvmArch(), systemSettingsProvider.getSystemOsName());
    }
}
