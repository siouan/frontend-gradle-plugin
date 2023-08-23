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

    private final ResolveCorepackExecutablePath getCorepackExecutablePath;

    private final ResolveNodeExecutablePath getNodeExecutablePath;

    private final ResolveNpmExecutablePath getNpmExecutablePath;

    private final ResolvePnpmExecutablePath getPnpmExecutablePath;

    private final ResolveYarnExecutablePath getYarnExecutablePath;

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
            case COREPACK -> getCorepackExecutablePath.execute(resolveExecutablePathCommand);
            case NODE -> getNodeExecutablePath.execute(resolveExecutablePathCommand);
            case NPM -> getNpmExecutablePath.execute(resolveExecutablePathCommand);
            case PNPM -> getPnpmExecutablePath.execute(resolveExecutablePathCommand);
            case YARN -> getYarnExecutablePath.execute(resolveExecutablePathCommand);
        };
    }
}
