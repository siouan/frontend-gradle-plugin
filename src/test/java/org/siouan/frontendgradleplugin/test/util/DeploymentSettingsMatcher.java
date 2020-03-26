package org.siouan.frontendgradleplugin.test.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.DeploymentSettings;

public class DeploymentSettingsMatcher extends AbstractArgumentMatcher<DeploymentSettings> {

    public DeploymentSettingsMatcher(@Nonnull final DeploymentSettings expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(@Nullable final DeploymentSettings actualValue) {
        if (actualValue == null) {
            return false;
        }

        return actualValue.getPlatform().equals(expectedValue.getPlatform()) && actualValue
            .getExtractDirectoryPath()
            .equals(expectedValue.getExtractDirectoryPath()) && actualValue
            .getInstallDirectoryPath()
            .equals(expectedValue.getInstallDirectoryPath()) && actualValue
            .getDistributionFilePath()
            .equals(expectedValue.getDistributionFilePath());
    }
}
