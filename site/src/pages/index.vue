<template>
    <section>
        <FgpMainTitle class="text-center">
            Frontend Gradle plugin
            <FgpImageLink
                href="https://github.com/siouan/frontend-gradle-plugin/releases/tag/v8.0.0"
                src="https://img.shields.io/badge/Latest%20release-8.0.0-blue.svg"
                alt="Latest release 8.0.0"
                class="ml-1"
            />
        </FgpMainTitle>

        <p class="fs-2 text-center">Build <FgpNodejsLink />-based applications with <FgpGradleLink /></p>

        <p>
            This plugin allows to build a <FgpNodejsLink />-based application relying on a package manager supported by
            <FgpCorepackLink />: <FgpNpmLink />, <FgpPnpmLink />, <FgpYarnLink />. It is inspired by the philosophy of the
            <FgpLink href="https://github.com/eirslett/frontend-maven-plugin">Frontend Maven plugin</FgpLink>, an
            equivalent plugin for <FgpLink href="https://maven.apache.org" title="Apache Maven Project">Maven</FgpLink>.
            Follow the <FgpSiteLink :path="$config.public.paths.gettingStarted">quick start guide</FgpSiteLink>, and
            build your frontend application. Take a look at the
            <FgpRepoLink path="/releases" class="text-lowercase">{{
                $t('navigation.repository.releases.label')
            }}</FgpRepoLink>
            if you are migrating from a previous version.
        </p>

        <FgpSubTitle>Features</FgpSubTitle>

        <FgpFeatureCard title="Distribution management" icon-class="fa fa-cloud-download-alt text-primary">
            By default, the plugin downloads and installs a
            <FgpNodejsLink /> distribution. Multiple Gradle (sub-)projects may use the distribution downloaded by one of
            them, or even use a distribution already installed in the workstation to avoid network overhead and
            duplication. The plugin may also use a HTTP proxy server when downloading the
            <FgpNodejsLink /> distribution, to take advantage of any caching facility, and submit to the organization's
            security rules. Basic authentication scheme is supported for both distribution and proxy servers. In case of
            connectivity issues, downloading the <FgpNodejsLink /> distribution is also retryable, with a configurable
            exponential backoff strategy.
        </FgpFeatureCard>
        <FgpFeatureCard title="Support of multiple package managers" icon-class="fas fa-dice-d6">
            The plugin delegates installation of your favorite package manager to
            <FgpCorepackLink />. Choose your package manager among <FgpNpmLink />, <FgpPnpmLink />, <FgpYarnLink />, set
            the
            <FgpNodejsLink path="/api/packages.html#packagemanager" label="packageManager" />
            property in your <FgpCode>package.json</FgpCode> file, and <FgpCorepackLink /> downloads and activates the
            distribution expected for each (sub-)project.
        </FgpFeatureCard>
        <FgpFeatureCard title="Configurable dependencies installation" icon-class="fa fa-cogs text-secondary">
            Depending on the environment, installing frontend dependencies using the
            <FgpCode>package.json</FgpCode> file may require a different command (e.g. <FgpCode>npm ci</FgpCode>).
        </FgpFeatureCard>
        <FgpFeatureCard title="Built-in tasks" icon-class="fa fa-plug text-danger">
            No need to define tasks to build, clean, check, or publish the frontend application through Gradle
            lifecycle. The plugin provides ready-to-use
            <FgpSiteLink :path="$config.public.paths.tasks">tasks</FgpSiteLink> out of the box, and ensures their
            implementation matches Gradle's
            <FgpGradleDocsLink path="/current/userguide/task_configuration_avoidance.html"
            >recommendations</FgpGradleDocsLink>. Plug scripts from a <FgpCode>package.json</FgpCode> file with the
            <FgpSiteLink :path="$config.public.paths.configuration">DSL</FgpSiteLink>, and run
            <FgpCode>gradlew build</FgpCode>.
        </FgpFeatureCard>
        <FgpFeatureCard title="Customization" icon-class="fa fa-code-branch text-warning">
            For more complex use cases, the plugin provides types to create tasks and run custom commands with
            <FgpNodejsLink />, <FgpCorepackLink />, <FgpNpmLink />, <FgpPnpmLink />, <FgpYarnLink />.
        </FgpFeatureCard>

        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">Under the hood</h5>
                <ul class="card-text">
                    <li>
                        <strong>Lazy configuration</strong>: tasks configuration is delayed until necessary thanks to
                        the use of Gradle
                        <FgpGradleDocsLink path="/current/userguide/lazy_configuration.html"
                            >lazy configuration API</FgpGradleDocsLink
                        >, to optimize performance of builds and ease chaining tasks I/O.
                    </li>
                    <li>
                        <strong>Self-contained domain design</strong>: the plugin design is influenced by
                        <FgpLink href="http://cleancoder.com/" title="Clean coder">clean coding</FgpLink>
                        principles. The domain layer is isolated from any framework and infrastructure. Writing
                        cross-platform unit tests and maintaining the plugin is easier. Code coverage and predictability
                        increase.
                    </li>
                </ul>
            </div>
        </div>
    </section>
</template>

<script setup lang="ts">
const canonicalUrl = 'https://siouan.github.io/frontend-gradle-plugin/';
const title = 'Gradle Node/NPM/PNPM/Yarn plugin to build Javascript applications';
const description =
    'All-in-one Gradle Node plugin, Gradle NPM plugin, Gradle PNPM plugin, Gradle Yarn plugin to build Javascript applications with Gradle: distribution management, built-in tasks';

useHead({
    link: [{ rel: 'canonical', href: canonicalUrl }]
});
useSeoMeta({
    description,
    ogDescription: description,
    ogTitle: title,
    ogUrl: canonicalUrl,
    title,
});
</script>
