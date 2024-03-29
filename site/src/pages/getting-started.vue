<template>
    <section>
        <fgp-main-title>Getting started</fgp-main-title>

        <fgp-sub-title>Requirements</fgp-sub-title>
        <p>
            The following tools must be installed to use the plugin:
        </p>
        <ul>
            <li><fgp-gradle-plugins-link path="/plugin/org.siouan.frontend-jdk17">JDK 17 build</fgp-gradle-plugins-link>: <fgp-gradle-link /> 7.3+</li>
            <li><fgp-gradle-plugins-link path="/plugin/org.siouan.frontend-jdk11">JDK 11 build</fgp-gradle-plugins-link>: <fgp-gradle-link /> 6.2+</li>
        </ul>
        <p>
            The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in
            the <fgp-repo-link path="/blob/main/CONTRIBUTING.md">contributing notes</fgp-repo-link>).
        </p>

        <fgp-sub-title>Supported distributions</fgp-sub-title>
        <ul>
            <li><fgp-nodejs-link />: ^14.19.0, &ge;16.9.0</li>
            <li><fgp-npm-link />: any</li>
            <li><fgp-pnpm-link />: ^5.0.0 (requires <fgp-nodejs-link /> 14), &ge;6.0.0</li>
            <li><fgp-yarn-link />: &ge;1.0.0</li>
        </ul>

        <fgp-sub-title>Steps</fgp-sub-title>
        <ol>
            <li>
                Install the plugin.
                <ul class="list-unstyled my-2 mr-3">
                    <li>
                        <p class="mb-2">
                            Using
                            <fgp-gradle-docs-link path="/current/userguide/plugins.html#sec:plugins_block">Gradle DSL</fgp-gradle-docs-link>
                            <span class="text-muted">(recommended)</span>
                        </p>

                        <fgp-gradle-scripts id="install-gradle-dsl">
                            <template #groovy>
<pre><fgp-code>plugins {
    <fgp-code-comment>// For JDK 17+</fgp-code-comment>
    id 'org.siouan.frontend-jdk17' version '8.0.0'
    <fgp-code-comment>// For JDK 11+</fgp-code-comment>
    id 'org.siouan.frontend-jdk11' version '8.0.0'
}</fgp-code></pre>
                            </template>
                            <template #kotlin>
<pre><fgp-code>plugins {
    <fgp-code-comment>// For JDK 17+</fgp-code-comment>
    id("org.siouan.frontend-jdk17") version "8.0.0"
    <fgp-code-comment>// For JDK 11+</fgp-code-comment>
    id("org.siouan.frontend-jdk11") version "8.0.0"
}</fgp-code></pre>
                            </template>
                        </fgp-gradle-scripts>
                    </li>
                    <li>
                        <p class="mb-2">
                            Using
                            <fgp-gradle-docs-link path="/current/userguide/plugins.html#sec:applying_plugins_buildscript">Gradle build script block</fgp-gradle-docs-link>
                        </p>

                        <fgp-gradle-scripts id="install-build-script-block">
                            <template #groovy>
<pre><fgp-code>buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        <fgp-code-comment>// For JDK 17+</fgp-code-comment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk17:8.0.0'
        <fgp-code-comment>// For JDK 11+</fgp-code-comment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:8.0.0'
    }
}

<fgp-code-comment>// For JDK 17+</fgp-code-comment>
apply plugin: 'org.siouan.frontend-jdk17'
<fgp-code-comment>// For JDK 11+</fgp-code-comment>
apply plugin: 'org.siouan.frontend-jdk11'</fgp-code></pre>
                            </template>
                            <template #kotlin>
<pre><fgp-code>buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        <fgp-code-comment>// For JDK 17+</fgp-code-comment>
        classpath("org.siouan:frontend-gradle-plugin-jdk17:8.0.0")
        <fgp-code-comment>// For JDK 11+</fgp-code-comment>
        classpath("org.siouan:frontend-gradle-plugin-jdk11:8.0.0")
    }
}

<fgp-code-comment>// For JDK 17+</fgp-code-comment>
apply(plugin = "org.siouan.frontend-jdk17")
<fgp-code-comment>// For JDK 11+</fgp-code-comment>
apply(plugin = "org.siouan.frontend-jdk11")</fgp-code></pre>
                            </template>
                        </fgp-gradle-scripts>
                    </li>
                </ul>
            </li>
            <li>
                Define the <fgp-code><fgp-nodejs-link path="/api/packages.html#packagemanager" label="packageManager"
                /></fgp-code>
                property in the <fgp-code>package.json</fgp-code> file.
            </li>
            <li>
                Add <fgp-code>.frontend-gradle-plugin/</fgp-code> directory to <fgp-code>.gitignore</fgp-code> file(s).
            </li>
            <li>
                <fgp-site-link :path="fgp.paths.configuration">Configure</fgp-site-link> your project, optionally
                with the help of <fgp-repo-link path="/tree/main/examples">examples</fgp-repo-link> provided for
                typical use cases.
            </li>
            <li>Run <fgp-code>gradlew build</fgp-code>.</li>
            <li>
                If you need to run
                <fgp-code>node</fgp-code>/<fgp-code>corepack</fgp-code>/<fgp-code>npm</fgp-code>/<fgp-code
                >pnpm</fgp-code>/<fgp-code>yarn</fgp-code> executables from a command line (e.g. to start a development
                server), take a look at the <fgp-site-link :path="fgp.paths.faqs">FAQ</fgp-site-link>.
            </li>
        </ol>
    </section>
</template>

<script>
import Vue from 'vue';
import fgpAppConfig from '@/mixin/app-config';
import fgpCode from '@/components/code';
import fgpCodeComment from '@/components/code-comment';
import fgpGradleDocsLink from '@/components/link/gradle-docs-link';
import fgpGradleLink from '@/components/link/gradle-link';
import fgpGradlePluginsLink from '@/components/link/gradle-plugins-link';
import fgpGradleScripts from '@/components/gradle-scripts';
import fgpMainTitle from '@/components/main-title';
import fgpNodejsLink from '@/components/link/nodejs-link';
import fgpNpmLink from '@/components/link/npm-link';
import fgpPnpmLink from '@/components/link/pnpm-link';
import fgpPageMeta from '@/mixin/page-meta';
import fgpRepoLink from '@/components/link/repo-link';
import fgpSiteLink from '@/components/link/site-link';
import fgpSubTitle from '@/components/sub-title';
import fgpYarnLink from '@/components/link/yarn-link';

export default Vue.component('fgp-getting-started', {
    components: {
        fgpCode,
        fgpCodeComment,
        fgpGradleDocsLink,
        fgpGradleLink,
        fgpGradlePluginsLink,
        fgpGradleScripts,
        fgpMainTitle,
        fgpNodejsLink,
        fgpNpmLink,
        fgpPnpmLink,
        fgpRepoLink,
        fgpSiteLink,
        fgpSubTitle,
        fgpYarnLink
    },
    mixins: [fgpAppConfig, fgpPageMeta],
    data() {
        return {
            htmlTitle: 'Getting started: building a Javascript application with the Gradle Node plugin',
            linkCanonicalHref: process.env.FGP_WEBSITE_URL + 'getting-started/',
            metaDescription: 'Guide to get started with the plugin: requirements, supported Node.js, NPM, PNPM, Yarn distributions, installation steps.'
        }
    }
});
</script>
