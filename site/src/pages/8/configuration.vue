<template>
    <section>
        <FgpMainTitle>Configuration</FgpMainTitle>

        <FgpSubTitle>Plugin DSL</FgpSubTitle>

        <p>
            The <FgpCode>frontend</FgpCode> extension is the implementation of the plugin DSL, and holds all settings.
        </p>

        <FgpGradleScripts id="plugin-dsl">
            <template #groovy>
                <pre><FgpCode>frontend {
    <FgpPropertyLink name="nodeDistributionProvided" /> = false
    <FgpPropertyLink name="nodeVersion" /> = '20.18.0'
    <FgpPropertyLink name="nodeDistributionUrlRoot" /> = 'https://nodejs.org/dist/'
    <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> = 'vVERSION/node-vVERSION-ARCH.TYPE'
    <FgpPropertyLink name="nodeDistributionServerUsername" /> = 'username'
    <FgpPropertyLink name="nodeDistributionServerPassword" /> = 'password'
    <FgpPropertyLink name="nodeInstallDirectory" /> = project.layout.projectDirectory.dir("node")
    <FgpPropertyLink name="corepackVersion" /> = 'latest'

    <FgpPropertyLink name="installScript" /> = 'install'
    <FgpPropertyLink name="cleanScript" /> = 'run clean'
    <FgpPropertyLink name="assembleScript" /> = 'run assemble'
    <FgpPropertyLink name="checkScript" /> = 'run check'
    <FgpPropertyLink name="publishScript" /> = 'run publish'

    <FgpPropertyLink name="packageJsonDirectory" /> = projectDir
    <FgpPropertyLink name="httpsProxyHost" /> = '127.0.0.1'
    <FgpPropertyLink name="httpsProxyPort" /> = 443
    <FgpPropertyLink name="httpsProxyUsername" /> = 'username'
    <FgpPropertyLink name="httpsProxyPassword" /> = 'password'
    <FgpPropertyLink name="httpProxyHost" /> = '127.0.0.1'
    <FgpPropertyLink name="httpProxyPort" /> = 80
    <FgpPropertyLink name="httpProxyUsername" /> = 'username'
    <FgpPropertyLink name="httpProxyPassword" /> = 'password'
    <FgpPropertyLink name="maxDownloadAttempts" /> = 1
    <FgpPropertyLink name="retryHttpStatuses" /> = [408, 429, 500, 502, 503, 504]
    <FgpPropertyLink name="retryInitialIntervalMs" /> = 1000
    <FgpPropertyLink name="retryIntervalMultiplier" /> = 2.0
    <FgpPropertyLink name="retryMaxIntervalMs" /> = 30000
    <FgpPropertyLink name="verboseModeEnabled" /> = false
    <FgpPropertyLink name="cacheDirectory" /> = project.layout.projectDirectory.dir(".frontend-gradle-plugin")
}</FgpCode></pre>
            </template>
            <template #kotlin>
                <pre><FgpCode>frontend {
    <FgpPropertyLink name="nodeDistributionProvided" />.set(false)
    <FgpPropertyLink name="nodeVersion" />.set("20.18.0")
    <FgpPropertyLink name="nodeDistributionUrlRoot" />.set("https://nodejs.org/dist/")
    <FgpPropertyLink name="nodeDistributionUrlPathPattern" />.set("vVERSION/node-vVERSION-ARCH.TYPE")
    <FgpPropertyLink name="nodeDistributionServerUsername" />.set("username")
    <FgpPropertyLink name="nodeDistributionServerPassword" />.set("password")
    <FgpPropertyLink name="nodeInstallDirectory" />.set(project.layout.projectDirectory.dir("node"))
    <FgpPropertyLink name="corepackVersion" />.set("latest")

    <FgpPropertyLink name="installScript" />.set("install")
    <FgpPropertyLink name="cleanScript" />.set("run clean")
    <FgpPropertyLink name="assembleScript" />.set("run assemble")
    <FgpPropertyLink name="checkScript" />.set("run check")
    <FgpPropertyLink name="publishScript" />.set("run publish")

    <FgpPropertyLink name="packageJsonDirectory" />.set(project.layout.projectDirectory)
    <FgpPropertyLink name="httpsProxyHost" />.set("127.0.0.1")
    <FgpPropertyLink name="httpsProxyPort" />.set(443)
    <FgpPropertyLink name="httpsProxyUsername" />.set("username")
    <FgpPropertyLink name="httpsProxyPassword" />.set("password")
    <FgpPropertyLink name="httpProxyHost" />.set("127.0.0.1")
    <FgpPropertyLink name="httpProxyPort" />.set(80)
    <FgpPropertyLink name="httpProxyUsername" />.set("username")
    <FgpPropertyLink name="httpProxyPassword" />.set("password")
    <FgpPropertyLink name="maxDownloadAttempts" />.set(1)
    <FgpPropertyLink name="retryHttpStatuses" />.set(setOf(408, 429, 500, 502, 503, 504))
    <FgpPropertyLink name="retryInitialIntervalMs" />.set(1000)
    <FgpPropertyLink name="retryIntervalMultiplier" />.set(2.0)
    <FgpPropertyLink name="retryMaxIntervalMs" />.set(30000)
    <FgpPropertyLink name="verboseModeEnabled" />.set(false)
    <FgpPropertyLink name="cacheDirectory" />.set(project.layout.projectDirectory.dir(".frontend-gradle-plugin"))
}</FgpCode></pre>
            </template>
        </FgpGradleScripts>

        <FgpSubTitle>Properties</FgpSubTitle>

        <section>
            <FgpSubSubTitle>Node.js settings</FgpSubSubTitle>

            <FgpNodeDistributionProvidedProperty />
            <FgpNodeVersionProperty />
            <FgpNodeDistributionUrlRootProperty />
            <FgpNodeDistributionUrlPathPatternProperty />
            <FgpNodeDistributionServerUsernameProperty />
            <FgpNodeDistributionServerPasswordProperty />
            <FgpNodeInstallDirectoryProperty />
            <FgpCorepackVersionProperty />
        </section>

        <section>
            <FgpSubSubTitle id="script-settings">Script settings</FgpSubSubTitle>
            <p>
                The value for each property hereafter is provided as arguments of the package manager executable.
            </p>
            <p>
                Under Unix-like O/S, white space characters <FgpCode>' '</FgpCode> in an argument value must be escaped
                with a backslash character <FgpCode>'\'</FgpCode>. Under Windows O/S, the whole argument must be
                enclosed between double-quotes.
            </p>
            <ul>
                <li>
                    Example on Unix-like O/S:
                    <FgpCode>assembleScript = 'run assemble single\ argument'</FgpCode>
                </li>
                <li>
                    Example on Windows O/S:
                    <FgpCode>assembleScript = 'run assemble "single argument"'</FgpCode>
                </li>
            </ul>
            <p>
                Design of the plugin's tasks running a
                <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode> executable (e.g.
                <FgpTaskLink name="assembleFrontend" /> task) rely on the assumption the
                <FgpCode>package.json</FgpCode> file contains all script definitions, and is the single resource
                defining how to build the frontend application, execute unit tests, lint source code, run a development
                server, publish artifacts... We recommend to keep these definitions in this file, in the
                <FgpCode>scripts</FgpCode> section, and avoid as much as possible using the properties below to run
                complex commands. Keeping these scripts in one place should also ease finding out where they are
                located. In an ideal situation, the properties below shall all have a value such as
                <FgpCode>run &lt;script-name&gt;</FgpCode>, and nothing more. Example:
            </p>
            <FgpGradleScripts id="script-property-example">
                <template #groovy>
                    <pre><FgpCode><FgpCodeComment>// Instead of:</FgpCodeComment>
