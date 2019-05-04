package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link NodePostInstallAction} class.
 */
class NodePostInstallActionTest {

    @TempDir
    protected File temporaryDirectory;

    @Test
    void shouldDoNothingUnderWindows() throws DistributionPostInstallException {
        final NodePostInstallAction action = new NodePostInstallAction();
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(null, "Windows NT", null, null,
            null, action);

        action.onDistributionInstalled(settings);
    }

    @Test
    void shouldDoNothingIfNpmExecutableNotFound() throws DistributionPostInstallException {
        final NodePostInstallAction action = new NodePostInstallAction();
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(null, "Linux", null, null,
            temporaryDirectory, action);

        action.onDistributionInstalled(settings);
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void shouldSetTargetExecutableWhenNpmExecutableIsSymbolicLink()
        throws DistributionPostInstallException, IOException {
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        final Set<PosixFilePermission> permissions = EnumSet
            .of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
        final Path targetFile = Files
            .createFile(binDirectory.resolve("target"), PosixFilePermissions.asFileAttribute(permissions));
        Files.createSymbolicLink(binDirectory.resolve("npm"), targetFile);
        final NodePostInstallAction action = new NodePostInstallAction();
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(null, "Mac OS X", null, null,
            temporaryDirectory, action);

        action.onDistributionInstalled(settings);

        final Set<PosixFilePermission> newPermissions = Files.getPosixFilePermissions(targetFile);
        assertThat(newPermissions).hasSize(permissions.size() + 1).containsAll(permissions)
            .contains(PosixFilePermission.OWNER_EXECUTE);
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void shouldRestoreSymbolicLinkWhenNpmExecutableIsFile() throws DistributionPostInstallException, IOException {
        final Path targetDirectory = Files.createDirectories(
            temporaryDirectory.toPath().resolve("lib").resolve("node_modules").resolve("npm").resolve("bin"));
        Files.createFile(targetDirectory.resolve("npm-cli.js"));
        final Path binDirectory = Files.createDirectory(temporaryDirectory.toPath().resolve("bin"));
        final Set<PosixFilePermission> permissions = EnumSet
            .of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
        final Path npmExecutable = Files
            .createFile(binDirectory.resolve("npm"), PosixFilePermissions.asFileAttribute(permissions));
        final NodePostInstallAction action = new NodePostInstallAction();
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(null, "Linux", null, null,
            temporaryDirectory, action);

        action.onDistributionInstalled(settings);

        assertThat(Files.isSymbolicLink(npmExecutable)).isTrue();
        final Set<PosixFilePermission> newPermissions = Files.getPosixFilePermissions(npmExecutable);
        assertThat(newPermissions).hasSize(permissions.size() + 1).containsAll(permissions)
            .contains(PosixFilePermission.OWNER_EXECUTE);
    }
}
