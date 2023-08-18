<template>
    <fgp-site-link :path="`${fgp.paths.configuration}#${name}`">
        <template v-if="$slots.default"><slot /></template>
        <fgp-code v-else>{{ name }}</fgp-code>
    </fgp-site-link>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpSiteLink from '@/components/link/site-link';
import fgpAppConfig from '@/mixin/app-config';

export default Vue.component('fgp-property-link', {
    components: { fgpCode, fgpSiteLink },
    mixins: [fgpAppConfig],
    props: {
        name: {
            type: String,
            required: true
        }
    }
});
</script>
