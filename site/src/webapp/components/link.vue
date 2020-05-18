<template>
    <a :href="href" :title="title">{{ label }}</a>
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
        },
        label: {
            type: String,
            required: true
        }
    }
});
</script>
