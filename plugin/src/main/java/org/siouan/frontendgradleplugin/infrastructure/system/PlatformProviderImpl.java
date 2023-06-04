package org.siouan.frontendgradleplugin.infrastructure.system;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.SystemSettingsProvider;

/**
 * Implementation of a platform provider.
 *
 * @since 7.0.0
 */
@RequiredArgsConstructor
public class PlatformProviderImpl implements PlatformProvider {

    private final SystemSettingsProvider systemSettingsProvider;

    @Override
    public Platform getPlatform() {
        return Platform
            .builder()
            .jvmArch(systemSettingsProvider.getSystemJvmArch())
            .osName(systemSettingsProvider.getSystemOsName())
            .build();
    }
}
