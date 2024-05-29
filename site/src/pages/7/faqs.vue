<template>
    <section>
        <FgpMainTitle>Frequently asked questions</FgpMainTitle>

        <nav>
            <ul>
                <li>
                    <FgpSiteLink :path="`${$config.public.paths.faqs}#node-corepack-npm-pnpm-yarn-direct-use`">
                        How to use
                        <FgpCode>node</FgpCode
                        >/<FgpCode>corepack</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode
                            >yarn</FgpCode
                        >
                        executables apart from Gradle when the
                        <FgpCode>Node.js</FgpCode> distribution is downloaded by the plugin?
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
                <li>
                    <FgpSiteLink :path="`${$config.public.paths.faqs}#cache-directory`">
                        What's the purpose of the
                        <FgpCode>.frontend-gradle-plugin</FgpCode> directory generated in each project?
                    </FgpSiteLink>
                </li>
            </ul>
        </nav>

        <FgpNodeCorepackNpmPnpmYarnDirectUseFaq id="node-corepack-npm-pnpm-yarn-direct-use" />
        <FgpUnsupportedPlatformExceptionFaq id="unsupported-platform-exception" />
        <FgpCustomNodejsDistributionServerFaq id="custom-nodejs-distribution-server" />
        <FgpCacheDirectoryFaq id="cache-directory" />
    </section>
</template>

<script setup lang="ts">
const canonicalUrl = 'https://siouan.github.io/frontend-gradle-plugin/faqs';
const title = 'Frequently asked questions';
const description = 'Using corepack, node, npm, pnpm, yarn executables apart from Gradle and resolving common issues.';

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
