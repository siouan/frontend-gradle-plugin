<template>
    <section>
        <FgpMainTitle>Getting started</FgpMainTitle>

        <FgpSubTitle>Requirements</FgpSubTitle>
        <p>The following tools must be installed to use the plugin:</p>
        <ul>
            <li>
                <FgpGradleLink /> 6.1+
            </li>
        </ul>
        <p>
            The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in the
            <FgpRepoLink path="/blob/main/CONTRIBUTING.md">contributing notes</FgpRepoLink>).
        </p>

        <FgpSubTitle>Supported distributions</FgpSubTitle>
        <ul>
            <li><FgpNodejsLink />: &ge;6.2.1</li>
            <li><FgpYarnLink :version="1" />: &ge;1.0.0 (<FgpYarnLink label-key="navigation.yarnBerry.originalName" /> initial support planned in release 6.0.0)</li>
        </ul>

        <FgpSubTitle>Steps</FgpSubTitle>
        <FgpInfo>
            Starting from release <FgpRepoLink path="/releases/tag/v3.0.1">3.0.1</FgpRepoLink>, ID
            <FgpCode>org.siouan.frontend</FgpCode> and classpath
            <FgpCode>org.siouan:frontend-gradle-plugin:&lt;version></FgpCode> are deprecated. If you are already using
            the plugin, we recommend <FgpRepoLink path="/releases/tag/v5.3.0">upgrading</FgpRepoLink> to the latest
            release as soon as possible.
        </FgpInfo>
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
    <FgpCodeComment>// For JDK 11+</FgpCodeComment>
    id 'org.siouan.frontend-jdk11' version '5.3.0'
    <FgpCodeComment>// For JDK 8+</FgpCodeComment>
    id 'org.siouan.frontend-jdk18' version '5.3.0'
}</FgpCode></pre>
                            </template>
                            <template #kotlin>
                                <pre><FgpCode>plugins {
    <FgpCodeComment>// For JDK 11+</FgpCodeComment>
    id("org.siouan.frontend-jdk11") version "5.3.0"
    <FgpCodeComment>// For JDK 8+</FgpCodeComment>
    id("org.siouan.frontend-jdk8") version "5.3.0"
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
        <FgpCodeComment>// For JDK 11+</FgpCodeComment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk11:5.3.0'
        <FgpCodeComment>// For JDK 8+</FgpCodeComment>
        classpath 'org.siouan:frontend-gradle-plugin-jdk8:5.3.0'
    }
}

<FgpCodeComment>// For JDK 11+</FgpCodeComment>
apply plugin: 'org.siouan.frontend-jdk11'
<FgpCodeComment>// For JDK 8+</FgpCodeComment>
apply plugin: 'org.siouan.frontend-jdk8'</FgpCode></pre>
                            </template>
                            <template #kotlin>
                                <pre><FgpCode>buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        <FgpCodeComment>// For JDK 11+</FgpCodeComment>
        classpath("org.siouan:frontend-gradle-plugin-jdk11:5.3.0")
        <FgpCodeComment>// For JDK 8+</FgpCodeComment>
        classpath("org.siouan:frontend-gradle-plugin-jdk8:5.3.0")
    }
}

<FgpCodeComment>// For JDK 11+</FgpCodeComment>
apply(plugin = "org.siouan.frontend-jdk11")
<FgpCodeComment>// For JDK 8+</FgpCodeComment>
apply(plugin = "org.siouan.frontend-jdk8")</FgpCode></pre>
                            </template>
                        </FgpGradleScripts>
                    </li>
                </ul>
            </li>
            <li>
                <FgpSiteLink :path="$config.public.paths.configuration">Configure</FgpSiteLink>
                your project, optionally with the help of
                <FgpRepoLink path="/tree/main/examples">examples</FgpRepoLink> provided for typical use cases.
            </li>
            <li>Run <FgpCode>gradlew build</FgpCode>.</li>
            <li>
                If you need to run
                <FgpCode>node</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>npx</FgpCode>/<FgpCode>yarn</FgpCode>
                executables from a command line (e.g. to start a development server), take a look at the
                <FgpSiteLink :path="$config.public.paths.faqs">FAQ</FgpSiteLink>.
            </li>
        </ol>
    </section>
</template>

<script setup lang="ts">
const canonicalUrl = 'https://siouan.github.io/frontend-gradle-plugin/getting-started';
const title = 'Getting started: building a Javascript application with Gradle and Node.js';
const description = 'Guide to get started with the plugin: requirements, supported Node.js and Yarn distributions, installation steps.';

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
