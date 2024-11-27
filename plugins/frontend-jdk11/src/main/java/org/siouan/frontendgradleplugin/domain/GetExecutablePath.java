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
        final ExecutableType executableType = command.getExecutableType();
        switch (executableType) {
        case COREPACK:
            return resolveCorepackExecutablePath.execute(resolveExecutablePathCommand);
        case NODE:
            return resolveNodeExecutablePath.execute(resolveExecutablePathCommand);
        case NPM:
            return resolveNpmExecutablePath.execute(resolveExecutablePathCommand);
        case PNPM:
            return resolvePnpmExecutablePath.execute(resolveExecutablePathCommand);
        case YARN:
            return resolveYarnExecutablePath.execute(resolveExecutablePathCommand);
        default:
            throw new IllegalArgumentException("Unsupported type of executable: " + executableType);
        }
    }
}
