package org.siouan.frontendgradleplugin.domain.installer;

import java.net.URL;
import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Settings for distribution validation.
 *
 * @since 2.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ValidateNodeDistributionCommand {

    /**
     * Path to a temporary directory.
     */
    @EqualsAndHashCode.Include
    private final Path temporaryDirectoryPath;

    /**
     * URL used to download the distribution.
     */
    @EqualsAndHashCode.Include
    private final URL distributionUrl;

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
     * Path to the distribution archive.
     */
    @EqualsAndHashCode.Include
    private final Path distributionFilePath;
}
