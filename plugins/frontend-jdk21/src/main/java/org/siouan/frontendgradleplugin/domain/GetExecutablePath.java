package org.siouan.frontendgradleplugin.domain;

import java.nio.file.Path;

import lombok.RequiredArgsConstructor;

/**
 * Gets the path to an executable.
 *
 * @since 5.0.1
 */
@RequiredArgsConstructor
public class GetExecutablePath {

    private final ResolveCorepackExecutablePath resolveCorepackExecutablePath;

    private final ResolveNodeExecutablePath resolveNodeExecutablePath;

    private final ResolveNpmExecutablePath resolveNpmExecutablePath;

    private final ResolvePnpmExecutablePath resolvePnpmExecutablePath;

    private final ResolveYarnExecutablePath resolveYarnExecutablePath;

    /**
     * Resolves the path of the executable.
     *
     * @param command Query parameters.
     * @return Executable path.
     * @see ExecutableType
     */
    public Path execute(final GetExecutablePathCommand command) {
        final ResolveExecutablePathCommand resolveExecutablePathCommand = ResolveExecutablePathCommand
            .builder()
            .platform(command.getPlatform())
            .nodeInstallDirectoryPath(command.getNodeInstallDirectoryPath())
            .build();
        return switch (command.getExecutableType()) {
            case COREPACK -> resolveCorepackExecutablePath.execute(resolveExecutablePathCommand);
            case NODE -> resolveNodeExecutablePath.execute(resolveExecutablePathCommand);
            case NPM -> resolveNpmExecutablePath.execute(resolveExecutablePathCommand);
            case PNPM -> resolvePnpmExecutablePath.execute(resolveExecutablePathCommand);
            case YARN -> resolveYarnExecutablePath.execute(resolveExecutablePathCommand);
        };
    }
}
