<template>
    <fgp-site-link :path="`${fgp.paths.tasks}#${name}`">
        <fgp-code>{{ name }}</fgp-code>
    </fgp-site-link>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpSiteLink from '@/components/link/site-link';
import fgpAppConfig from '@/mixin/app-config';

export default Vue.component('fgp-task-link', {
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
