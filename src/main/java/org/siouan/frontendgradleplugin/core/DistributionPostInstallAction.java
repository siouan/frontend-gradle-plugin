package org.siouan.frontendgradleplugin.core;

/**
 * Action callable once a distribution has been successfully installed.
 *
 * @since 1.1.2
 */
@FunctionalInterface
public interface DistributionPostInstallAction {

    /**
     * Runs the action with distribution installer settings.
     *
     * @param settings Distribution installer settings.
     * @throws DistributionPostInstallException If the action failed.
     */
    void onDistributionInstalled(DistributionInstallerSettings settings) throws DistributionPostInstallException;
}
