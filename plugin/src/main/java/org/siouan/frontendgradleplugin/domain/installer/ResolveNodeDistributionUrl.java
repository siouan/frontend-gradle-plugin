package org.siouan.frontendgradleplugin.domain.installer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import lombok.RequiredArgsConstructor;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;

/**
 * Resolves the URL to download a Node.js distribution.
 */
@RequiredArgsConstructor
public class ResolveNodeDistributionUrl {

    /**
     * Token in the download URL pattern replaced with the version number.
     */
    public static final String VERSION_TOKEN = "VERSION";

    /**
     * Token in the download URL pattern replaced with the architecture ID of the underlying platform.
     */
    public static final String ARCHITECTURE_ID_TOKEN = "ARCH";

    /**
     * Token in the download URL pattern replaced with the archive type supported on the underlying platform.
     */
    public static final String TYPE_TOKEN = "TYPE";

    private final ResolveNodeDistributionArchitectureId resolveNodeDistributionArchitectureId;

    private final ResolveNodeDistributionType resolveNodeDistributionType;

    /**
     * Resolves the URL to download the distribution.
     *
     * @param resolveNodeDistributionUrlCommand Properties to resolve the appropriate distribution.
     * @return An URL.
     * @throws UnsupportedPlatformException If the underlying platform is not supported and a URL cannot be resolved.
     * @throws MalformedURLException If the URL built is invalid due to an invalid definition.
     */
    public URL execute(final ResolveNodeDistributionUrlCommand resolveNodeDistributionUrlCommand)
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = resolveNodeDistributionUrlCommand.getPlatform();
        final String pathPatternWithVersionResolved = resolveNodeDistributionUrlCommand
            .getDownloadUrlPathPattern()
            .replace(VERSION_TOKEN, resolveNodeDistributionUrlCommand.getVersion());
        final String pathPatternWithArchIdResolved;
        if (pathPatternWithVersionResolved.contains(ARCHITECTURE_ID_TOKEN)) {
            pathPatternWithArchIdResolved = pathPatternWithVersionResolved.replace(ARCHITECTURE_ID_TOKEN,
                resolveNodeDistributionArchitectureId
                    .execute(platform)
                    .orElseThrow(
                        () -> new UnsupportedPlatformException(resolveNodeDistributionUrlCommand.getPlatform())));
        } else {
            pathPatternWithArchIdResolved = pathPatternWithVersionResolved;
        }
        final String pathPatternWithTypeResolved;
        if (pathPatternWithArchIdResolved.contains(TYPE_TOKEN)) {
            pathPatternWithTypeResolved = pathPatternWithArchIdResolved.replace(TYPE_TOKEN,
                resolveNodeDistributionType.execute(platform));
        } else {
            pathPatternWithTypeResolved = pathPatternWithArchIdResolved;
        }
        return URI.create(resolveNodeDistributionUrlCommand.getDownloadUrlRoot() + pathPatternWithTypeResolved).toURL();
    }
}
