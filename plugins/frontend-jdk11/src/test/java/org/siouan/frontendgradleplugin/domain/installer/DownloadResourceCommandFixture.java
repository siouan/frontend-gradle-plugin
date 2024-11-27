package org.siouan.frontendgradleplugin.domain.installer;

import java.net.MalformedURLException;
import java.nio.file.Path;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DownloadResourceCommandFixture {

    public static DownloadResourceCommand aCommand(final Path resourceFilePath, final ProxySettings proxySettings,
        final RetrySettings retrySettings, final Path temporaryFilePath, final Path destinationFilePath) {
        try {
            return DownloadResourceCommand
                .builder()
                .resourceUrl(resourceFilePath.toUri().toURL())
                .serverCredentials(null)
                .proxySettings(proxySettings)
                .retrySettings(retrySettings)
                .temporaryFilePath(temporaryFilePath)
                .destinationFilePath(destinationFilePath)
                .build();
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
