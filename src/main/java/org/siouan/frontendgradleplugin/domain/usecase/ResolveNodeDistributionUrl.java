package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Resolves the URL to download a Node.js distribution.
 */
public class ResolveNodeDistributionUrl implements DistributionUrlResolver {

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

    public ResolveNodeDistributionUrl(
        @Nonnull final ResolveNodeDistributionArchitectureId resolveNodeDistributionArchitectureId,
        @Nonnull final ResolveNodeDistributionType resolveNodeDistributionType) {
        this.resolveNodeDistributionArchitectureId = resolveNodeDistributionArchitectureId;
        this.resolveNodeDistributionType = resolveNodeDistributionType;
    }

    @Override
    @Nonnull
    public URL execute(@Nonnull final DistributionDefinition distributionDefinition)
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = distributionDefinition.getPlatform();
        final String pathPatternWithVersionResolved = distributionDefinition
            .getDownloadUrlPathPattern()
            .replace(VERSION_TOKEN, distributionDefinition.getVersion());
        final String pathPatternWithArchIdResolved;
        if (pathPatternWithVersionResolved.contains(ARCHITECTURE_ID_TOKEN)) {
            pathPatternWithArchIdResolved = pathPatternWithVersionResolved.replace(ARCHITECTURE_ID_TOKEN,
                resolveNodeDistributionArchitectureId
                    .execute(platform)
                    .orElseThrow(() -> new UnsupportedPlatformException(distributionDefinition.getPlatform())));
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
        return new URL(distributionDefinition.getDownloadUrlRoot() + pathPatternWithTypeResolved);
    }
}
