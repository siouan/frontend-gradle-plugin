<template>
    <section>
        <fgp-main-title>Configuration</fgp-main-title>

        <fgp-sub-title>Plugin DSL</fgp-sub-title>

        <p>
            The <fgp-code>frontend</fgp-code> extension is the implementation of the plugin DSL, and holds all settings.
        </p>

        <fgp-gradle-scripts id="plugin-dsl">
            <template #groovy>
                <pre><fgp-code>frontend {
    <fgp-property-link name="nodeDistributionProvided" /> = false
    <fgp-property-link name="nodeVersion" /> = '18.16.0'
    <fgp-property-link name="nodeDistributionUrlRoot" /> = 'https://nodejs.org/dist/'
    <fgp-property-link name="nodeDistributionUrlPathPattern" /> = 'vVERSION/node-vVERSION-ARCH.TYPE'
    <fgp-property-link name="nodeDistributionServerUsername" /> = 'username'
    <fgp-property-link name="nodeDistributionServerPassword" /> = 'password'
    <fgp-property-link name="nodeInstallDirectory" /> = file("${projectDir}/node")

    <fgp-code-comment>// REMOVED PROPERTIES - PRIOR TO RELEASE 7.0.0</fgp-code-comment>
    <fgp-property-link name="yarnEnabled" /> = false
    <fgp-property-link name="yarnVersion" /> = '3.5.1'
    <fgp-code-comment>// END OF REMOVED PROPERTIES</fgp-code-comment>
    <fgp-code-comment>// REMOVED PROPERTIES - PRIOR TO RELEASE 6.0.0</fgp-code-comment>
    <fgp-property-link name="yarnDistributionProvided" /> = false
    <fgp-property-link name="yarnDistributionUrlRoot" /> = 'https://github.com/yarnpkg/yarn/releases/download/'
    <fgp-property-link name="yarnDistributionUrlPathPattern" /> = 'vVERSION/yarn-vVERSION.tar.gz'
    <fgp-property-link name="yarnDistributionServerUsername" /> = 'username'
    <fgp-property-link name="yarnDistributionServerPassword" /> = 'password'
    <fgp-property-link name="yarnInstallDirectory" /> = file("${projectDir}/yarn")
    <fgp-code-comment>// END OF REMOVED PROPERTIES</fgp-code-comment>

    <fgp-property-link name="installScript" /> = 'install'
    <fgp-property-link name="cleanScript" /> = 'run clean'
    <fgp-property-link name="assembleScript" /> = 'run assemble'
    <fgp-property-link name="checkScript" /> = 'run check'
    <fgp-property-link name="publishScript" /> = 'run publish'

    <fgp-property-link name="packageJsonDirectory" /> = projectDir
    <fgp-property-link name="httpsProxyHost" /> = '127.0.0.1'
    <fgp-property-link name="httpsProxyPort" /> = 443
    <fgp-property-link name="httpsProxyUsername" /> = 'username'
    <fgp-property-link name="httpsProxyPassword" /> = 'password'
    <fgp-property-link name="httpProxyHost" /> = '127.0.0.1'
    <fgp-property-link name="httpProxyPort" /> = 80
    <fgp-property-link name="httpProxyUsername" /> = 'username'
    <fgp-property-link name="httpProxyPassword" /> = 'password'
    <fgp-property-link name="maxDownloadAttempts" /> = 1
    <fgp-property-link name="retryHttpStatuses" /> = [408, 429, 500, 502, 503, 504]
    <fgp-property-link name="retryInitialIntervalMs" /> = 1000
    <fgp-property-link name="retryIntervalMultiplier" /> = 2.0
    <fgp-property-link name="retryMaxIntervalMs" /> = 30000
    <fgp-property-link name="verboseModeEnabled" /> = false
    <fgp-property-link name="cacheDirectory" /> = file("${projectDir}/.frontend-gradle-plugin")
}</fgp-code></pre>
            </template>
            <template #kotlin>
                <pre><fgp-code>frontend {
    <fgp-property-link name="nodeDistributionProvided" />.set(false)
    <fgp-property-link name="nodeVersion" />.set("18.16.0")
    <fgp-property-link name="nodeDistributionUrlRoot" />.set("https://nodejs.org/dist/")
    <fgp-property-link name="nodeDistributionUrlPathPattern" />.set("vVERSION/node-vVERSION-ARCH.TYPE")
    <fgp-property-link name="nodeDistributionServerUsername" />.set("username")
    <fgp-property-link name="nodeDistributionServerPassword" />.set("password")
    <fgp-property-link name="nodeInstallDirectory" />.set(project.layout.projectDirectory.dir("node"))

    <fgp-code-comment>// REMOVED PROPERTIES - PRIOR TO RELEASE 7.0.0</fgp-code-comment>
    <fgp-property-link name="yarnEnabled" />.set(false)
    <fgp-property-link name="yarnVersion" />.set("3.5.1")
    <fgp-code-comment>// END OF REMOVED PROPERTIES</fgp-code-comment>
    <fgp-code-comment>// REMOVED PROPERTIES - PRIOR TO RELEASE 6.0.0</fgp-code-comment>
    <fgp-property-link name="yarnDistributionProvided" />.set(false)
    <fgp-property-link name="yarnDistributionUrlRoot" />.set("https://github.com/yarnpkg/yarn/releases/download/")
    <fgp-property-link name="yarnDistributionUrlPathPattern" />.set("vVERSION/yarn-vVERSION.tar.gz")
    <fgp-property-link name="yarnDistributionServerUsername" />.set("username")
    <fgp-property-link name="yarnDistributionServerPassword" />.set("password")
    <fgp-property-link name="yarnInstallDirectory" />.set(project.layout.projectDirectory.dir("yarn"))
    <fgp-code-comment>// END OF REMOVED PROPERTIES</fgp-code-comment>

    <fgp-property-link name="installScript" />.set("install")
    <fgp-property-link name="cleanScript" />.set("run clean")
    <fgp-property-link name="assembleScript" />.set("run assemble")
    <fgp-property-link name="checkScript" />.set("run check")
    <fgp-property-link name="publishScript" />.set("run publish")

    <fgp-property-link name="packageJsonDirectory" />.set(project.layout.projectDirectory)
    <fgp-property-link name="httpsProxyHost" />.set("127.0.0.1")
    <fgp-property-link name="httpsProxyPort" />.set(443)
    <fgp-property-link name="httpsProxyUsername" />.set("username")
    <fgp-property-link name="httpsProxyPassword" />.set("password")
    <fgp-property-link name="httpProxyHost" />.set("127.0.0.1")
    <fgp-property-link name="httpProxyPort" />.set(80)
    <fgp-property-link name="httpProxyUsername" />.set("username")
    <fgp-property-link name="httpProxyPassword" />.set("password")
    <fgp-property-link name="maxDownloadAttempts" />.set(1)
    <fgp-property-link name="retryHttpStatuses" />.set(setOf(408, 429, 500, 502, 503, 504))
    <fgp-property-link name="retryInitialIntervalMs" />.set(1000)
    <fgp-property-link name="retryIntervalMultiplier" />.set(2.0)
    <fgp-property-link name="retryMaxIntervalMs" />.set(30000)
    <fgp-property-link name="verboseModeEnabled" />.set(false)
    <fgp-property-link name="cacheDirectory" />.set(project.layout.projectDirectory.dir(".frontend-gradle-plugin"))
}</fgp-code></pre>
            </template>
        </fgp-gradle-scripts>

        <fgp-sub-title>Properties</fgp-sub-title>

        <section>
            <fgp-sub-sub-title>Node.js settings</fgp-sub-sub-title>

            <fgp-node-distribution-provided-property />
            <fgp-node-version-property />
            <fgp-node-distribution-url-root-property />
            <fgp-node-distribution-url-path-pattern-property />
            <fgp-node-distribution-server-username-property />
            <fgp-node-distribution-server-password-property />
            <fgp-node-install-directory-property />
        </section>

        <section>
            <fgp-sub-sub-title>Yarn settings</fgp-sub-sub-title>

            <fgp-yarn-enabled-property />
            <fgp-yarn-distribution-provided-property />
            <fgp-yarn-version-property />
            <fgp-yarn-distribution-url-root-property />
            <fgp-yarn-distribution-url-path-pattern-property />
            <fgp-yarn-distribution-server-username-property />
            <fgp-yarn-distribution-server-password-property />
            <fgp-yarn-install-directory-property />
        </section>

        <section>
            <fgp-sub-sub-title id="script-settings">Script settings</fgp-sub-sub-title>
            <p>
                Starting from release 7.0.0, the value for each property hereafter is provided as arguments of the
                package manager executable.
            </p>
            <p>
                Before release 7.0.0, Depending on the value of the <fgp-property-link name="yarnEnabled" /> property,
                the value for each property hereafter is provided as argument either of the <fgp-code>npm</fgp-code>
                executable or the <fgp-code>yarn</fgp-code> executable.
            </p>
            <p>
                Under Unix-like O/S, white space characters <fgp-code>' '</fgp-code> in an argument value must be
                escaped with a backslash character <fgp-code>'\'</fgp-code>. Under Windows O/S, the whole argument must
                be enclosed between double-quotes.
            </p>
            <ul>
                <li>Example on Unix-like O/S: <fgp-code>assembleScript = 'run assemble single\ argument'</fgp-code></li>
                <li>Example on Windows O/S: <fgp-code>assembleScript = 'run assemble "single argument"'</fgp-code></li>
            </ul>
            <p>
                Design of the plugin's tasks running a
                <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> executable
                (e.g. <fgp-task-link name="assembleFrontend" /> task) rely on the assumption the
                <fgp-code>package.json</fgp-code> file contains all script definitions, and is the single resource
                defining how to build the frontend application, execute unit tests, lint source code, run a development
                server, publish artifacts... We recommend to keep these definitions in this file, in the
                <fgp-code>scripts</fgp-code> section, and avoid as much as possible using the properties below to run
                complex commands. Keeping these scripts in one place should also ease finding out where they are
                located. In an ideal situation, the properties below shall all have a value such as
                <fgp-code>run &lt;script-name&gt;</fgp-code>, and nothing more. Example:
            </p>
            <fgp-gradle-scripts id="script-property-example" class="mt-3">
                <template #groovy>
                    <pre><fgp-code><fgp-code-comment>// Instead of:</fgp-code-comment>
