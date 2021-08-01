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

        // @formatter:off
        return actualValue.getVersion().equals(expectedValue.getVersion())
            && actualValue.getPlatform().equals(expectedValue.getPlatform())
            && actualValue.getTemporaryDirectoryPath().equals(expectedValue.getTemporaryDirectoryPath())
            && Objects.equals(actualValue.getProxySettings(), expectedValue.getProxySettings())
            && actualValue.getDistributionUrlRoot().equals(expectedValue.getDistributionUrlRoot())
            && actualValue.getDistributionUrlPathPattern().equals(expectedValue.getDistributionUrlPathPattern())
            && Objects.equals(actualValue.getDistributionServerCredentials(), expectedValue.getDistributionServerCredentials());
        // @formatter:on
    }
}
