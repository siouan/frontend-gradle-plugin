package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class YarnPostInstallAction implements DistributionPostInstallAction {

    @Override
    public void onDistributionInstalled(final DistributionInstallerSettings settings)
        throws DistributionPostInstallException {
        if (!Utils.isWindowsOs(settings.getOsName())) {
            final Optional<Path> executableDirectory = Utils
                .getYarnExecutablePath(settings.getInstallDirectory().toPath(), settings.getOsName());
            if (executableDirectory.isPresent()) {
                try {
                    Utils.setFileExecutable(executableDirectory.get(), settings.getOsName());
                } catch (final IOException e) {
                    throw new DistributionPostInstallException(e);
                }
            }
        }
    }
}
