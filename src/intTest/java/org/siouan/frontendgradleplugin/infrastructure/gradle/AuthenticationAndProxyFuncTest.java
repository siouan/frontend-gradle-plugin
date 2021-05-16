package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskFailed;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildAssertions.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.test.util.GradleBuildFiles.createBuildFile;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradle;
import static org.siouan.frontendgradleplugin.test.util.GradleHelper.runGradleAndExpectFailure;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.util.ServerConfigurator;

/**
 * Functional tests to verify authentication and integration of a proxy server with the {@link NodeInstallTask} task and
 * the {@link YarnInstallTask} task. Test cases uses a mock server, acting either as the distribution server, or as the
 * proxy server, and fake Node.js/Yarn distributions, to avoid the download overhead.
 */
class AuthenticationAndProxyFuncTest {

    private static final String NODE_DISTRIBUTION_URL_ROOT = "http://nodejs.org/dist/";

    private static final String NODE_DISTRIBUTION_URL_PATH_PATTERN = "vVERSION/node-vVERSION.zip";

    private static final String YARN_DISTRIBUTION_URL_ROOT = "http://github.com/yarnpkg/yarn/releases/download/";

    private static final String YARN_DISTRIBUTION_URL_PATH_PATTERN = "vVERSION/yarn-vVERSION.tar.gz";

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
    void shouldFailInstallingNodeWhenAuthenticationFailsOnDistributionServer() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithDirectConnection("AYr2n{VF");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    // Same test as above, just use the exact password.
    @Test
    void shouldInstallNodeWhenAuthenticationSucceedsOnDistributionServer() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithDirectConnection(
            DISTRIBUTION_SERVER_PASSWORD);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingNodeWhenProxyServerIsNotReachable() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // Try to download the distribution through a proxy server, but the port used is not the same port than the
        // port of the WireMock proxy server.
        // Manual verification: when proxy server host below is null, the distribution shall be downloaded without
        // the proxy server, and the build shall succeed.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection(
            DISTRIBUTION_SERVER_PORT + 10);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        // The build should fail with a java.net.ConnectException because the proxy server is not reachable.
        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    // Same test as above, just use the exact proxy server port.
    @Test
    void shouldInstallNodeThroughProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection();
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingNodeWhenAuthenticationFailsOnProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection("xE!s67O?");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskFailed(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    // Same test as above, just use the exact password.
    @Test
    void shouldInstallNodeWhenAuthenticationSucceedsOnProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureNodeServerAndPluginWithProxyConnection(
            PROXY_SERVER_PASSWORD);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenAuthenticationFailsOnDistributionServer() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureYarnServerAndPluginWithDirectConnection("AYr2n{VF");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    // Same test as above, just use the exact password.
    @Test
    void shouldInstallYarnWhenAuthenticationSucceedsOnDistributionServer() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureYarnServerAndPluginWithDirectConnection(
            DISTRIBUTION_SERVER_PASSWORD);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenProxyServerIsNotReachable() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // Try to download the distribution through a proxy server, but the port used is not the same port than the
        // port of the WireMock proxy server.
        // Manual verification: when proxy server host below is null, the distribution shall be downloaded without
        // the proxy server, and the build shall succeed.
        final FrontendMapBuilder frontendMapBuilder = configureYarnServerAndPluginWithProxyConnection(
            DISTRIBUTION_SERVER_PORT + 10);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        // The build should fail with a java.net.ConnectException because the proxy server is not reachable.
        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    // Same test as above, just use the exact proxy server port.
    @Test
    void shouldInstallYarnThroughProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureYarnServerAndPluginWithProxyConnection(
            DISTRIBUTION_SERVER_PORT);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenAuthenticationFailsOnProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureYarnServerAndPluginWithProxyConnection("xE!s67O?");
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradleAndExpectFailure(projectDirectoryPath,
            FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskFailed(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    // Same test as above, just use the exact password.
    @Test
    void shouldInstallYarnWhenAuthenticationSucceedsOnProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureYarnServerAndPluginWithProxyConnection(
            PROXY_SERVER_PASSWORD);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Nonnull
    private FrontendMapBuilder configureNodeServerAndPluginWithDirectConnection(
        @Nonnull final String distributionServerPassword) throws IOException {
        return configureServerAndPlugin(
            new URL("http", DISTRIBUTION_SERVER_HOST, DISTRIBUTION_SERVER_PORT, "/").toString(),
            NODE_DISTRIBUTION_URL_PATH_PATTERN, null, null, DISTRIBUTION_SERVER_USERNAME, distributionServerPassword,
            null, null, null, null);
    }

    @Nonnull
    private FrontendMapBuilder configureNodeServerAndPluginWithProxyConnection() throws IOException {
        return configureNodeServerAndPluginWithProxyConnection(PROXY_SERVER_PORT);
    }

    @Nonnull
    private FrontendMapBuilder configureNodeServerAndPluginWithProxyConnection(final int proxyServerPort)
        throws IOException {
        return configureServerAndPlugin(NODE_DISTRIBUTION_URL_ROOT, NODE_DISTRIBUTION_URL_PATH_PATTERN, null, null,
            null, null, PROXY_SERVER_HOST, proxyServerPort, null, null);
    }

    @Nonnull
    private FrontendMapBuilder configureNodeServerAndPluginWithProxyConnection(
        @Nonnull final String proxyServerPassword) throws IOException {
        return configureServerAndPlugin(NODE_DISTRIBUTION_URL_ROOT, NODE_DISTRIBUTION_URL_PATH_PATTERN, null, null,
            null, null, PROXY_SERVER_HOST, PROXY_SERVER_PORT, PROXY_SERVER_USERNAME, proxyServerPassword);
    }

    @Nonnull
    private FrontendMapBuilder configureYarnServerAndPluginWithDirectConnection(
        @Nonnull final String distributionServerPassword) throws IOException {
        return configureServerAndPlugin(null, null,
            new URL("http", DISTRIBUTION_SERVER_HOST, DISTRIBUTION_SERVER_PORT, "/").toString(),
            YARN_DISTRIBUTION_URL_PATH_PATTERN, DISTRIBUTION_SERVER_USERNAME, distributionServerPassword, null, null,
            null, null);
    }

    @Nonnull
    private FrontendMapBuilder configureYarnServerAndPluginWithProxyConnection(final int proxyServerPort)
        throws IOException {
        return configureServerAndPlugin(null, null, YARN_DISTRIBUTION_URL_ROOT, YARN_DISTRIBUTION_URL_PATH_PATTERN,
            null, null, PROXY_SERVER_HOST, proxyServerPort, null, null);
    }

    @Nonnull
    private FrontendMapBuilder configureYarnServerAndPluginWithProxyConnection(
        @Nonnull final String proxyServerPassword) throws IOException {
        return configureServerAndPlugin(null, null, YARN_DISTRIBUTION_URL_ROOT, YARN_DISTRIBUTION_URL_PATH_PATTERN,
            null, null, PROXY_SERVER_HOST, PROXY_SERVER_PORT, PROXY_SERVER_USERNAME, proxyServerPassword);
    }

    @Nonnull
    private FrontendMapBuilder configureServerAndPlugin(@Nullable final String nodeDistributionUrlRoot,
        @Nullable final String nodeDistributionUrlPathPattern, @Nullable final String yarnDistributionUrlRoot,
        @Nullable final String yarnDistributionUrlPathPattern, @Nullable final String distributionServerUsername,
        @Nullable final String distributionServerPassword, @Nullable final String httpProxyHost,
        @Nullable final Integer httpProxyPort, @Nullable final String httpProxyUsername,
        @Nullable final String httpProxyPassword) throws IOException {

        final ServerConfigurator proxyServerConfigurator = new ServerConfigurator(proxyServer,
            "http://" + DISTRIBUTION_SERVER_HOST + ':' + DISTRIBUTION_SERVER_PORT);
        final ServerConfigurator distributionServerConfigurator = new ServerConfigurator(distributionServer, null);

        // Try to download the distribution through a proxy server, but the port used is not the same port than the
        // port of the WireMock proxy server.
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().verboseModeEnabled(false);
        if (nodeDistributionUrlRoot != null) {
            frontendMapBuilder
                .nodeVersion("14.16.1")
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
        if (yarnDistributionUrlRoot != null) {
            frontendMapBuilder
                .yarnEnabled(true)
                .yarnVersion("1.22.10")
                .yarnDistributionUrlRoot(yarnDistributionUrlRoot)
                .yarnDistributionUrlPathPattern(yarnDistributionUrlPathPattern);
            distributionServerConfigurator.withYarnDistribution();
            if (distributionServerUsername != null) {
                frontendMapBuilder
                    .yarnDistributionServerUsername(distributionServerUsername)
                    .yarnDistributionServerPassword(distributionServerPassword);
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
