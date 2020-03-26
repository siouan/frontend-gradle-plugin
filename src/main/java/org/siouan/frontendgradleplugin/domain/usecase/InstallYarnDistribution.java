package org.siouan.frontendgradleplugin.domain.usecase;

import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionId;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Implementation that supports installing a Yarn distribution.
 *
 * @since 2.0.0
 */
public class InstallYarnDistribution extends AbstractInstallDistribution {

    public InstallYarnDistribution(final FileManager fileManager, final GetDistribution getDistribution,
        final DeployDistribution deployDistribution, final Logger logger) {
        super(fileManager, getDistribution, deployDistribution, logger);
    }

    @Override
    @Nonnull
    protected DistributionId getDistributionId() {
        return DistributionId.YARN;
    }
}
