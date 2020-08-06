<template>
    <fgp-link :href="`https://docs.gradle.org${path}`"><slot /></fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-gradle-docs-link", {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: null
        }
    }
});
</script>
