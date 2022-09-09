package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.api.provider.Provider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolveNodeInstallDirectoryPathTest {

    private static final Path DEFAULT_NODE_INSTALL_DIRECTORY_PATH = Paths.get("node");

    @Mock
    private Provider<Path> primaryNodeInstallDirectoryPath;

    @Mock
    private Provider<Path> primaryNodeInstallDirectoryPath2;

    @Mock
    private Provider<Path> defaultNodeInstallDirectoryPath;

    @Mock
    private Provider<Path> nodeInstallDirectoryPathFromEnvironment;

    @InjectMocks
    private ResolveNodeInstallDirectoryPath resolveNodeInstallDirectoryPath;

    @Test
    void should_return_primary_node_install_directory_when_defined_and_distribution_is_not_provided() {
        when(primaryNodeInstallDirectoryPath.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            primaryNodeInstallDirectoryPath);

        assertThat(resolveNodeInstallDirectoryPath.execute(false, primaryNodeInstallDirectoryPath,
            DEFAULT_NODE_INSTALL_DIRECTORY_PATH, nodeInstallDirectoryPathFromEnvironment)).isEqualTo(
            primaryNodeInstallDirectoryPath);

        verifyNoMoreInteractions(primaryNodeInstallDirectoryPath, primaryNodeInstallDirectoryPath2,
            defaultNodeInstallDirectoryPath, nodeInstallDirectoryPathFromEnvironment);
    }

    @Test
    void should_return_default_node_install_directory_when_primary_is_not_defined_and_distribution_is_not_provided() {
        when(primaryNodeInstallDirectoryPath.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            defaultNodeInstallDirectoryPath);

        assertThat(resolveNodeInstallDirectoryPath.execute(false, primaryNodeInstallDirectoryPath,
            DEFAULT_NODE_INSTALL_DIRECTORY_PATH, nodeInstallDirectoryPathFromEnvironment)).isEqualTo(
            defaultNodeInstallDirectoryPath);

        verifyNoMoreInteractions(primaryNodeInstallDirectoryPath, primaryNodeInstallDirectoryPath2,
            defaultNodeInstallDirectoryPath, nodeInstallDirectoryPathFromEnvironment);
    }

    @Test
    void should_return_primary_node_install_directory_when_defined_and_distribution_is_provided() {
        when(primaryNodeInstallDirectoryPath.orElse(nodeInstallDirectoryPathFromEnvironment)).thenReturn(
            primaryNodeInstallDirectoryPath);
        when(primaryNodeInstallDirectoryPath.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            primaryNodeInstallDirectoryPath);

        assertThat(resolveNodeInstallDirectoryPath.execute(true, primaryNodeInstallDirectoryPath,
            DEFAULT_NODE_INSTALL_DIRECTORY_PATH, nodeInstallDirectoryPathFromEnvironment)).isEqualTo(
            primaryNodeInstallDirectoryPath);

        verifyNoMoreInteractions(primaryNodeInstallDirectoryPath, primaryNodeInstallDirectoryPath2,
            defaultNodeInstallDirectoryPath, nodeInstallDirectoryPathFromEnvironment);
    }

    @Test
    void should_return_node_install_directory_from_environment_when_primary_is_not_defined_and_distribution_is_provided() {
        when(primaryNodeInstallDirectoryPath.orElse(nodeInstallDirectoryPathFromEnvironment)).thenReturn(
            nodeInstallDirectoryPathFromEnvironment);
        when(nodeInstallDirectoryPathFromEnvironment.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            nodeInstallDirectoryPathFromEnvironment);

        assertThat(resolveNodeInstallDirectoryPath.execute(true, primaryNodeInstallDirectoryPath,
            DEFAULT_NODE_INSTALL_DIRECTORY_PATH, nodeInstallDirectoryPathFromEnvironment)).isEqualTo(
            nodeInstallDirectoryPathFromEnvironment);

        verifyNoMoreInteractions(primaryNodeInstallDirectoryPath, primaryNodeInstallDirectoryPath2,
            defaultNodeInstallDirectoryPath, nodeInstallDirectoryPathFromEnvironment);
    }

    @Test
    void should_return_default_node_install_directory_when_distribution_is_provided_without_predefined_configuration() {
        when(primaryNodeInstallDirectoryPath.orElse(nodeInstallDirectoryPathFromEnvironment)).thenReturn(
            nodeInstallDirectoryPathFromEnvironment);
        when(nodeInstallDirectoryPathFromEnvironment.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            defaultNodeInstallDirectoryPath);

        assertThat(resolveNodeInstallDirectoryPath.execute(true, primaryNodeInstallDirectoryPath,
            DEFAULT_NODE_INSTALL_DIRECTORY_PATH, nodeInstallDirectoryPathFromEnvironment)).isEqualTo(
            defaultNodeInstallDirectoryPath);

        verifyNoMoreInteractions(primaryNodeInstallDirectoryPath, primaryNodeInstallDirectoryPath2,
            defaultNodeInstallDirectoryPath, nodeInstallDirectoryPathFromEnvironment);
    }
}
