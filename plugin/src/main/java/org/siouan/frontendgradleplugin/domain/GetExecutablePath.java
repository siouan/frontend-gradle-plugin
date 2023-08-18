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

    private final ResolveGlobalCorepackExecutablePath getCorepackExecutablePath;

    private final ResolveGlobalNodeExecutablePath getNodeExecutablePath;

    private final ResolveGlobalNpmExecutablePath getNpmExecutablePath;

    private final ResolveGlobalPnpmExecutablePath getPnpmExecutablePath;

    private final ResolveGlobalYarnExecutablePath getYarnExecutablePath;

    /**
     * Resolves the path of the executable.
     *
     * @param command Query parameters.
     * @return Executable path.
     * @see ExecutableType
     */
    public Path execute(final GetExecutablePathCommand command) {
        final ResolveGlobalExecutablePathCommand resolveGlobalExecutablePathCommand = ResolveGlobalExecutablePathCommand
            .builder()
            .platform(command.getPlatform())
            .nodeInstallDirectoryPath(command.getNodeInstallDirectoryPath())
            .build();
        return switch (command.getExecutableType()) {
            case COREPACK -> getCorepackExecutablePath.execute(resolveGlobalExecutablePathCommand);
            case NODE -> getNodeExecutablePath.execute(resolveGlobalExecutablePathCommand);
            case NPM -> getNpmExecutablePath.execute(resolveGlobalExecutablePathCommand);
            case PNPM -> getPnpmExecutablePath.execute(resolveGlobalExecutablePathCommand);
            case YARN -> getYarnExecutablePath.execute(resolveGlobalExecutablePathCommand);
        };
    }
}
