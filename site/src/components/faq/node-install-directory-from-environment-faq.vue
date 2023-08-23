<template>
    <fgp-faq>
        <template #question>
            How can I set the install directory of the <fgp-nodejs-link /> distribution using an environment variable?
        </template>
        <template #answer>
            <p>
                The best way to set the install directory
                <fgp-gradle-docs-link path="/current/userguide/build_environment.html#sec:gradle_environment_variables"
                >using the value of an environment variable</fgp-gradle-docs-link> is to rely on Gradle API rather than
                on Java API:
            </p>

            <fgp-gradle-scripts id="plugin-dsl">
                <template #groovy>
                <pre><fgp-code>frontend {
    <fgp-property-link name="nodeInstallDirectory" /> = providers.environmentVariable('NODEJS_HOME')
}</fgp-code></pre>
                </template>
                <template #kotlin>
                <pre><fgp-code>frontend {
    <fgp-property-link name="nodeInstallDirectory" />.set(providers.environmentVariable("NODEJS_HOME"))
}</fgp-code></pre>
                </template>
            </fgp-gradle-scripts>
        </template>
    </fgp-faq>
</template>

<script>
import Vue from 'vue';
import fgpFaq from '@/components/faq/faq';

export default Vue.component('fgp-node-install-directory-from-environment-faq', {
    components: {
        fgpFaq
    }
});
</script>