assembleScript = 'run webpack &#45;&#45; &#45;&#45;config webpack.config.js &#45;&#45;profile'

<fgp-code-comment>// Prefer:</fgp-code-comment>
assembleScript = 'run build'
<fgp-code-comment>// with a package.json file containing:
// "scripts": {
//   "build": "webpack &#45;&#45;config webpack/webpack.prod.js &#45;&#45;profile"
// }</fgp-code-comment></fgp-code></pre>
                </template>
                <template #kotlin>
                    <pre><fgp-code><fgp-code-comment>// Instead of:</fgp-code-comment>
assembleScript.set("run webpack &#45;&#45; &#45;&#45;config webpack.config.js &#45;&#45;profile")

<fgp-code-comment>// Prefer:</fgp-code-comment>
assembleScript.set("run build")
<fgp-code-comment>// with a package.json file containing:
// "scripts": {
//   "build": "webpack &#45;&#45;config webpack/webpack.prod.js &#45;&#45;profile"
// }</fgp-code-comment></fgp-code></pre>
                </template>
            </fgp-gradle-scripts>

            <fgp-install-script-property />
            <fgp-clean-script-property />
            <fgp-assemble-script-property />
            <fgp-check-script-property />
            <fgp-publish-script-property />
        </section>

        <section>
            <fgp-sub-sub-title>Other settings</fgp-sub-sub-title>

            <fgp-package-json-directory-property />
            <fgp-https-proxy-host-property />
            <fgp-https-proxy-port-property />
            <fgp-https-proxy-username-property />
            <fgp-https-proxy-password-property />
            <fgp-http-proxy-host-property />
            <fgp-http-proxy-port-property />
            <fgp-http-proxy-username-property />
            <fgp-http-proxy-password-property />
            <fgp-max-download-attempts-property />
            <fgp-retry-http-statuses-property />
            <fgp-retry-initial-interval-ms-property />
            <fgp-retry-interval-multiplier-property />
            <fgp-retry-max-interval-ms-property />
            <fgp-verbose-mode-enabled-property />
            <fgp-cache-directory-property />
        </section>

        <section>
            <fgp-sub-sub-title id="proxy-resolution-process">
                About proxy resolution
                <fgp-site-link path="#app" class="small text-info">&uparrow;</fgp-site-link>
            </fgp-sub-sub-title>

            <p>
                As a prerequisite, the distribution server's IP address or domain name must not match an entry specified
                in the VM
                <fgp-java-network-properties-link><fgp-code>http.nonProxyHosts</fgp-code></fgp-java-network-properties-link>
                network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own
                settings in priority, and finally on the VM
                <fgp-java-network-properties-link><fgp-code>network properties</fgp-code></fgp-java-network-properties-link>.
                The exact behaviour at runtime is introduced below:
            </p>
            <ul>
                <li>
                    The distribution download URL uses the <fgp-code>https</fgp-code> protocol:
                    <ol>
                        <li>
                            If the <fgp-property-link name="httpsProxyHost" /> property is not
                            <fgp-code>null</fgp-code>, the plugin uses the IP address or domain name defined with this
                            property and the port defined with the <fgp-property-link name="httpsProxyPort" /> property.
                        </li>
                        <li>
                            If the VM
                            <fgp-java-network-properties-link><fgp-code>https.proxyHost</fgp-code></fgp-java-network-properties-link>
                            network property is not <fgp-code>null</fgp-code>, the plugin uses the IP address or domain
                            name defined with this property and the port defined with the VM
                            <fgp-java-network-properties-link><fgp-code>https.proxyPort</fgp-code></fgp-java-network-properties-link>
                            network property.
                        </li>
                    </ol>
                </li>
                <li>
                    The distribution download URL uses the <fgp-code>http</fgp-code> protocol:
                    <ol>
                        <li>
                            If the <fgp-property-link name="httpProxyHost" /> property is not <fgp-code>null</fgp-code>,
                            the plugin uses the IP address or domain name defined with this property and the port
                            defined with the <fgp-property-link name="httpProxyPort" /> property.
                        </li>
                        <li>
                            If the VM
                            <fgp-java-network-properties-link><fgp-code>http.proxyHost</fgp-code></fgp-java-network-properties-link>
                            network property is not <fgp-code>null</fgp-code>, the plugin uses the IP address or domain
                            name defined with this property and the port defined with the VM
                            <fgp-java-network-properties-link><fgp-code>http.proxyPort</fgp-code></fgp-java-network-properties-link>
                            network property.
                        </li>
                    </ol>
                </li>
            </ul>
        </section>
    </section>
