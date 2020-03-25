package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

public class DistributionValidatorProperties {

    private final Path installDirectory;

    private final Path temporaryDirectory;

    private final URL distributionUrl;

    private final Path distributionFile;

    public DistributionValidatorProperties(@Nonnull final Path installDirectory, @Nonnull final Path temporaryDirectory,
        @Nonnull final URL distributionUrl, @Nonnull final Path distributionFile) {
        this.installDirectory = installDirectory;
        this.temporaryDirectory = temporaryDirectory;
        this.distributionUrl = distributionUrl;
        this.distributionFile = distributionFile;
    }

    @Nonnull
    public Path getInstallDirectory() {
        return installDirectory;
    }

    @Nonnull
    public Path getTemporaryDirectory() {
        return temporaryDirectory;
    }

    @Nonnull
    public URL getDistributionUrl() {
        return distributionUrl;
    }

    @Nonnull
    public Path getDistributionFile() {
        return distributionFile;
    }
}
