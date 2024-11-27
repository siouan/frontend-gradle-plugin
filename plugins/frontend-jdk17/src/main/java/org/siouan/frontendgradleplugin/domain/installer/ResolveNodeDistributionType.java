package org.siouan.frontendgradleplugin.domain.installer;

import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Resolves the type of the Node.js distribution.
 *
 * @since 5.0.1
 */
public class ResolveNodeDistributionType {

    /**
     * A gzipped TAR distribution archive.
     */
    public static final String TAR_GZ_TYPE = "tar.gz";

    /**
     * A zipped distribution archive.
     */
    public static final String ZIP_TYPE = "zip";

    /**
     * Resolves the distribution type supported for a given platform.
     *
     * @param platform Platform.
     * @return The distribution type.
     */
    public String execute(final Platform platform) {
        return platform.isWindowsOs() ? ZIP_TYPE : TAR_GZ_TYPE;
    }
}
