<template>
    <section>
        <FgpMainTitle>Frequently asked questions</FgpMainTitle>

        <nav>
            <ul>
                <li>
                    <FgpSiteLink :path="`${$config.public.paths.faqs}#node-npm-npx-direct-use`">
                        How to use <FgpCode>node</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>npx</FgpCode> executables
                        apart from Gradle when the <FgpCode>Node.js</FgpCode> distribution is downloaded by the plugin?
                    </FgpSiteLink>
                </li>
                <li>
                    <FgpSiteLink :path="`${$config.public.paths.faqs}#yarn-direct-use`">
                        How to use <FgpCode>yarn</FgpCode> executable apart from Gradle when the Yarn distribution is
                        downloaded by the plugin?
                    </FgpSiteLink>
                </li>
                <li>
                    <FgpSiteLink :path="`${$config.public.paths.faqs}#unsupported-platform-exception`">
                        What should I do when error
                        <FgpCode>UnsupportedPlatformException</FgpCode> occurs?
                    </FgpSiteLink>
                </li>
                <li>
                    <FgpSiteLink :path="`${$config.public.paths.faqs}#custom-nodejs-distribution-server`">
                        How to configure plugin so as I can download
                        <FgpCode>Node.js</FgpCode> distribution from a custom server?
                    </FgpSiteLink>
                </li>
            </ul>
        </nav>

        <FgpNodeNpmNpxDirectUseFaqV6 id="node-npm-npx-direct-use" />
        <FgpYarnDirectUseFaqV6 id="yarn-direct-use" />
        <FgpUnsupportedPlatformExceptionFaq id="unsupported-platform-exception" />
        <FgpCustomNodejsDistributionServerFaq id="custom-nodejs-distribution-server" />
    </section>
</template>

<script setup lang="ts">
const canonicalUrl = 'https://siouan.github.io/frontend-gradle-plugin/faqs';
const title = 'Frequently asked questions';
const description = 'Using node, npm, npx, yarn executables apart from Gradle and resolving common issues.';

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
