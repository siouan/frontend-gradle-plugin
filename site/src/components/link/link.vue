<template>
    <a :href="href" :title="title"><slot /></a>
</template>

<script>
import Vue from "vue";

export default Vue.component("fgp-link", {
    props: {
        href: {
            type: String,
            required: true
        },
        title: {
            type: String,
            default: null
        }
    }
});
</script>
