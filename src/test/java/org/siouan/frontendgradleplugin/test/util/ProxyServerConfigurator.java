package org.siouan.frontendgradleplugin.test.util;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import javax.annotation.Nonnull;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;

/**
 * A configurator of a proxy server.
 */
public class ProxyServerConfigurator {

    private final WireMockServer proxyServer;

    private boolean nodeEnabled;

    private boolean yarnEnabled;

    private String distributionServerUsername;

    private String distributionServerPassword;

    private String proxyServerUsername;

    private String proxyServerPassword;

    private ProxyServerConfigurator(@Nonnull final WireMockServer proxyServer) {
        this.proxyServer = proxyServer;
        this.nodeEnabled = false;
        this.yarnEnabled = false;
    }

    @Nonnull
    public static ProxyServerConfigurator getInstance(@Nonnull final WireMockServer proxyServer) {
        return new ProxyServerConfigurator(proxyServer);
    }

    public void withNodeDistribution() {
        this.nodeEnabled = true;
    }

    public void withYarnDistribution() {
        this.yarnEnabled = true;
    }

    public void withDistributionServerAuth(@Nonnull final String username, @Nonnull final String password) {
        this.distributionServerUsername = username;
        this.distributionServerPassword = password;
    }

    public void withProxyServerAuth(@Nonnull final String username, @Nonnull final String password) {
        this.proxyServerUsername = username;
        this.proxyServerPassword = password;
    }

    public void configure() throws IOException {
        MappingBuilder mappingBuilder;
        if (nodeEnabled) {
            // The proxy server is configured to return test resources to avoid network overhead.
            mappingBuilder = get(urlPathMatching("^.*/node-v[^/]+$"));
            withDistributionServerAuth(mappingBuilder);
            withProxyServerAuth(mappingBuilder);
            proxyServer.stubFor(mappingBuilder.willReturn(
                aResponse().withBody(Files.readAllBytes(getResourcePath("node-v12.16.3.zip")))));

            mappingBuilder = get(urlPathMatching("^.*/SHASUMS256.txt$"));
            withDistributionServerAuth(mappingBuilder);
            withProxyServerAuth(mappingBuilder);
            proxyServer.stubFor(
                mappingBuilder.willReturn(aResponse().withBody(Files.readAllBytes(getResourcePath("SHASUMS256.txt")))));
        }

        if (yarnEnabled) {
            // The proxy server is configured to return test resources to avoid network overhead.
            mappingBuilder = get(urlPathMatching("^.*/yarn-v[^/]+$"));
            withDistributionServerAuth(mappingBuilder);
            withProxyServerAuth(mappingBuilder);
            proxyServer.stubFor(mappingBuilder.willReturn(
                aResponse().withBody(Files.readAllBytes(getResourcePath("yarn-v1.22.4.tar.gz")))));
        }
    }

    private void withDistributionServerAuth(@Nonnull final MappingBuilder mappingBuilder) {
        if (distributionServerUsername != null) {
            mappingBuilder.withBasicAuth(distributionServerUsername, distributionServerPassword);
        }
    }

    private void withProxyServerAuth(@Nonnull final MappingBuilder mappingBuilder) {
        if (proxyServerUsername != null) {
            final String proxyServerCredentials = proxyServerUsername + ':' + proxyServerPassword;
            mappingBuilder.withHeader("Proxy-Authorization",
                equalTo("Basic " + Base64.getEncoder().encodeToString(proxyServerCredentials.getBytes())));
        }
    }
}
