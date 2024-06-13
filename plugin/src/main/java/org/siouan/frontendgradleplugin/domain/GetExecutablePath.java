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
        final ExecutableType executableType = command.getExecutableType();
        switch (executableType) {
        case COREPACK:
            return getCorepackExecutablePath.execute(resolveExecutablePathCommand);
        case NODE:
            return getNodeExecutablePath.execute(resolveExecutablePathCommand);
        case NPM:
            return getNpmExecutablePath.execute(resolveExecutablePathCommand);
        case PNPM:
            return getPnpmExecutablePath.execute(resolveExecutablePathCommand);
        case YARN:
            return getYarnExecutablePath.execute(resolveExecutablePathCommand);
        default:
            throw new IllegalArgumentException("Unsupported type of executable: " + executableType);
        }
    }
}
