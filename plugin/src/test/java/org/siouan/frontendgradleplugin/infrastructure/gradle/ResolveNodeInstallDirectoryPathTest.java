package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gradle.api.Transformer;
import org.gradle.api.provider.Provider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class ResolveNodeInstallDirectoryPathTest {

    private static final Path DEFAULT_NODE_INSTALL_DIRECTORY_PATH = Paths.get("node");

    @Mock
    private Provider<Boolean> nodeDistributionProvided;

    @Mock
    private Provider<Path> nodeInstallDirectoryFromUser;

    @Mock
    private Provider<Path> defaultNodeInstallDirectory;

    @Mock
    private Provider<Path> nodeInstallDirectoryFromEnvironment;

    @InjectMocks
    private ResolveNodeInstallDirectoryPath resolveNodeInstallDirectoryPath;

    @Test
    void should_return_install_directory_from_user_when_defined_and_distribution_is_not_provided() {
        when(nodeDistributionProvided.flatMap(any(Transformer.class))).then(new ReturnsTransformerResult(false));
        when(nodeInstallDirectoryFromUser.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            nodeInstallDirectoryFromUser);

        assertThat(resolveNodeInstallDirectoryPath.execute(ResolveNodeInstallDirectoryPathCommand
            .builder()
            .nodeInstallDirectoryFromUser(nodeInstallDirectoryFromUser)
            .nodeDistributionProvided(nodeDistributionProvided)
            .nodeInstallDirectoryFromEnvironment(nodeInstallDirectoryFromEnvironment)
            .defaultPath(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)
            .build())).isEqualTo(nodeInstallDirectoryFromUser);

        verifyNoMoreInteractions(nodeDistributionProvided, nodeInstallDirectoryFromUser, defaultNodeInstallDirectory,
            nodeInstallDirectoryFromEnvironment);
    }

    @Test
    void should_return_default_install_directory_when_not_defined_by_user_and_distribution_is_not_provided() {
        when(nodeDistributionProvided.flatMap(any(Transformer.class))).then(new ReturnsTransformerResult(false));
        when(nodeInstallDirectoryFromUser.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            defaultNodeInstallDirectory);

        assertThat(resolveNodeInstallDirectoryPath.execute(ResolveNodeInstallDirectoryPathCommand
            .builder()
            .nodeInstallDirectoryFromUser(nodeInstallDirectoryFromUser)
            .nodeDistributionProvided(nodeDistributionProvided)
            .nodeInstallDirectoryFromEnvironment(nodeInstallDirectoryFromEnvironment)
            .defaultPath(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)
            .build())).isEqualTo(defaultNodeInstallDirectory);

        verifyNoMoreInteractions(nodeDistributionProvided, nodeInstallDirectoryFromUser, defaultNodeInstallDirectory,
            nodeInstallDirectoryFromEnvironment);
    }

    @Test
    void should_return_install_directory_from_user_when_defined_and_distribution_is_provided() {
        when(nodeDistributionProvided.flatMap(any(Transformer.class))).then(new ReturnsTransformerResult(true));
        when(nodeInstallDirectoryFromUser.orElse(nodeInstallDirectoryFromEnvironment)).thenReturn(
            nodeInstallDirectoryFromUser);
        when(nodeInstallDirectoryFromUser.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            nodeInstallDirectoryFromUser);

        assertThat(resolveNodeInstallDirectoryPath.execute(ResolveNodeInstallDirectoryPathCommand
            .builder()
            .nodeInstallDirectoryFromUser(nodeInstallDirectoryFromUser)
            .nodeDistributionProvided(nodeDistributionProvided)
            .nodeInstallDirectoryFromEnvironment(nodeInstallDirectoryFromEnvironment)
            .defaultPath(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)
            .build())).isEqualTo(nodeInstallDirectoryFromUser);

        verifyNoMoreInteractions(nodeDistributionProvided, nodeInstallDirectoryFromUser, defaultNodeInstallDirectory,
            nodeInstallDirectoryFromEnvironment);
    }

    @Test
    void should_return_install_directory_from_environment_when_not_defined_by_user_and_distribution_is_provided() {
        when(nodeDistributionProvided.flatMap(any(Transformer.class))).then(new ReturnsTransformerResult(true));
        when(nodeInstallDirectoryFromUser.orElse(nodeInstallDirectoryFromEnvironment)).thenReturn(
            nodeInstallDirectoryFromEnvironment);
        when(nodeInstallDirectoryFromEnvironment.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            nodeInstallDirectoryFromEnvironment);

        assertThat(resolveNodeInstallDirectoryPath.execute(ResolveNodeInstallDirectoryPathCommand
            .builder()
            .nodeInstallDirectoryFromUser(nodeInstallDirectoryFromUser)
            .nodeDistributionProvided(nodeDistributionProvided)
            .nodeInstallDirectoryFromEnvironment(nodeInstallDirectoryFromEnvironment)
            .defaultPath(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)
            .build())).isEqualTo(nodeInstallDirectoryFromEnvironment);

        verifyNoMoreInteractions(nodeDistributionProvided, nodeInstallDirectoryFromUser, defaultNodeInstallDirectory,
            nodeInstallDirectoryFromEnvironment);
    }

    @Test
    void should_return_default_install_directory_when_distribution_is_provided_without_predefined_path() {
        when(nodeDistributionProvided.flatMap(any(Transformer.class))).then(new ReturnsTransformerResult(true));
        when(nodeInstallDirectoryFromUser.orElse(nodeInstallDirectoryFromEnvironment)).thenReturn(
            nodeInstallDirectoryFromEnvironment);
        when(nodeInstallDirectoryFromEnvironment.orElse(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)).thenReturn(
            defaultNodeInstallDirectory);

        assertThat(resolveNodeInstallDirectoryPath.execute(ResolveNodeInstallDirectoryPathCommand
            .builder()
            .nodeInstallDirectoryFromUser(nodeInstallDirectoryFromUser)
            .nodeDistributionProvided(nodeDistributionProvided)
            .nodeInstallDirectoryFromEnvironment(nodeInstallDirectoryFromEnvironment)
            .defaultPath(DEFAULT_NODE_INSTALL_DIRECTORY_PATH)
            .build())).isEqualTo(defaultNodeInstallDirectory);

        verifyNoMoreInteractions(nodeDistributionProvided, nodeInstallDirectoryFromUser, defaultNodeInstallDirectory,
            nodeInstallDirectoryFromEnvironment);
    }

    @RequiredArgsConstructor
    @Getter
    private static class ReturnsTransformerResult implements Answer<Provider<Path>> {

        private final boolean nodeDistributionProvided;

        @Override
        public Provider<Path> answer(final InvocationOnMock invocationOnMock) {
            return ((Transformer<Provider<Path>, Boolean>) invocationOnMock.getArgument(0)).transform(
                nodeDistributionProvided);
        }
    }
}
