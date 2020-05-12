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
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.test.util.FrontendMapBuilder;
import org.siouan.frontendgradleplugin.test.util.ProxyServerConfigurator;

/**
 * Functional tests to verify authentication and integration of a proxy server with the {@link NodeInstallTask} task and
 * the {@link YarnInstallTask} task. Test cases uses a mock server, acting either as the distribution server, or as the
 * proxy server, and fake Node.js/Yarn distributions, to avoid the download overhead.
 */
class AuthenticationAndProxyFuncTest {

    private static final String NODE_DISTRIBUTION_URL_PATTERN = "http://nodejs.org/dist/vVERSION/node-vVERSION.zip";

    private static final String YARN_DISTRIBUTION_URL_PATTERN = "http://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz";

    private static final String SERVER_HOST = "127.0.0.1";

    private static final int SERVER_PORT = 59338;

    private static final String DISTRIBUTION_SERVER_USERNAME = "dist-username";

    private static final String DISTRIBUTION_SERVER_PASSWORD = "dist-password";

    private static final String PROXY_SERVER_USERNAME = "proxy-username";

    private static final String PROXY_SERVER_PASSWORD = "proxy-password";

    private static WireMockServer server;

    @TempDir
    Path projectDirectoryPath;

    @BeforeAll
    static void beforeAll() {
        server = new WireMockServer(wireMockConfig().port(SERVER_PORT));
        server.start();
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    @BeforeEach
    void setUp() {
        server.resetAll();
    }

    @Test
    void shouldFailInstallingNodeWhenAuthenticationFailsOnDistributionServer() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithDirectConnection(
            new URL("http", SERVER_HOST, SERVER_PORT, "/dist/vVERSION/node-vVERSION.zip").toString(), null, "AYr2n{VF");
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithDirectConnection(
            new URL("http", SERVER_HOST, SERVER_PORT, "/dist/vVERSION/node-vVERSION.zip").toString(), null,
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(
            NODE_DISTRIBUTION_URL_PATTERN, null, SERVER_PORT + 1);
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(
            NODE_DISTRIBUTION_URL_PATTERN, null, SERVER_PORT);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingNodeWhenAuthenticationFailsOnProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(
            NODE_DISTRIBUTION_URL_PATTERN, null, "xE!s67O?");
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(
            NODE_DISTRIBUTION_URL_PATTERN, null, PROXY_SERVER_PASSWORD);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenAuthenticationFailsOnDistributionServer() throws IOException {
        // Direct connection to the distribution server, i.e. the 'server' variable acts as the distribution server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithDirectConnection(null,
            new URL("http", SERVER_HOST, SERVER_PORT,
                "/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz").toString(), "AYr2n{VF");
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithDirectConnection(null,
            new URL("http", SERVER_HOST, SERVER_PORT,
                "/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz").toString(),
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(null,
            YARN_DISTRIBUTION_URL_PATTERN, SERVER_PORT + 1);
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(null,
            YARN_DISTRIBUTION_URL_PATTERN, SERVER_PORT);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Test
    void shouldFailInstallingYarnWhenAuthenticationFailsOnProxyServer() throws IOException {
        // Connection through a proxy server, i.e. the 'server' variable acts as the proxy server.

        // We use a HTTP address because proxying through HTTPS is not supported with WireMock.
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(null,
            YARN_DISTRIBUTION_URL_PATTERN, "xE!s67O?");
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
        final FrontendMapBuilder frontendMapBuilder = configureServerAndPluginWithProxyConnection(null,
            YARN_DISTRIBUTION_URL_PATTERN, PROXY_SERVER_PASSWORD);
        createBuildFile(projectDirectoryPath, frontendMapBuilder.toMap());

        final BuildResult result1 = runGradle(projectDirectoryPath, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);

        assertTaskSuccess(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
    }

    @Nonnull
    private FrontendMapBuilder configureServerAndPluginWithDirectConnection(
        @Nullable final String nodeDistributionUrlPattern, @Nullable final String yarnDistributionUrlPattern,
        @Nonnull final String distributionServerPassword) throws IOException {
        return configureServerAndPlugin(nodeDistributionUrlPattern, yarnDistributionUrlPattern,
            DISTRIBUTION_SERVER_USERNAME, distributionServerPassword, null, null, null, null);
    }

    @Nonnull
    private FrontendMapBuilder configureServerAndPluginWithProxyConnection(
        @Nullable final String nodeDistributionUrlPattern, @Nullable final String yarnDistributionUrlPattern,
        final int proxyServerPort) throws IOException {
        return configureServerAndPlugin(nodeDistributionUrlPattern, yarnDistributionUrlPattern, null, null, SERVER_HOST,
            proxyServerPort, null, null);
    }

    @Nonnull
    private FrontendMapBuilder configureServerAndPluginWithProxyConnection(
        @Nullable final String nodeDistributionUrlPattern, @Nullable final String yarnDistributionUrlPattern,
        @Nonnull final String proxyServerPassword) throws IOException {
        return configureServerAndPlugin(nodeDistributionUrlPattern, yarnDistributionUrlPattern, null, null, SERVER_HOST,
            SERVER_PORT, PROXY_SERVER_USERNAME, proxyServerPassword);
    }

    @Nonnull
    private FrontendMapBuilder configureServerAndPlugin(@Nullable final String nodeDistributionUrlPattern,
        @Nullable final String yarnDistributionUrlPattern, @Nullable final String distributionServerUsername,
        @Nullable final String distributionServerPassword, @Nullable final String proxyServerHost,
        @Nullable final Integer proxyServerPort, @Nullable final String proxyServerUsername,
        @Nullable final String proxyServerPassword) throws IOException {
        final ProxyServerConfigurator proxyServerConfigurator = ProxyServerConfigurator.getInstance(server);

        // Try to download the distribution through a proxy server, but the port used is not the same port than the
        // port of the WireMock proxy server.
        final FrontendMapBuilder frontendMapBuilder = new FrontendMapBuilder().verboseModeEnabled(true);
        if (nodeDistributionUrlPattern != null) {
            frontendMapBuilder.nodeVersion("12.16.3").nodeDistributionUrlPattern(nodeDistributionUrlPattern);
            proxyServerConfigurator.withNodeDistribution();
            if (distributionServerUsername != null) {
                frontendMapBuilder
                    .nodeDistributionServerUsername(distributionServerUsername)
                    .nodeDistributionServerPassword(distributionServerPassword);
                proxyServerConfigurator.withDistributionServerAuth(DISTRIBUTION_SERVER_USERNAME,
                    DISTRIBUTION_SERVER_PASSWORD);
            }
        }
        if (yarnDistributionUrlPattern != null) {
            frontendMapBuilder
                .yarnEnabled(true)
                .yarnVersion("1.22.4")
                .yarnDistributionUrlPattern(yarnDistributionUrlPattern);
            proxyServerConfigurator.withYarnDistribution();
            if (distributionServerUsername != null) {
                frontendMapBuilder
                    .yarnDistributionServerUsername(distributionServerUsername)
                    .yarnDistributionServerPassword(distributionServerPassword);
                proxyServerConfigurator.withDistributionServerAuth(DISTRIBUTION_SERVER_USERNAME,
                    DISTRIBUTION_SERVER_PASSWORD);
            }
        }
        if (proxyServerHost != null) {
            frontendMapBuilder.proxyHost(proxyServerHost).proxyPort(proxyServerPort);
            if (proxyServerUsername != null) {
                frontendMapBuilder.proxyUsername(proxyServerUsername).proxyPassword(proxyServerPassword);
                proxyServerConfigurator.withProxyServerAuth(PROXY_SERVER_USERNAME, PROXY_SERVER_PASSWORD);
            }
        }

        proxyServerConfigurator.configure();
        // Manual verification: when proxy host below is not defined, the distribution shall be downloaded without
        // the proxy server, and the build shall succeed.
        return frontendMapBuilder;
    }
}
