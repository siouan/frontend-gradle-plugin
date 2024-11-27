package org.siouan.frontendgradleplugin.domain.installer;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Settings to install a distribution.
 *
 * @since 1.1.2
 */
@Builder
@Getter
public class InstallNodeDistributionCommand {

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Version of the distribution.
     */
    private final String version;

    /**
     * URL root part to build the exact URL to download the distribution.
     */
    private final String distributionUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the distribution.
     */
    private final String distributionUrlPathPattern;

    /**
     * Credentials to authenticate on the distribution server before download.
     */
    private final Credentials distributionServerCredentials;

    /**
     * Proxy settings used for downloads.
     */
    private final ProxySettings proxySettings;

    /**
     * Settings to retry a file download.
     */
    private final RetrySettings retrySettings;

    /**
     * Path to a temporary directory.
     */
    private final Path temporaryDirectoryPath;

    /**
     * Path to a directory where the distribution shall be installed.
     */
    private final Path installDirectoryPath;
}
