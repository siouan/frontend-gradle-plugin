package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.siouan.frontendgradleplugin.test.GradleBuildAssertions.assertTaskOutcome;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.GradleBuildFiles;
import org.siouan.frontendgradleplugin.test.GradleHelper;
import org.siouan.frontendgradleplugin.test.PluginTaskOutcome;
import org.siouan.frontendgradleplugin.test.ServerConfigurator;

/**
 * Functional tests to verify authentication and integration of a proxy server with the {@link InstallNodeTask} task.
 * Test cases uses a mock server, acting either as the distribution server, or as the proxy server, and fake
 * Node.js/Yarn distributions, to avoid the download overhead.
 */
class AuthenticationAndProxyFuncTest {

    private static final String NODE_DISTRIBUTION_URL_ROOT = "http://nodejs.org/dist/";

    private static final String NODE_DISTRIBUTION_URL_PATH_PATTERN = "vVERSION/node-vVERSION.zip";

    private static final String DISTRIBUTION_SERVER_HOST = "127.0.0.1";

    private static final String DISTRIBUTION_SERVER_PASSWORD = "dist-password";

    private static final int DISTRIBUTION_SERVER_PORT = 59338;

    private static final String DISTRIBUTION_SERVER_USERNAME = "dist-username";

    private static final String PROXY_SERVER_HOST = "127.0.0.1";

    private static final String PROXY_SERVER_PASSWORD = "proxy-password";

    private static final int PROXY_SERVER_PORT = 59339;

    private static final String PROXY_SERVER_USERNAME = "proxy-username";

    private static WireMockServer distributionServer;

    private static WireMockServer proxyServer;

    @TempDir
    Path projectDirectoryPath;

    @BeforeAll
    static void beforeAll() {
        proxyServer = new WireMockServer(wireMockConfig().port(PROXY_SERVER_PORT));
        distributionServer = new WireMockServer(wireMockConfig().port(DISTRIBUTION_SERVER_PORT));
        proxyServer.start();
        distributionServer.start();
    }

    @AfterAll
    static void afterAll() {
        distributionServer.stop();
        proxyServer.stop();
    }

    @BeforeEach
    void setUp() {
        proxyServer.resetAll();
        distributionServer.resetAll();
    }

