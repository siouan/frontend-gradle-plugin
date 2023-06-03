<template>
    <fgp-link :href="`https://docs.gradle.org${path}`" :title="title" :hover-style-disabled="hoverStyleDisabled"
    ><slot /></fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-gradle-docs-link', {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: null
        },
        title: {
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
