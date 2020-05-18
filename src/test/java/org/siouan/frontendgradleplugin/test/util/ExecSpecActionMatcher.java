package org.siouan.frontendgradleplugin.test.util;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;
import org.siouan.frontendgradleplugin.infrastructure.gradle.ExecSpecAction;

public class ExecSpecActionMatcher extends AbstractArgumentMatcher<ExecSpecAction> {

    public ExecSpecActionMatcher(@Nonnull final ExecSpecAction expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(final ExecSpecAction actualValue) {
        final ExecutionSettings expectedExecutionSettings = expectedValue.getExecutionSettings();
        final ExecutionSettings actualExecutionSettings = actualValue.getExecutionSettings();
        return actualExecutionSettings.getWorkingDirectoryPath().equals(expectedExecutionSettings.getWorkingDirectoryPath())
            && actualExecutionSettings.getAdditionalExecutablePaths().equals(expectedExecutionSettings.getAdditionalExecutablePaths())
            && actualExecutionSettings.getExecutablePath().equals(expectedExecutionSettings.getExecutablePath())
            && (actualValue.getAfterConfiguredConsumer() != null);
    }
}
