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
import org.siouan.frontendgradleplugin.domain.usecase.InstallNodeDistribution;

/**
 * Task that downloads and installs a Node distribution.
 */
public class NodeInstallTask extends AbstractDistributionInstallTask {

    /**
     * Version of the Node distribution to download.
     */
    private final Property<String> nodeVersion;

    /**
     * Directory where the Node distribution shall be installed.
     */
    private final DirectoryProperty nodeInstallDirectory;

    /**
     * URL pattern to download the Node.js distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionUrlPattern;

    /**
     * Username to authenticate on the server providing Node.js distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionServerUsername;

    /**
     * Password to authenticate on the server providing Node.js distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionServerPassword;

    public NodeInstallTask() {
        super();
        this.nodeVersion = getProject().getObjects().property(String.class);
        this.nodeInstallDirectory = getProject().getObjects().directoryProperty();
        this.nodeDistributionUrlPattern = getProject().getObjects().property(String.class);
        this.nodeDistributionServerUsername = getProject().getObjects().property(String.class);
        this.nodeDistributionServerPassword = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    @Input
    public Property<String> getNodeDistributionUrlPattern() {
        return nodeDistributionUrlPattern;
    }

    @OutputDirectory
    @Optional
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Internal
    public Property<String> getNodeDistributionServerUsername() {
        return nodeDistributionServerUsername;
    }

    @Internal
    public Property<String> getNodeDistributionServerPassword() {
        return nodeDistributionServerPassword;
    }

    @Override
    @Nullable
    protected Credentials getDistributionServerCredentials() {
        return nodeDistributionServerUsername
            .map(
                username -> new Credentials(nodeDistributionServerUsername.get(), nodeDistributionServerPassword.get()))
            .getOrNull();
    }

    @Override
    @Nonnull
    protected Class<InstallNodeDistribution> getInstallDistributionClass() {
        return InstallNodeDistribution.class;
    }

    @Override
    @Nonnull
    protected InstallSettings getInstallSettings(@Nonnull final Platform platform,
        @Nullable final Credentials distributionServerCredentials, @Nonnull final ProxySettings proxySettings) {
        return new InstallSettings(platform, nodeVersion.get(), nodeDistributionUrlPattern.get(),
            distributionServerCredentials, proxySettings, getTemporaryDir().toPath(),
            nodeInstallDirectory.getAsFile().get().toPath());
    }
}
