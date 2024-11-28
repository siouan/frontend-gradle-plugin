package org.siouan.frontendgradleplugin.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import org.apache.hc.core5.http.HttpHeaders;

/**
 * A HTTP server configurator.
 */
public class ServerConfigurator {

    private final WireMockServer server;

    private final String proxyTargetHost;

    private boolean nodeEnabled;

    private String username;

    private String password;

    public ServerConfigurator(final WireMockServer server, final String proxyTargetHost) {
        this.server = server;
        this.proxyTargetHost = proxyTargetHost;
        this.nodeEnabled = false;
    }

    public void withNodeDistribution() {
        this.nodeEnabled = true;
    }

    public void withAuth(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public void configure() throws IOException {
        MappingBuilder mappingBuilder;
        if (proxyTargetHost == null) {
            if (nodeEnabled) {
                // The distribution server is configured to return test resources to avoid network overhead.
                mappingBuilder = get(urlPathMatching("^.*/node-v[^/]+$"));
                withAuth(mappingBuilder);
                server.stubFor(mappingBuilder.willReturn(
                    aResponse().withBody(Files.readAllBytes(getResourcePath("node-v22.11.0.zip")))));

                mappingBuilder = get(urlPathMatching("^.*/SHASUMS256.txt$"));
                withAuth(mappingBuilder);
                server.stubFor(mappingBuilder.willReturn(
                    aResponse().withBody(Files.readAllBytes(getResourcePath("SHASUMS256.txt")))));
            }
        } else {
            // The proxy server is configured to forward requests to the distribution server.
            mappingBuilder = get(urlPathMatching("^.*$"));
            if (username != null) {
                // Add a condition if Basic authentication is required
                mappingBuilder.withHeader(HttpHeaders.PROXY_AUTHORIZATION,
                    equalTo(buildBasicToken(username, password)));
            }
            server.stubFor(mappingBuilder.willReturn(aResponse().proxiedFrom(proxyTargetHost)
                // https://datatracker.ietf.org/doc/html/rfc2817#section-3.1
                // WireMock 3.9.1 does not support optional TLS upgrade issued by Apache HttpClient 5.4+ with headers
                // "Upgrade" and "Connection" to secure communications, which lead to a 400 HTTP response. To prevent
                // such errors, let's remove these request headers in the context of a JUnit test case.
                .withRemoveRequestHeader(HttpHeaders.UPGRADE).withRemoveRequestHeader(HttpHeaders.CONNECTION)));

            if (username != null) {
                // Simulate HTTP 407 responses when proxy authorization is missing or is incorrect.
                mappingBuilder = get(urlPathMatching("^.*$"));
                mappingBuilder.andMatching(request -> MatchResult.of(
                    !request.containsHeader(HttpHeaders.PROXY_AUTHORIZATION) || !request
                        .getHeader(HttpHeaders.PROXY_AUTHORIZATION)
                        .equals(buildBasicToken(username, password))));
                server.stubFor(mappingBuilder.willReturn(aResponse().withStatus(407)));
            }
        }
    }

    private void withAuth(final MappingBuilder mappingBuilder) {
        if (username != null) {
            mappingBuilder.withBasicAuth(username, password);
        }
    }

    private String buildBasicToken(final String username, final String password) {
        final String credentials = username + ':' + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
