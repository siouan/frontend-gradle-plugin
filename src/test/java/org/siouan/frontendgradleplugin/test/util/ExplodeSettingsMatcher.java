package org.siouan.frontendgradleplugin.test.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;

public class ExplodeSettingsMatcher extends AbstractArgumentMatcher<ExplodeSettings> {

    public ExplodeSettingsMatcher(@Nonnull final ExplodeSettings expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(@Nullable final ExplodeSettings actualValue) {
        if (actualValue == null) {
            return false;
        }

        return actualValue.getPlatform().equals(expectedValue.getPlatform())
            && actualValue.getArchiveFilePath().equals(expectedValue.getArchiveFilePath())
            && actualValue.getTargetDirectoryPath().equals(expectedValue.getTargetDirectoryPath());
    }
}
