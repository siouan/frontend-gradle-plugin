package org.siouan.frontendgradleplugin.test;

import org.gradle.testkit.runner.TaskOutcome;

/**
 * Enumeration of task outcomes for utilities in {@link GradleBuildAssertions} class.
 *
 * @since 7.0.0
 */
public enum PluginTaskOutcome {
    FAILED(TaskOutcome.FAILED),
    IGNORED(null),
    SKIPPED(TaskOutcome.SKIPPED),
    SUCCESS(TaskOutcome.SUCCESS),
    UP_TO_DATE(TaskOutcome.UP_TO_DATE);

    private final TaskOutcome nativeOutcome;

    PluginTaskOutcome(final TaskOutcome nativeOutcome) {
        this.nativeOutcome = nativeOutcome;
    }

    public TaskOutcome getNativeOutcome() {
        return nativeOutcome;
    }
}
