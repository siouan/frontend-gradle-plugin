package org.siouan.frontendgradleplugin.test.util;

import java.util.Objects;
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
            && Objects.equals(actualValue.getProxySettings(), expectedValue.getProxySettings())
            && actualValue.getTemporaryFilePath().equals(expectedValue.getTemporaryFilePath())
            && actualValue.getDestinationFilePath().equals(expectedValue.getDestinationFilePath());
    }
}
