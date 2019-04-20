package org.siouan.frontendgradleplugin.node;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

import org.siouan.frontendgradleplugin.Utils;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolverException;

/**
 * This resolver determines the name of a distribution supported by the underlying OS and JRE.
 */
public class NodeDistributionUrlResolver implements DistributionUrlResolver {

    private final String version;

    private final String distributionUrl;

    private final String osName;

    private final String jreArch;

    /**
     * Builds a resolver using the local platform characteristics to determine the appropriate distro (Windows, Linux,
     * i86, x64 compliant...).
     *
     * @param version Version.
     * @param distributionUrl URL to download the distribution.
     */
    public NodeDistributionUrlResolver(final String version, final String distributionUrl) {
        this(version, distributionUrl, System.getProperty("os.name"), System.getProperty("os.arch"));
    }

    /**
     * Builds a resolver using the local platform characteristics to determine the appropriate distro (Windows, Linux,
     * i86, x64 compliant...).
     *
     * @param version Version.
     * @param distributionUrl URL to download the distribution.
     */
    public NodeDistributionUrlResolver(final String version, final String distributionUrl, final String osName,
        final String jreArch) {
        if ((version == null) && (distributionUrl == null)) {
            throw new IllegalArgumentException(
                "Either the Node version or a download URL must be configured (see plugin's DSL).");
        }
        this.version = version;
        this.distributionUrl = distributionUrl;
        this.osName = osName;
        this.jreArch = jreArch;
    }

    public URL resolve() throws DistributionUrlResolverException {
        final String urlAsString;
        if (distributionUrl == null) {
            final StringBuilder buffer = new StringBuilder();
            buffer.append("https://nodejs.org/dist/v");
            buffer.append(version);
            buffer.append("/node-v");
            buffer.append(version);
            final Optional<String> extension = resolveExtension();
            if (extension.isPresent()) {
                buffer.append(extension.get());
            } else {
                throw new DistributionUrlResolverException(
                    "This platform is not supported yet: " + osName + ", " + jreArch);
            }

            urlAsString = buffer.toString();
        } else {
            urlAsString = distributionUrl;
        }

        try {
            return URI.create(urlAsString).toURL();
        } catch (final MalformedURLException e) {
            throw new DistributionUrlResolverException(e);
        }
    }

    private Optional<String> resolveExtension() {
        final String extension;
        if (Utils.is64BitsArch(jreArch)) {
            if (Utils.isWindowsOs(osName)) {
                extension = "-win-x64.zip";
            } else if (Utils.isLinuxOs(osName)) {
                extension = "-linux-x64.tar.gz";
            } else if (Utils.isMacOs(osName)) {
                extension = "-darwin-x64.tar.gz";
            } else {
                extension = null;
            }
        } else {
            if (Utils.isWindowsOs(osName)) {
                extension = "-win-x86.zip";
            } else {
                extension = null;
            }
        }

        return Optional.ofNullable(extension);
    }
}
