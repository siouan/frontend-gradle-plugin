package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.siouan.frontendgradleplugin.domain.model.Credentials;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.domain.usecase.InstallYarnDistribution;

/**
 * Task that downloads and installs a Yarn distribution.
 */
public class YarnInstallTask extends AbstractDistributionInstallTask {

    /**
     * Version of the distribution to download.
     */
    private final Property<String> yarnVersion;

    /**
     * Directory where the distribution shall be installed.
     */
    private final DirectoryProperty yarnInstallDirectory;

    /**
     * URL root part to build the exact URL to download the Yarn distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the Yarn distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionUrlPathPattern;

    /**
     * Username to authenticate on the server providing Yarn distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionServerUsername;

    /**
     * Password to authenticate on the server providing Yarn distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionServerPassword;

    public YarnInstallTask() {
        this.yarnVersion = getProject().getObjects().property(String.class);
        this.yarnInstallDirectory = getProject().getObjects().directoryProperty();
        this.yarnDistributionUrlRoot = getProject().getObjects().property(String.class);
        this.yarnDistributionUrlPathPattern = getProject().getObjects().property(String.class);
        this.yarnDistributionServerUsername = getProject().getObjects().property(String.class);
        this.yarnDistributionServerPassword = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    @Input
    public Property<String> getYarnDistributionUrlRoot() {
        return yarnDistributionUrlRoot;
    }

    @Input
    public Property<String> getYarnDistributionUrlPathPattern() {
        return yarnDistributionUrlPathPattern;
    }

    @Internal
    public Property<String> getYarnDistributionServerUsername() {
        return yarnDistributionServerUsername;
    }

    @Internal
    public Property<String> getYarnDistributionServerPassword() {
        return yarnDistributionServerPassword;
    }

    @OutputDirectory
    @Optional
    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    @Override
    @Nullable
    protected Credentials getDistributionServerCredentials() {
        return yarnDistributionServerUsername
            .map(
                username -> new Credentials(yarnDistributionServerUsername.get(), yarnDistributionServerPassword.get()))
            .getOrNull();
    }

    @Override
    @Nonnull
    protected Class<InstallYarnDistribution> getInstallDistributionClass() {
        return InstallYarnDistribution.class;
    }

    @Nonnull
    @Override
    protected String getDistributionUrlRoot() {
        return yarnDistributionUrlRoot.get();
    }

    @Override
    @Nonnull
    protected InstallSettings getInstallSettings(@Nonnull final Platform platform,
        @Nullable final Credentials distributionServerCredentials, @Nullable final ProxySettings proxySettings) {
        return new InstallSettings(platform, yarnVersion.get(), yarnDistributionUrlRoot.get(),
            yarnDistributionUrlPathPattern.get(), distributionServerCredentials, proxySettings,
            getTemporaryDir().toPath(), yarnInstallDirectory.getAsFile().get().toPath());
    }
}
