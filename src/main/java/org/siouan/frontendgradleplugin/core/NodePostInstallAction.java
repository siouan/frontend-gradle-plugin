package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class NodePostInstallAction implements DistributionPostInstallAction {

    @Override
    public void onDistributionInstalled(final DistributionInstallerSettings settings)
        throws DistributionPostInstallException {
        if (!Utils.isWindowsOs(settings.getOsName())) {
            final Optional<Path> excutableDirectory = Utils
                .getNodeExecutablePath(settings.getInstallDirectory().toPath(), settings.getOsName());
            if (excutableDirectory.isPresent()) {
                try {
                    Utils.setFileExecutable(excutableDirectory.get(), settings.getOsName());
                } catch (final IOException e) {
                    throw new DistributionPostInstallException(e);
                }
            }
        }
    }
}
