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
        final ExecutableType executableType = command.getExecutableType();
        switch (executableType) {
        case COREPACK:
            return getCorepackExecutablePath.execute(resolveGlobalExecutablePathCommand);
        case NODE:
            return getNodeExecutablePath.execute(resolveGlobalExecutablePathCommand);
        case NPM:
            return getNpmExecutablePath.execute(resolveGlobalExecutablePathCommand);
        case PNPM:
            return getPnpmExecutablePath.execute(resolveGlobalExecutablePathCommand);
        case YARN:
            return getYarnExecutablePath.execute(resolveGlobalExecutablePathCommand);
        default:
            throw new IllegalArgumentException("Unsupported type of executable: " + executableType);
        }
    }
}
