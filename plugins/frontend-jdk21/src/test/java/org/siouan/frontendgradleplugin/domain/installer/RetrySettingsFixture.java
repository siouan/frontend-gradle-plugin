package org.siouan.frontendgradleplugin.domain.installer;

import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RetrySettingsFixture {

    public static RetrySettings someRetrySettings() {
        return someRetrySettings(1);
    }

    public static RetrySettings someRetrySettings(final int maxDownloadAttempts) {
        return someRetrySettings(maxDownloadAttempts, Set.of(502, 503, 504));
    }

    public static RetrySettings someRetrySettings(final int maxDownloadAttempts, final Set<Integer> retryHttpStatuses) {
        return RetrySettings
            .builder()
            .maxDownloadAttempts(maxDownloadAttempts)
            .retryHttpStatuses(retryHttpStatuses)
            .retryInitialIntervalMs(100)
            .retryIntervalMultiplier(2)
            .retryMaxIntervalMs(500)
            .build();
    }
}
