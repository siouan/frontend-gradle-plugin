<template>
    <section>
        <fgp-main-title>Installation</fgp-main-title>

        <fgp-sub-title>
            Using
            <fgp-gradle-docs-link path="/current/userguide/plugins.html#sec:plugins_block">
                Gradle DSL</fgp-gradle-docs-link
            ></fgp-sub-title
        >

        <p>This is the modern and recommended approach.</p>

        <fgp-gradle-scripts id="install-gradle-dsl">
            <template v-slot:groovy>
                <pre><fgp-code>plugins {
    id 'org.siouan.frontend' version '3.1.0'
}</fgp-code></pre>
            </template>
            <template v-slot:kotlin>
                <pre><fgp-code>plugins {
    id("org.siouan.frontend") version "3.1.0"
}</fgp-code></pre>
            </template>
        </fgp-gradle-scripts>

        <fgp-sub-title>
            Using
            <fgp-gradle-docs-link path="/current/userguide/plugins.html#sec:applying_plugins_buildscript">
                Gradle build script block</fgp-gradle-docs-link
            ></fgp-sub-title
        >

        <p>This is the legacy approach.</p>

        <fgp-gradle-scripts id="install-gradle-build-script-block">
            <template v-slot:groovy>
                <pre><fgp-code>buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        classpath 'org.siouan:frontend-gradle-plugin:3.1.0'
    }
}

apply plugin: 'org.siouan.frontend'</fgp-code></pre>
            </template>
            <template v-slot:kotlin>
                <pre><fgp-code>buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.siouan:frontend-gradle-plugin:3.1.0")
    }
}

apply(plugin = "org.siouan.frontend")</fgp-code></pre>
            </template>
        </fgp-gradle-scripts>
    </section>
</template>

<script>
import Vue from "vue";
import fgpCode from "./layout/code";
import fgpGradleScripts from "./layout/gradle-scripts";
import fgpMainTitle from "./layout/main-title";
import fgpSubTitle from "./layout/sub-title";

export default Vue.component("fgp-installation", {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpMainTitle,
        fgpSubTitle
    }
});
</script>
