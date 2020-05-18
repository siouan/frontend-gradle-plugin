package org.siouan.frontendgradleplugin.test.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;

public class DownloadSettingsMatcher extends AbstractArgumentMatcher<DownloadSettings> {

    public DownloadSettingsMatcher(@Nonnull final DownloadSettings expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(@Nullable final DownloadSettings actualValue) {
        if (actualValue == null) {
            return false;
        }

        return actualValue.getResourceUrl().equals(expectedValue.getResourceUrl())
            && actualValue.getProxySettings().equals(expectedValue.getProxySettings())
            && actualValue.getTemporaryDirectoryPath().equals(expectedValue.getTemporaryDirectoryPath())
            && actualValue.getDestinationFilePath().equals(expectedValue.getDestinationFilePath());
    }
}
