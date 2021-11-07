<template>
    <fgp-link href="https://yarnpkg.com/" :title="$t('navigation.yarnBerry.title')">{{ label }}</fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-yarn-link", {
    components: { fgpLink },
    props: {
        labelKey: {
            type: String,
            default: 'navigation.yarnBerry.label'
        }
    },
    computed: {
        label() {
            return this.$t(this.labelKey);
        }
    }
});
</script>