assembleScript = 'run webpack &#45;&#45; &#45;&#45;config webpack.config.js &#45;&#45;profile'

<FgpCodeComment>// Prefer:</FgpCodeComment>
assembleScript = 'run build'
<FgpCodeComment>// with a package.json file containing:
// "scripts": {
//   "build": "webpack &#45;&#45;config webpack/webpack.prod.js &#45;&#45;profile"
// }</FgpCodeComment></FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode><FgpCodeComment>// Instead of:</FgpCodeComment>
assembleScript.set("run webpack &#45;&#45; &#45;&#45;config webpack.config.js &#45;&#45;profile")

<FgpCodeComment>// Prefer:</FgpCodeComment>
assembleScript.set("run build")
<FgpCodeComment>// with a package.json file containing:
// "scripts": {
//   "build": "webpack &#45;&#45;config webpack/webpack.prod.js &#45;&#45;profile"
// }</FgpCodeComment></FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <FgpInstallScriptProperty />
            <FgpCleanScriptProperty />
            <FgpAssembleScriptProperty />
            <FgpCheckScriptProperty />
            <FgpPublishScriptProperty />
        </section>

        <section>
            <FgpSubSubTitle>Other settings</FgpSubSubTitle>

            <FgpPackageJsonDirectoryProperty />
            <FgpHttpsProxyHostProperty />
            <FgpHttpsProxyPortProperty />
            <FgpHttpsProxyUsernameProperty />
            <FgpHttpsProxyPasswordProperty />
            <FgpHttpProxyHostProperty />
            <FgpHttpProxyPortProperty />
            <FgpHttpProxyUsernameProperty />
            <FgpHttpProxyPasswordProperty />
            <FgpMaxDownloadAttemptsProperty />
            <FgpRetryHttpStatusesProperty />
            <FgpRetryInitialIntervalMsProperty />
            <FgpRetryIntervalMultiplierProperty />
            <FgpRetryMaxIntervalMsProperty />
            <FgpVerboseModeEnabledProperty />
            <FgpCacheDirectoryProperty />
        </section>

        <section>
            <FgpSubSubTitle id="proxy-resolution-process">
                About proxy resolution
                <FgpSiteLink :path="`${$config.public.paths.configuration}#app`" class="small text-info">&uparrow;</FgpSiteLink>
            </FgpSubSubTitle>

            <p>
                As a prerequisite, the distribution server's IP address or domain name must not match an entry specified
                in the VM
                <FgpJavaNetworkPropertiesLink><FgpCode>http.nonProxyHosts</FgpCode></FgpJavaNetworkPropertiesLink>
                network property, otherwise the plugin uses a direct connection. Then, the plugin relies on its own
                settings in priority, and finally on the VM
                <FgpJavaNetworkPropertiesLink><FgpCode>network properties</FgpCode></FgpJavaNetworkPropertiesLink
                >. The exact behaviour at runtime is introduced below:
            </p>
            <ul>
                <li>
                    The distribution download URL uses the
                    <FgpCode>https</FgpCode> protocol:
                    <ol>
                        <li>
                            If the <FgpPropertyLink name="httpsProxyHost" /> property is not <FgpCode>null</FgpCode>,
                            the plugin uses the IP address or domain name defined with this property and the port
                            defined with the <FgpPropertyLink name="httpsProxyPort" /> property.
                        </li>
                        <li>
                            If the VM
                            <FgpJavaNetworkPropertiesLink
                                ><FgpCode>https.proxyHost</FgpCode></FgpJavaNetworkPropertiesLink
                            >
                            network property is not <FgpCode>null</FgpCode>, the plugin uses the IP address or domain
                            name defined with this property and the port defined with the VM
                            <FgpJavaNetworkPropertiesLink
                                ><FgpCode>https.proxyPort</FgpCode></FgpJavaNetworkPropertiesLink
                            >
                            network property.
                        </li>
                    </ol>
                </li>
                <li>
                    The distribution download URL uses the
                    <FgpCode>http</FgpCode> protocol:
                    <ol>
                        <li>
                            If the <FgpPropertyLink name="httpProxyHost" /> property is not <FgpCode>null</FgpCode>, the
                            plugin uses the IP address or domain name defined with this property and the port defined
                            with the <FgpPropertyLink name="httpProxyPort" /> property.
                        </li>
                        <li>
                            If the VM
                            <FgpJavaNetworkPropertiesLink
                                ><FgpCode>http.proxyHost</FgpCode></FgpJavaNetworkPropertiesLink
                            >
                            network property is not <FgpCode>null</FgpCode>, the plugin uses the IP address or domain
                            name defined with this property and the port defined with the VM
                            <FgpJavaNetworkPropertiesLink
                                ><FgpCode>http.proxyPort</FgpCode></FgpJavaNetworkPropertiesLink
                            >
                            network property.
                        </li>
                    </ol>
                </li>
            </ul>
        </section>
    </section>
</template>

<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
const canonicalUrl = `${runtimeConfig.public.canonicalBaseUrl}${runtimeConfig.public.paths.configuration}`;
const title = 'Configuring Gradle to build a Javascript application with node';
const description = 'Choose pre-installed packages or request Node.js distributions download, plug scripts from a package.json file to build/test/publish frontend artifacts with Gradle.';

useHead({
    link: [{ rel: 'canonical', href: canonicalUrl }]
});
useSeoMeta({
    description,
    ogDescription: description,
    ogTitle: title,
    ogUrl: canonicalUrl,
    title
});
</script>
