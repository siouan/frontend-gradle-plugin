<template>
    <section>
        <FgpMainTitle>Getting started</FgpMainTitle>

        <FgpSubTitle>Requirements</FgpSubTitle>
        <p>The following tools must be installed to use the plugin:</p>
        <ul>
            <li>
                <FgpGradlePluginsLink path="/plugin/org.siouan.frontend-jdk21">JDK 21 build</FgpGradlePluginsLink>:
                <FgpGradleLink /> 8.5+
            </li>
            <li>
                <FgpGradlePluginsLink path="/plugin/org.siouan.frontend-jdk17">JDK 17 build</FgpGradlePluginsLink>:
                <FgpGradleLink /> 8.5+
            </li>
            <li>
                <FgpGradlePluginsLink path="/plugin/org.siouan.frontend-jdk11">JDK 11 build</FgpGradlePluginsLink>:
                <FgpGradleLink /> 8.5+
            </li>
        </ul>
        <p>
            The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the
            <FgpRepoLink path="/blob/main/CONTRIBUTING.md">contributing notes</FgpRepoLink>).
        </p>

        <FgpSubTitle>Supported distributions</FgpSubTitle>
        <ul>
            <li><FgpNodejsLink />: ^14.19.0, &ge;16.9.0</li>
            <li><FgpNpmLink />: any</li>
            <li><FgpPnpmLink />: ^5.0.0 (requires <FgpNodejsLink /> 14), &ge;6.0.0</li>
            <li><FgpYarnLink />: &ge;1.0.0</li>
        </ul>

        <FgpSubTitle>Steps</FgpSubTitle>
        <ol>
            <li>
                Install the plugin.
                <ul class="list-unstyled my-2 me-3">
                    <li>
                        <p class="mb-2">
                            Using
                            <FgpGradleDocsLink path="/current/userguide/plugins.html#sec:plugins_block"
                            >Gradle DSL</FgpGradleDocsLink> <span class="text-muted">(recommended)</span>
                        </p>

                        <FgpGradleScripts id="install-gradle-dsl">
                            <template #groovy>
                                <pre><FgpCode>plugins {
    <FgpCodeComment>// For JDK 21+</FgpCodeComment>
    id 'org.siouan.frontend-jdk21' version '9.0.0'
    <FgpCodeComment>// For JDK 17+</FgpCodeComment>
    id 'org.siouan.frontend-jdk17' version '9.0.0'
    <FgpCodeComment>// For JDK 11+</FgpCodeComment>
    id 'org.siouan.frontend-jdk11' version '9.0.0'
}</FgpCode></pre>
                            </template>
                            <template #kotlin>
                                <pre><FgpCode>plugins {
    <FgpCodeComment>// For JDK 21+</FgpCodeComment>
    id("org.siouan.frontend-jdk21") version "9.0.0"
    <FgpCodeComment>// For JDK 17+</FgpCodeComment>
    id("org.siouan.frontend-jdk17") version "9.0.0"
    <FgpCodeComment>// For JDK 11+</FgpCodeComment>
    id("org.siouan.frontend-jdk11") version "9.0.0"
}</FgpCode></pre>
                            </template>
                        </FgpGradleScripts>
                    </li>
                    <li>
                        <p class="mb-2">
                            Using
                            <FgpGradleDocsLink path="/current/userguide/plugins.html#sec:applying_plugins_buildscript"
                                >Gradle build script block</FgpGradleDocsLink
                            >
                        </p>

                        <FgpGradleScripts id="install-build-script-block">
                            <template #groovy>
                                <pre><FgpCode>buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        <FgpCodeComment>// For JDK 21+</FgpCodeComment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk21:9.0.0'
        <FgpCodeComment>// For JDK 17+</FgpCodeComment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:9.0.0'
        <FgpCodeComment>// For JDK 11+</FgpCodeComment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:9.0.0'
    }
}

