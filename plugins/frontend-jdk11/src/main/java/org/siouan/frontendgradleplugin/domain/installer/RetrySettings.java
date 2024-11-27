package org.siouan.frontendgradleplugin.domain.installer;

import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Settings to retry a file download.
 *
 * @since 7.1.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RetrySettings {

    /**
     * Maximum number of attempts to download a file.
     */
    @EqualsAndHashCode.Include
    private final int maxDownloadAttempts;

    /**
     * HTTP statuses that should trigger another download attempt.
     */
    @EqualsAndHashCode.Include
    private final Set<Integer> retryHttpStatuses;

    /**
     * Interval between the first download attempt and an eventual retry.
     */
    @EqualsAndHashCode.Include
    private final int retryInitialIntervalMs;

    /**
     * Multiplier used to compute the intervals between retry attempts.
     */
    @EqualsAndHashCode.Include
    private final double retryIntervalMultiplier;

    /**
     * Maximum interval between retry attempts.
     */
    @EqualsAndHashCode.Include
    private final int retryMaxIntervalMs;
}
