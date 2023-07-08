<template>
    <fgp-link :href="href" :title="internalTitle">{{ internalLabel }}</fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-yarn-link', {
    components: { fgpLink },
    props: {
        label: {
            type: String,
            default: null,
        },
        labelKey: {
            type: String,
            default: 'navigation.yarnBerry.label'
        },
        path: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: null
        }
    },
    computed: {
        href() {
            return `https://yarnpkg.com/${this.path}`;
        },
        internalLabel() {
            return this.label || this.$t(this.labelKey);
        },
        internalTitle() {
            return this.title || this.$t('navigation.yarnBerry.title');
        }
    }
});
</script>
