package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Optional;
import java.util.Set;

/**
 * Action that checks and fixes symbolic links that disappeared when the distribution was exploded.
 *
 * @since 1.1.2
 */
public class NodePostInstallAction implements DistributionPostInstallAction {

    @Override
    public void onDistributionInstalled(final DistributionInstallerSettings settings)
        throws DistributionPostInstallException {
        if (!Utils.isWindowsOs(settings.getOsName())) {
            // Checks, and fixes if required, the NPM executable is a symbolic link. Due to Gradle 'tarTree'
            // limitations, symbolic links are not preserved during TAR exploding. They must be restored or NPM won't
            // work.
            final Path installDirectory = settings.getInstallDirectory().toPath();
            final Optional<Path> npmExecutablePath = Utils.getNpmExecutablePath(installDirectory, settings.getOsName());
            if (npmExecutablePath.isPresent()) {
                final Path npmExecutable = npmExecutablePath.get();
                try {
                    // The original link is a relative link. We must preserve it and its permissions.
                    final Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(npmExecutable);
                    if (!Files.isSymbolicLink(npmExecutable)) {
                        Files.delete(npmExecutable);
                        Files.createSymbolicLink(npmExecutable, installDirectory.resolve("bin").relativize(
                            installDirectory.resolve("lib").resolve("node_modules").resolve("npm").resolve("bin")
                                .resolve("npm-cli.js")));
                    }
                    Utils.setFileExecutable(npmExecutable, permissions, settings.getOsName());
                } catch (final IOException e) {
                    throw new DistributionPostInstallException(e);
                }
            }
        }
    }
}
