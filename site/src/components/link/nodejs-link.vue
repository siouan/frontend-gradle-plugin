<template>
    <fgp-link :href="nodeUrl">{{ internalLabel }}</fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link.vue';

const NODEJS_BASE_URL = 'https://nodejs.org';

export default Vue.component('fgp-nodejs-link', {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: '/'
        },
        label: {
            type: String,
            default: null
        }
    },
    computed: {
        internalLabel() {
            return this.label || this.$t('navigation.nodejs.label');
        },
        nodeUrl() {
            return this.path ? `${NODEJS_BASE_URL}${this.path}` : NODEJS_BASE_URL;
        }
    }
});
</script>
