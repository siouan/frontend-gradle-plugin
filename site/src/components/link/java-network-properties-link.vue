<template>
    <fgp-link href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/doc-files/net-properties.html">
        <slot />
    </fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-java-network-properties-link", {
    components: { fgpLink }
});
</script>
