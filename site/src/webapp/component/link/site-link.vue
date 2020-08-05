<template>
    <router-link :to="path"><slot /></router-link>
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
