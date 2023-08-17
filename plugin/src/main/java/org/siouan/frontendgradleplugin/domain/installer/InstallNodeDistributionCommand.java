package org.siouan.frontendgradleplugin.domain.installer;

import java.nio.file.Path;

import lombok.Builder;
import org.siouan.frontendgradleplugin.domain.Platform;

/**
 * Settings to install a distribution.
 *
 * @param platform Underlying platform.
 * @param version Version of the distribution.
 * @param distributionUrlRoot URL root part to build the exact URL to download the distribution.
 * @param distributionUrlPathPattern Trailing path pattern to build the exact URL to download the distribution.
 * @param distributionServerCredentials Credentials to authenticate on the distribution server before download.
 * @param proxySettings Proxy settings used for downloads.
 * @param retrySettings Settings to retry a file download.
 * @param temporaryDirectoryPath Path to a temporary directory.
 * @param installDirectoryPath Path to a directory where the distribution shall be installed.
 * @since 1.1.2
 */
@Builder
public record InstallNodeDistributionCommand(Platform platform, String version, String distributionUrlRoot,
    String distributionUrlPathPattern, Credentials distributionServerCredentials, ProxySettings proxySettings,
    RetrySettings retrySettings, Path temporaryDirectoryPath, Path installDirectoryPath) {}
