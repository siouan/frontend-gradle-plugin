package org.siouan.frontendgradleplugin.test.util;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorSettings;

public class DistributionValidatorSettingsMatcher extends AbstractArgumentMatcher<DistributionValidatorSettings> {

    public DistributionValidatorSettingsMatcher(@Nonnull final DistributionValidatorSettings expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(@Nullable final DistributionValidatorSettings actualValue) {
        if (actualValue == null) {
            return false;
        }

        return actualValue.getDistributionUrl().equals(expectedValue.getDistributionUrl())
            && Objects.equals(actualValue.getDistributionServerCredentials(), expectedValue.getDistributionServerCredentials())
            && actualValue.getTemporaryDirectoryPath().equals(expectedValue.getTemporaryDirectoryPath())
            && actualValue.getDistributionFilePath().equals(expectedValue.getDistributionFilePath())
            && Objects.equals(actualValue.getProxySettings(), expectedValue.getProxySettings());
    }
}
