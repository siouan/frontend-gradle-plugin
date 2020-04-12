package org.siouan.frontendgradleplugin.test.util;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;

public class DistributionDefintionMatcher extends AbstractArgumentMatcher<DistributionDefinition> {

    public DistributionDefintionMatcher(@Nonnull final DistributionDefinition expectedValue) {
        super(expectedValue);
    }

    @Override
    public boolean matches(@Nullable final DistributionDefinition actualValue) {
        if (actualValue == null) {
            return false;
        }

        return actualValue.getPlatform().equals(expectedValue.getPlatform()) && actualValue
            .getVersion()
            .equals(expectedValue.getVersion()) && Objects.equals(actualValue.getDownloadUrl(),
            expectedValue.getDownloadUrl());
    }
}
