<template>
    <fgp-faq>
        <template #question>
            How to configure the plugin so as it can download a <fgp-nodejs-link /> distribution from a custom server?
        </template>
        <template #answer>
            Properties <fgp-property-link name="node-distribution-url-root-property" /> and
            <fgp-property-link name="node-distribution-url-path-pattern-property" /> exist specially for this use case.
        </template>
    </fgp-faq>
</template>

<script>
import Vue from 'vue';
import fgpFaq from '@/components/faq/faq';
import fgpNodejsLink from '@/components/link/nodejs-link';

export default Vue.component('fgp-custom-nodejs-distribution-server', {
    components: {
        fgpFaq,
        fgpNodejsLink
    }
});
</script>