<FgpCodeComment>// For JDK 21+</FgpCodeComment>
apply plugin: 'org.siouan.frontend-jdk21'
<FgpCodeComment>// For JDK 17+</FgpCodeComment>
apply plugin: 'org.siouan.frontend-jdk17'
<FgpCodeComment>// For JDK 11+</FgpCodeComment>
apply plugin: 'org.siouan.frontend-jdk11'</FgpCode></pre>
                            </template>
                            <template #kotlin>
                                <pre><FgpCode>buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        <FgpCodeComment>// For JDK 21+</FgpCodeComment>
        classpath("org.siouan:frontend-gradle-plugin-jdk21:9.0.0")
        <FgpCodeComment>// For JDK 17+</FgpCodeComment>
        classpath("org.siouan:frontend-gradle-plugin-jdk17:9.0.0")
        <FgpCodeComment>// For JDK 11+</FgpCodeComment>
        classpath("org.siouan:frontend-gradle-plugin-jdk11:9.0.0")
    }
}

<FgpCodeComment>// For JDK 21+</FgpCodeComment>
apply(plugin = "org.siouan.frontend-jdk21")
<FgpCodeComment>// For JDK 17+</FgpCodeComment>
apply(plugin = "org.siouan.frontend-jdk17")
<FgpCodeComment>// For JDK 11+</FgpCodeComment>
apply(plugin = "org.siouan.frontend-jdk11")</FgpCode></pre>
                            </template>
                        </FgpGradleScripts>
                    </li>
                </ul>
            </li>
            <li>
                Define the
                <FgpCode><FgpNodejsLink path="/api/packages.html#packagemanager" label="packageManager" /></FgpCode>
                property in the <FgpCode>package.json</FgpCode> file.
            </li>
            <li>Add <FgpCode>.frontend-gradle-plugin/</FgpCode> directory to <FgpCode>.gitignore</FgpCode> file(s).</li>
            <li>
                <FgpSiteLink :path="$config.public.paths.configuration">Configure</FgpSiteLink>
                the build, test (check), deploy (publish) phases in your project, with the help of the
                <FgpSiteLink :path="`${$config.public.paths.tasks}#dependency-tree`"
                >built-in tasks dependency tree</FgpSiteLink> and optionally
                <FgpRepoLink path="/tree/main/examples">examples</FgpRepoLink> provided for typical use cases.
            </li>
            <li>Run <FgpCode>gradlew build</FgpCode>.</li>
            <li>Additionally:
                <ul>
                <li>
                    If you need to execute an instant command with a node-based executable (e.g. to install a
                    dependency), invoke one of the following tasks on a Gradle command line:
                    <FgpTaskLink name="runNode" />, <FgpTaskLink name="runCorepack" />, <FgpTaskLink name="runNpm" />,
                    <FgpTaskLink name="runPnpm" />, <FgpTaskLink name="runYarn" />.
                </li>
                <li>
                    If you need to define a reusable node-based command with eventual custom inputs, outputs,
                    or environment, use one of the following task types: <FgpTaskLink name="RunNodeTaskType" />,
                    <FgpTaskLink name="RunCorepackTaskType" />, <FgpTaskLink name="RunNpmTaskType" />,
                    <FgpTaskLink name="RunPnpmTaskType" />, <FgpTaskLink name="RunYarnTaskType" />.
                </li>
                <li>
                    If you need to run
                    <FgpCode>node</FgpCode
                    >/<FgpCode>corepack</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode>
                    executables from a command line (e.g. to start a development server), take a look at the
                    <FgpSiteLink :path="$config.public.paths.faqs">FAQ</FgpSiteLink>.
                </li>
                </ul>
            </li>
        </ol>
    </section>
</template>

<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
const canonicalUrl = `${runtimeConfig.public.canonicalBaseUrl}${runtimeConfig.public.paths.gettingStarted}`;
const title = 'Building a Javascript application with the Gradle Node plugin';
const description = 'Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.';

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