</template>

<script>
import Vue from 'vue';
import fgpAssembleScriptProperty from '@/components/property/assemble-script-property';
import fgpCacheDirectoryProperty from '@/components/property/cache-directory-property';
import fgpCheckScriptProperty from '@/components/property/check-script-property';
import fgpCleanScriptProperty from '@/components/property/clean-script-property';
import fgpCode from '@/components/code';
import fgpCodeComment from '@/components/code-comment';
import fgpGradleScripts from '@/components/gradle-scripts';
import fgpHttpProxyHostProperty from '@/components/property/http-proxy-host-property';
import fgpHttpProxyPasswordProperty from '@/components/property/http-proxy-password-property';
import fgpHttpProxyPortProperty from '@/components/property/http-proxy-port-property';
import fgpHttpProxyUsernameProperty from '@/components/property/http-proxy-username-property';
import fgpHttpsProxyHostProperty from '@/components/property/https-proxy-host-property';
import fgpHttpsProxyPasswordProperty from '@/components/property/https-proxy-password-property';
import fgpHttpsProxyPortProperty from '@/components/property/https-proxy-port-property';
import fgpHttpsProxyUsernameProperty from '@/components/property/https-proxy-username-property';
import fgpInstallScriptProperty from '@/components/property/install-script-property';
import fgpJavaNetworkPropertiesLink from '@/components/link/java-network-properties-link';
import fgpMainTitle from '@/components/main-title';
import fgpMaxDownloadAttemptsProperty from '@/components/property/max-download-attempts-property';
import fgpNodeDistributionProvidedProperty from '@/components/property/node-distribution-provided-property';
import fgpNodeVersionProperty from '@/components/property/node-version-property';
import fgpNodeDistributionUrlRootProperty from '@/components/property/node-distribution-url-root-property';
import fgpNodeDistributionUrlPathPatternProperty from '@/components/property/node-distribution-url-path-pattern-property';
import fgpNodeDistributionServerUsernameProperty from '@/components/property/node-distribution-server-username-property';
import fgpNodeDistributionServerPasswordProperty from '@/components/property/node-distribution-server-password-property';
import fgpNodeInstallDirectoryProperty from '@/components/property/node-install-directory-property';
import fgpPageMeta from '@/mixin/page-meta';
import fgpPackageJsonDirectoryProperty from '@/components/property/package-json-directory-property';
import fgpPropertyLink from '@/components/link/property-link';
import fgpPublishScriptProperty from '@/components/property/publish-script-property';
import fgpRetryHttpStatusesProperty from '@/components/property/retry-http-statuses-property';
import fgpRetryInitialIntervalMsProperty from '@/components/property/retry-initial-interval-ms-property';
import fgpRetryIntervalMultiplierProperty from '@/components/property/retry-interval-multiplier-property';
import fgpRetryMaxIntervalMsProperty from '@/components/property/retry-max-interval-ms-property';
import fgpSubSubTitle from '@/components/sub-sub-title';
import fgpSubTitle from '@/components/sub-title';
import fgpVerboseModeEnabledProperty from '@/components/property/verbose-mode-enabled-property';
import fgpYarnEnabledProperty from '@/components/property/yarn-enabled-property';
import fgpYarnDistributionProvidedProperty from '@/components/property/yarn-distribution-provided-property';
import fgpYarnVersionProperty from '@/components/property/yarn-version-property';
import fgpYarnDistributionUrlRootProperty from '@/components/property/yarn-distribution-url-root-property';
import fgpYarnDistributionUrlPathPatternProperty from '@/components/property/yarn-distribution-url-path-pattern-property';
import fgpYarnDistributionServerUsernameProperty from '@/components/property/yarn-distribution-server-username-property';
import fgpYarnDistributionServerPasswordProperty from '@/components/property/yarn-distribution-server-password-property';
import fgpYarnInstallDirectoryProperty from '@/components/property/yarn-install-directory-property';

