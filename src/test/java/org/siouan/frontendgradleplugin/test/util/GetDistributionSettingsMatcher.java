package org.siouan.frontendgradleplugin.test.util;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.GetDistributionSettings;

public class GetDistributionSettingsMatcher extends AbstractArgumentMatcher<GetDistributionSettings> {

    public GetDistributionSettingsMatcher(@Nonnull final GetDistributionSettings expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(@Nullable final GetDistributionSettings actualValue) {
        if (actualValue == null) {
            return false;
        }

        return (actualValue.getDistributionId() == expectedValue.getDistributionId()) && actualValue
            .getVersion()
            .equals(expectedValue.getVersion()) && actualValue.getPlatform().equals(expectedValue.getPlatform())
            && actualValue.getTemporaryDirectoryPath().equals(expectedValue.getTemporaryDirectoryPath()) && actualValue
            .getPlatform()
            .equals(expectedValue.getPlatform()) && Objects.equals(actualValue.getDistributionUrl(),
            expectedValue.getDistributionUrl()) && actualValue.getProxy().equals(expectedValue.getProxy());
    }
}
