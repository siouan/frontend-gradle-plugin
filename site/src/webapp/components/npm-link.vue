<template>
    <fgp-link href="https://www.npmjs.com/" :label="$t('navigation.npm.label')" />
</template>

<script>
import Vue from "vue";
import FgpLink from "./link.vue";

export default Vue.component("fgp-npm-link", {
    components: { FgpLink }
});
</script>
