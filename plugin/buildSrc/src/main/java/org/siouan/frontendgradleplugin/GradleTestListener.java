package org.siouan.frontendgradleplugin;

import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.tasks.testing.TestOutputEvent;
import org.gradle.api.tasks.testing.TestOutputListener;
import org.gradle.api.tasks.testing.TestResult;

public class GradleTestListener implements TestListener, TestOutputListener {

    private final Logger logger;

    public GradleTestListener(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void beforeSuite(TestDescriptor testSuite) {
    }

    @Override
    public void afterSuite(TestDescriptor testSuite, TestResult result) {
    }

    @Override
    public void beforeTest(TestDescriptor testDescriptor) {
        final String className = testDescriptor.getClassName();
        logger.lifecycle("\n========== TEST {}.{} STARTED ==========", (className == null) ? "UNKNOWN TEST"
                : testDescriptor.getClassName().substring(testDescriptor.getClassName().lastIndexOf('.') + 1),
            testDescriptor.getDisplayName());
    }

    @Override
    public void afterTest(TestDescriptor testDescriptor, TestResult result) {
        final String className = testDescriptor.getClassName();
        logger.lifecycle("========== TEST {}.{} {} ==========", (className == null) ? "UNKNOWN TEST"
                : testDescriptor.getClassName().substring(testDescriptor.getClassName().lastIndexOf('.') + 1),
            testDescriptor.getDisplayName(), result.getResultType());
    }

    @Override
    public void onOutput(TestDescriptor testDescriptor, TestOutputEvent outputEvent) {
        if (!outputEvent.getMessage().trim().isEmpty()) {
            logger.lifecycle("  {}", outputEvent.getMessage());
        }
    }
}
