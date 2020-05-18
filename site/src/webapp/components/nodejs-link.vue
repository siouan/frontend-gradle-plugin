<template>
    <fgp-link href="https://nodejs.org/" :label="$t('navigation.nodejs.label')" />
</template>

<script>
import Vue from "vue";
import FgpLink from "./link.vue";

export default Vue.component("fgp-nodejs-link", {
    components: { FgpLink }
});
</script>
