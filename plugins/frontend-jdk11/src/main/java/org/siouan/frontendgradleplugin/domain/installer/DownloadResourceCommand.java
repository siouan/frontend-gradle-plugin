package org.siouan.frontendgradleplugin.domain.installer;

import java.net.URL;
import java.nio.file.Path;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Settings to download a resource.
 *
 * @since 2.0.0
 */
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DownloadResourceCommand {

    /**
     * URL to download the resource.
     */
    @EqualsAndHashCode.Include
    private final URL resourceUrl;

    /**
     * Credentials to authenticate on the server before download.
     */
    @EqualsAndHashCode.Include
    private final Credentials serverCredentials;

    /**
     * Proxy settings used for the connection.
     */
    @EqualsAndHashCode.Include
    private final ProxySettings proxySettings;

    /**
     * Settings to retry a file download.
     *
     * @since 7.1.0
     */
    @EqualsAndHashCode.Include
    private final RetrySettings retrySettings;

    /**
     * Path to a temporary file.
     */
    @EqualsAndHashCode.Include
    private final Path temporaryFilePath;

    /**
     * Path to a destination file.
     */
    @EqualsAndHashCode.Include
    private final Path destinationFilePath;
}
