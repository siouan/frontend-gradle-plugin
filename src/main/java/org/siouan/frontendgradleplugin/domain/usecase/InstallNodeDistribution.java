package org.siouan.frontendgradleplugin.domain.usecase;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionId;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Implementation that supports installing a Node distribution.
 *
 * @since 2.0.0
 */
public class InstallNodeDistribution extends AbstractInstallDistribution {

    public InstallNodeDistribution(final FileManager fileManager, final GetDistribution getDistribution,
        final DeployDistribution deployDistribution, final Logger logger) {
        super(fileManager, getDistribution, deployDistribution, logger);
    }

    @Override
    @Nonnull
    protected String getDistributionId() {
        return DistributionId.NODE;
    }
}
