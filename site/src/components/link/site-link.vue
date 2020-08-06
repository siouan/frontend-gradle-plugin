<template>
    <nuxt-link :to="path"><slot /></nuxt-link>
</template>

<script>
import Vue from "vue";

export default Vue.component("fgp-site-link", {
    props: {
        path: {
            type: String,
            required: true
        }
    }
});
</script>