export default Vue.component('fgp-configuration', {
    components: {
        fgpAssembleScriptProperty,
        fgpCacheDirectoryProperty,
        fgpCheckScriptProperty,
        fgpCleanScriptProperty,
        fgpCode,
        fgpCodeComment,
        fgpGradleScripts,
        fgpInstallScriptProperty,
        fgpJavaNetworkPropertiesLink,
        fgpMainTitle,
        fgpMaxDownloadAttemptsProperty,
        fgpNodeDistributionProvidedProperty,
        fgpNodeVersionProperty,
        fgpNodeDistributionUrlRootProperty,
        fgpNodeDistributionUrlPathPatternProperty,
        fgpNodeDistributionServerUsernameProperty,
        fgpNodeDistributionServerPasswordProperty,
        fgpNodeInstallDirectoryProperty,
        fgpPackageJsonDirectoryProperty,
        fgpPropertyLink,
        fgpHttpProxyHostProperty,
        fgpHttpProxyPasswordProperty,
        fgpHttpProxyPortProperty,
        fgpHttpProxyUsernameProperty,
        fgpHttpsProxyHostProperty,
        fgpHttpsProxyPasswordProperty,
        fgpHttpsProxyPortProperty,
        fgpHttpsProxyUsernameProperty,
        fgpPublishScriptProperty,
        fgpRetryHttpStatusesProperty,
        fgpRetryInitialIntervalMsProperty,
        fgpRetryIntervalMultiplierProperty,
        fgpRetryMaxIntervalMsProperty,
        fgpSubSubTitle,
        fgpSubTitle,
        fgpVerboseModeEnabledProperty,
        fgpYarnEnabledProperty,
        fgpYarnDistributionProvidedProperty,
        fgpYarnVersionProperty,
        fgpYarnDistributionUrlRootProperty,
        fgpYarnDistributionUrlPathPatternProperty,
        fgpYarnDistributionServerUsernameProperty,
        fgpYarnDistributionServerPasswordProperty,
        fgpYarnInstallDirectoryProperty
    },
    mixins: [fgpPageMeta],
    data() {
        return {
            htmlTitle: 'Configuring Gradle to build a Javascript application with node',
            metaDescription: 'Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.',
            linkCanonicalHref: process.env.FGP_WEBSITE_URL + 'configuration/'
        }
    }
});
</script>
