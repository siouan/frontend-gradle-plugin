<template>
    <fgp-link href="https://github.com/npm/npx">{{ $t("navigation.npx.label") }}</fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-npx-link", {
    components: { fgpLink }
});
</script>
