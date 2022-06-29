<template>
    <section>
        <fgp-main-title class="text-center">
            Frontend Gradle plugin
            <small class="text-muted">
                - Integrated <fgp-nodejs-link />, <fgp-npm-link />, <fgp-yarn-link />
                builds
            </small>
            <fgp-image-link
                href="https://github.com/siouan/frontend-gradle-plugin/releases/tag/v6.0.1"
                src="https://img.shields.io/badge/Latest%20release-6.0.1-blue.svg"
                alt="Latest release 6.0.1"
                class="ml-1"
            />
        </fgp-main-title>

        <p>
            This plugin allows to integrate into <fgp-gradle-link /> a build based on
            <fgp-nodejs-link />/<fgp-npm-link />/<fgp-yarn-link />. It is inspired by the philosophy of the
            <fgp-link href="https://github.com/eirslett/frontend-maven-plugin">Frontend Maven plugin</fgp-link>, an
            equivalent plugin for
            <fgp-link href="https://maven.apache.org" title="Apache Maven Project">Maven</fgp-link>. Follow the
            <fgp-site-link :path="fgp.paths.gettingStarted">quick start guide</fgp-site-link>, and build your frontend
            application. Take a look at the
            <fgp-repo-link path="/releases" class="text-lowercase">{{
                $t("navigation.repository.releases.label")
            }}</fgp-repo-link>
            if you are migrating from a previous version.
        </p>

        <fgp-sub-title>Features</fgp-sub-title>

        <fgp-feature-card title="Distribution management" icon-class="fas fa-dice-d6 text-primary">
            The plugin downloads and installs a <fgp-nodejs-link /> distribution. When required, the plugin may also
            trigger the install of a Yarn distribution relying on the
            <fgp-yarn-link label-key="navigation.yarnBerry.originalName" /> baseline. This means that both
            <fgp-yarn1-link /> and <fgp-yarn2-link /> distributions are supported. Optionally, a
            shared/global <fgp-nodejs-link /> distribution may be used instead to avoid network overhead and
            duplication. The plugin may also use a HTTP proxy server when downloading the <fgp-nodejs-link />
            distribution, to take advantage of any caching facility, and submit to the organization's security rules.
            Basic authentication scheme is supported for both distribution and proxy servers.
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
    </section>
</template>

<script>
import Vue from "vue";
import fgpAppConfig from "../mixin/app-config";
import fgpCode from "../components/code";
import fgpFeatureCard from "../components/feature-card";
import fgpGradleDocsLink from "../components/link/gradle-docs-link";
import fgpGradleLink from "../components/link/gradle-link";
import fgpImageLink from "../components/link/image-link";
import fgpLink from "../components/link/link";
import fgpMainTitle from "../components/main-title";
import fgpNodejsLink from "../components/link/nodejs-link";
import fgpNpmLink from "../components/link/npm-link";
import fgpNpxLink from "../components/link/npx-link";
import fgpPageMeta from "../mixin/page-meta";
import fgpRepoLink from "../components/link/repo-link";
import fgpSiteLink from "../components/link/site-link";
import fgpSubTitle from "../components/sub-title";
import fgpYarnLink from "../components/link/yarn-link";
import fgpYarn1Link from "../components/link/yarn1-link";
import fgpYarn2Link from "../components/link/yarn2-link";

export default Vue.component("fgp-index", {
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
        fgpYarnLink,
        fgpYarn1Link,
        fgpYarn2Link
    },
    mixins: [fgpAppConfig, fgpPageMeta],
    data() {
        return {
            htmlTitle: "Gradle Node/NPM/Yarn plugin to build Javascript applications",
            linkCanonicalHref: process.env.FGP_WEBSITE_URL,
            metaDescription: "All-in-one Gradle Node plugin, Gradle NPM plugin, Gradle Yarn plugin to build Javascript applications with Gradle: distribution management, built-in tasks, NPX support"
        }
    }
});
</script>
