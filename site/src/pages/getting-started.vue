<template>
    <section>
        <fgp-main-title>Getting started</fgp-main-title>

        <fgp-sub-title>Requirements</fgp-sub-title>
        <ul>
            <li><fgp-gradle-link /> 5.1+</li>
            <li>JDK 8+ 64 bits</li>
            <li>
                The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in
                the <fgp-repo-link path="/blob/master/CONTRIBUTING.md">contributing notes</fgp-repo-link>).
            </li>
        </ul>

        <fgp-sub-title>Supported distributions</fgp-sub-title>
        <ul>
            <li><fgp-nodejs-link /> 6.2.1+</li>
            <li><fgp-yarn-link /> 1.0+ (<fgp-yarn-link :version="2" /> initial support planned in release 6.0.0)</li>
        </ul>

        <fgp-sub-title>Steps</fgp-sub-title>
        <fgp-info>
            Starting from release <fgp-repo-link path="/releases/tag/v3.0.1">3.0.1</fgp-repo-link>, ID
            <fgp-code>org.siouan.frontend</fgp-code> and classpath
            <fgp-code>org.siouan:frontend-gradle-plugin:&lt;version></fgp-code> are deprecated. If you are already using
            the plugin, we recommend <fgp-repo-link path="/releases/tag/v5.0.0">upgrading</fgp-repo-link> to the latest
            release as soon as possible.
        </fgp-info>
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
                            <template v-slot:groovy>
<pre><fgp-code>plugins {
    <fgp-code-comment>// For JDK 11+</fgp-code-comment>
    id 'org.siouan.frontend-jdk11' version '5.0.0'
    <fgp-code-comment>// For JDK 8+</fgp-code-comment>
    id 'org.siouan.frontend-jdk8' version '5.0.0'
}</fgp-code></pre>
                            </template>
                            <template v-slot:kotlin>
<pre><fgp-code>plugins {
    <fgp-code-comment>// For JDK 11+</fgp-code-comment>
    id("org.siouan.frontend-jdk11") version "5.0.0"
    <fgp-code-comment>// For JDK 8+</fgp-code-comment>
    id("org.siouan.frontend-jdk8") version "5.0.0"
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
                            <template v-slot:groovy>
<pre><fgp-code>buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        <fgp-code-comment>// For JDK 11+</fgp-code-comment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:5.0.0'
        <fgp-code-comment>// For JDK 8+</fgp-code-comment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk8:5.0.0'
    }
}

<fgp-code-comment>// For JDK 11+</fgp-code-comment>
apply plugin: 'org.siouan.frontend-jdk11'
<fgp-code-comment>// For JDK 8+</fgp-code-comment>
apply plugin: 'org.siouan.frontend-jdk8'</fgp-code></pre>
                            </template>
                            <template v-slot:kotlin>
<pre><fgp-code>buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        <fgp-code-comment>// For JDK 11+</fgp-code-comment>
        classpath("org.siouan:frontend-gradle-plugin-jdk11:5.0.0")
        <fgp-code-comment>// For JDK 8+</fgp-code-comment>
        classpath("org.siouan:frontend-gradle-plugin-jdk8:5.0.0")
    }
}

<fgp-code-comment>// For JDK 11+</fgp-code-comment>
apply(plugin = "org.siouan.frontend-jdk11")
<fgp-code-comment>// For JDK 8+</fgp-code-comment>
apply(plugin = "org.siouan.frontend-jdk8")</fgp-code></pre>
                            </template>
                        </fgp-gradle-scripts>
                    </li>
                </ul>
            </li>
            <li>
                <fgp-site-link :path="fgp.paths.configuration">Configure</fgp-site-link> your project, optionally
                with the help of <fgp-repo-link path="/tree/master/examples">examples</fgp-repo-link> provided for
                typical use cases.
            </li>
            <li>Run <fgp-code>gradlew build</fgp-code>.</li>
            <li>
                If you need to run
                <fgp-code>node</fgp-code>/<fgp-code>npm</fgp-code>/<fgp-code>npx</fgp-code>/<fgp-code
            >yarn</fgp-code
            >
                executables from a command line (e.g. to start a development server), take a look at the
                <fgp-site-link :path="fgp.paths.faqs">FAQ</fgp-site-link>.
            </li>
        </ol>
    </section>
</template>

<script>
import Vue from "vue";
import fgpAppConfig from "../mixin/app-config";
import fgpCode from "../components/code";
import fgpCodeComment from "../components/code-comment";
import fgpGradleDocsLink from "../components/link/gradle-docs-link";
import fgpGradleLink from "../components/link/gradle-link";
import fgpGradleScripts from "../components/gradle-scripts";
import fgpInfo from "../components/info";
import fgpMainTitle from "../components/main-title";
import fgpNodejsLink from "../components/link/nodejs-link";
import fgpPageMeta from "../mixin/page-meta";
import fgpRepoLink from "../components/link/repo-link";
import fgpSiteLink from "../components/link/site-link";
import fgpSubTitle from "../components/sub-title";
import fgpYarnLink from "../components/link/yarn-link";

export default Vue.component("fgp-getting-started", {
    components: {
        fgpCode,
        fgpCodeComment,
        fgpGradleDocsLink,
        fgpGradleLink,
        fgpGradleScripts,
        fgpInfo,
        fgpMainTitle,
        fgpNodejsLink,
        fgpRepoLink,
        fgpSiteLink,
        fgpSubTitle,
        fgpYarnLink
    },
    mixins: [fgpAppConfig, fgpPageMeta],
    data() {
        return {
            htmlTitle: "Getting started: building a Javascript application with Gradle and Node.js",
            linkCanonicalHref: process.env.FGP_WEBSITE_URL + 'getting-started/',
            metaDescription: "Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps."
        }
    }
});
</script>
