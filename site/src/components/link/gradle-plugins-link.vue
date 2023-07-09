<template>
    <fgp-link :href="`https://plugins.gradle.org${path}`" :hover-style-disabled="hoverStyleDisabled"><slot /></fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-gradle-plugins-link', {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: null
        },
        hoverStyleDisabled: {
            type: Boolean,
            default: false
        }
    }
});
</script>
