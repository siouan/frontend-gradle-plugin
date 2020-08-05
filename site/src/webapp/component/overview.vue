<template>
    <section>
        <fgp-main-title class="text-center">
            Frontend Gradle plugin
            <small class="text-muted">
                - Integrated <fgp-nodejs-link />, <fgp-npm-link />, <fgp-yarn-link /> builds
            </small>
            <fgp-image-link
                href="https://github.com/siouan/frontend-gradle-plugin/releases/tag/v3.0.1"
                src="https://img.shields.io/badge/Latest%20release-3.0.1-blue.svg"
                alt="Latest release 3.0.1"
                class="ml-1"
            />
        </fgp-main-title>

        <p>
            This plugin allows to integrate into <fgp-gradle-link /> a build based on
            <fgp-nodejs-link />/<fgp-npm-link />/<fgp-yarn-link />. It is inspired by the philosophy of the
            <fgp-link href="https://github.com/eirslett/frontend-maven-plugin">Frontend Maven plugin</fgp-link>, an
            equivalent plugin for
            <fgp-link href="https://maven.apache.org" title="Apache Maven Project">Maven</fgp-link>. Follow the
            <fgp-site-link path="#quick-start">quick start guide</fgp-site-link> hereafter, and build your frontend
            application. Take a look at the
            <fgp-repo-link path="/releases" class="text-lowercase">{{
                $t("navigation.repository.releases.label")
            }}</fgp-repo-link>
            if you are migrating from a previous version.
        </p>

        <fgp-sub-title>Features</fgp-sub-title>

        <fgp-feature-card title="Distribution management" icon-class="fas fa-dice-d6 text-primary">
            The plugin downloads and installs a <fgp-nodejs-link /> distribution and/or a <fgp-yarn-link /> distribution
            when required. Optionally, a shared/global distribution may be used instead to avoid network overhead and
            duplication. The plugin may also use a HTTP proxy server for downloads, to take advantage of any caching
            facility, and submit to the organization's security rules. Basic authentication scheme is supported for both
            distribution and proxy servers.
        </fgp-feature-card>
        <fgp-feature-card title="Configurable dependencies installation" icon-class="fa fa-cogs text-secondary">
            Depending on the environment, installing frontend dependencies using the
            <fgp-code>package.json</fgp-code> file may require a different command (e.g. <fgp-code>npm ci</fgp-code>).
        </fgp-feature-card>
        <fgp-feature-card title="Built-in tasks" icon-class="fa fa-plug text-danger">
            No need to define tasks to build, clean, check, or publish the frontend application through Gradle
            lifecycle. The plugin provides ready-to-use <fgp-site-link :path="fgp.paths.tasks">tasks</fgp-site-link> out
            of the box, and ensures their implementation matches Gradle's
            <fgp-gradle-docs-link path="/current/userguide/task_configuration_avoidance.html"
                >recommandations</fgp-gradle-docs-link
            >. Plug scripts from a <fgp-code>package.json</fgp-code> file with the
            <fgp-site-link :path="`${fgp.paths.configuration}#dsl-reference`">DSL</fgp-site-link>, and run
            <fgp-code>gradlew build</fgp-code>.
        </fgp-feature-card>
        <fgp-feature-card title="Customization" icon-class="fa fa-code-branch text-warning">
            For more complex use cases, the plugin provides types to create tasks and run custom commands with
            <fgp-nodejs-link />, <fgp-npm-link />, <fgp-npx-link />, <fgp-yarn-link />.
        </fgp-feature-card>

        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">Under the hood</h5>
                <ul class="card-text">
                    <li>
                        <strong>Lazy configuration</strong>: tasks configuration is delayed until necessary thanks to
                        the use of Gradle
                        <fgp-gradle-docs-link path="/current/userguide/lazy_configuration.html"
                            >lazy configuration API</fgp-gradle-docs-link
                        >, to optimize performance of builds and ease chaining tasks I/O.
                    </li>
                    <li>
                        <strong>Self-contained domain design</strong>: the plugin design is influenced by
                        <fgp-link href="http://cleancoder.com/" title="Clean coder">clean coding</fgp-link> principles.
                        The domain layer is isolated from any framework and infrastructure. Writing cross-platform unit
                        tests and maintaining the plugin is easier. Code coverage and predictability increase.
                    </li>
                </ul>
            </div>
        </div>

        <article id="quick-start">
            <fgp-sub-title>Quick start</fgp-sub-title>

            <fgp-sub-sub-title>Requirements</fgp-sub-sub-title>
            <ul>
                <li><fgp-gradle-link /> 5.1+</li>
                <li>JDK 8+ 64 bits</li>
                <li>
                    The plugin is built and tested on Linux, Mac OS, Windows (see the list of build environments used in
                    the <fgp-repo-link path="/blob/master/CONTRIBUTING.md">contributing notes</fgp-repo-link>).
                </li>
            </ul>

            <fgp-sub-sub-title>Supported distributions</fgp-sub-sub-title>
            <ul>
                <li><fgp-nodejs-link /> 6.2.1+</li>
                <li><fgp-yarn-link /> 1.0+ (<fgp-yarn-link :version="2" /> not supported)</li>
            </ul>

            <fgp-sub-sub-title>Steps</fgp-sub-sub-title>
            <ul>
                <li><fgp-site-link :path="fgp.paths.installation">Install</fgp-site-link> the plugin.</li>
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
            </ul>
        </article>
    </section>
</template>

<script>
import Vue from "vue";
import fgpAppConfig from "../mixin/app-config";
import fgpCode from "./layout/code";
import fgpFeatureCard from "./feature-card";
import fgpGradleDocsLink from "./link/gradle-docs-link";
import fgpGradleLink from "./link/gradle-link";
import fgpImageLink from "./link/image-link";
import fgpLink from "./link/link";
import fgpMainTitle from "./layout/main-title";
import fgpNodejsLink from "./link/nodejs-link";
import fgpNpmLink from "./link/npm-link";
import fgpNpxLink from "./link/npx-link";
import fgpRepoLink from "./link/repo-link";
import fgpSiteLink from "./link/site-link";
import fgpSubTitle from "./layout/sub-title";
import fgpYarnLink from "./link/yarn-link";

export default Vue.component("fgp-overview", {
    components: {
        fgpCode,
        fgpFeatureCard,
        fgpGradleDocsLink,
        fgpGradleLink,
        fgpImageLink,
        fgpLink,
        fgpMainTitle,
        fgpNodejsLink,
        fgpNpmLink,
        fgpNpxLink,
        fgpRepoLink,
        fgpSiteLink,
        fgpSubTitle,
        fgpYarnLink
    },
    mixins: [fgpAppConfig]
});
</script>
