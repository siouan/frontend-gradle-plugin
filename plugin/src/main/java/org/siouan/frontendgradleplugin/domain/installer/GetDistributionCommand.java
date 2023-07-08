package org.siouan.frontendgradleplugin.domain.installer;

import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Settings to get a distribution.
 *
 * @since 1.1.3
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GetDistributionCommand {

    /**
     * Underlying platform.
     */
    @EqualsAndHashCode.Include
    private final Platform platform;

    /**
     * Version.
     */
    @EqualsAndHashCode.Include
    private final String version;

    /**
     * URL root part to build the exact URL to download the distribution.
     */
    @EqualsAndHashCode.Include
    private final String distributionUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the distribution.
     */
    @EqualsAndHashCode.Include
    private final String distributionUrlPathPattern;

    /**
     * Credentials to authenticate on the distribution server before download.
     */
    @EqualsAndHashCode.Include
    private final Credentials distributionServerCredentials;

    /**
     * Proxy settings used for downloads.
     */
    @EqualsAndHashCode.Include
    private final ProxySettings proxySettings;

    /**
     * Path to a temporary directory.
     */
    @EqualsAndHashCode.Include
    private final Path temporaryDirectoryPath;
}