    @Test
    void should_fail_installing_node_when_authentication_fails_on_distribution_server() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithDirectConnection("AYr2n{VF");
        GradleBuildFiles.createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = GradleHelper.runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    // Same test as above, just use the exact password.
    @Test
    void should_install_node_when_authentication_succeeds_on_distribution_server() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithDirectConnection(
            DISTRIBUTION_SERVER_PASSWORD);
        GradleBuildFiles.createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = GradleHelper.runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.SUCCESS);
    }

    @Test
    void should_fail_installing_node_when_proxy_server_is_not_reachable() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // Try to download the distribution through a proxy server, but the port used is not the same port than the
        // port of the WireMock proxy server.
        // Manual verification: when proxy server host below is null, the distribution shall be downloaded without
        // the proxy server, and the build shall succeed.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection(
            DISTRIBUTION_SERVER_PORT + 10);
        GradleBuildFiles.createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        // The build should fail with a java.net.ConnectException because the proxy server is not reachable.
        final BuildResult result = GradleHelper.runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    // Same test as above, just use the exact proxy server port.
    @Test
    void should_install_node_through_proxy_server() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection();
        GradleBuildFiles.createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = GradleHelper.runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.SUCCESS);
    }

    @Test
    void should_fail_installing_node_when_authentication_fails_on_proxy_server() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection("xE!s67O?");
        GradleBuildFiles.createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = GradleHelper.runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.FAILED);
    }

    // Same test as above, just use the exact password.
    @Test
    void should_install_node_when_authentication_succeeds_on_proxy_server() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection(
            PROXY_SERVER_PASSWORD);
        GradleBuildFiles.createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result = GradleHelper.runGradle(projectDirectoryPath,
            FrontendGradlePlugin.INSTALL_NODE_TASK_NAME);

        assertTaskOutcome(result, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, PluginTaskOutcome.SUCCESS);
    }

    private FrontendMapBuilder configureNodeServerAndPluginWithDirectConnection(final String distributionServerPassword)
        throws IOException {
        return configureServerAndPlugin(
            new URL("http", DISTRIBUTION_SERVER_HOST, DISTRIBUTION_SERVER_PORT, "/").toString(),
            NODE_DISTRIBUTION_URL_PATH_PATTERN, DISTRIBUTION_SERVER_USERNAME, distributionServerPassword, null, null,
            null, null);
    }

    private FrontendMapBuilder configureNodeServerAndPluginWithProxyConnection() throws IOException {
        return configureNodeServerAndPluginWithProxyConnection(PROXY_SERVER_PORT);
    }

    private FrontendMapBuilder configureNodeServerAndPluginWithProxyConnection(final int proxyServerPort)
        throws IOException {
        return configureServerAndPlugin(NODE_DISTRIBUTION_URL_ROOT, NODE_DISTRIBUTION_URL_PATH_PATTERN, null, null,
            PROXY_SERVER_HOST, proxyServerPort, null, null);
    }

    private FrontendMapBuilder configureNodeServerAndPluginWithProxyConnection(final String proxyServerPassword)
        throws IOException {
        return configureServerAndPlugin(NODE_DISTRIBUTION_URL_ROOT, NODE_DISTRIBUTION_URL_PATH_PATTERN, null, null,
            PROXY_SERVER_HOST, PROXY_SERVER_PORT, PROXY_SERVER_USERNAME, proxyServerPassword);
    }

    private FrontendMapBuilder configureServerAndPlugin(final String nodeDistributionUrlRoot,
        final String nodeDistributionUrlPathPattern, final String distributionServerUsername,
        final String distributionServerPassword, final String httpProxyHost, final Integer httpProxyPort,
        final String httpProxyUsername, final String httpProxyPassword) throws IOException {

        final ServerConfigurator proxyServerConfigurator = new ServerConfigurator(proxyServer,
            "http://" + DISTRIBUTION_SERVER_HOST + ':' + DISTRIBUTION_SERVER_PORT);
        final ServerConfigurator distributionServerConfigurator = new ServerConfigurator(distributionServer, null);

        // Try to download the distribution through a proxy server, but the port used is not the same port than the
        // port of the WireMock proxy server.
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().verboseModeEnabled(false);
        if (nodeDistributionUrlRoot != null) {
            frontendMapBuilder
                .nodeVersion("18.17.1")
                .nodeDistributionUrlRoot(nodeDistributionUrlRoot)
                .nodeDistributionUrlPathPattern(nodeDistributionUrlPathPattern);
            distributionServerConfigurator.withNodeDistribution();
            if (distributionServerUsername != null) {
                frontendMapBuilder
                    .nodeDistributionServerUsername(distributionServerUsername)
                    .nodeDistributionServerPassword(distributionServerPassword);
                distributionServerConfigurator.withAuth(DISTRIBUTION_SERVER_USERNAME, DISTRIBUTION_SERVER_PASSWORD);
            }
        }
        if (httpProxyHost != null) {
            frontendMapBuilder.httpProxyHost(httpProxyHost).httpProxyPort(httpProxyPort);
            if (httpProxyUsername != null) {
                frontendMapBuilder.httpProxyUsername(httpProxyUsername).httpProxyPassword(httpProxyPassword);
                proxyServerConfigurator.withAuth(PROXY_SERVER_USERNAME, PROXY_SERVER_PASSWORD);
            }
        }

        distributionServerConfigurator.configure();
        proxyServerConfigurator.configure();
        // Manual verification: when proxy host below is not defined, the distribution shall be downloaded without
        // the proxy server, and the build shall succeed.
        return frontendMapBuilder;
    }
}
