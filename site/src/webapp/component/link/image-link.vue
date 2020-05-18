<template>
    <fgp-link :href="href" :title="title"><img :src="src" :alt="alt"/></fgp-link>
</template>

<script>
import Vue from "vue";

export default Vue.component("fgp-image-link", {
    props: {
        href: {
            type: String,
            required: true
        },
        title: {
            type: String,
            default: null
        },
        src: {
            type: String,
            required: true
        },
        alt: {
            type: String,
            default: null
        }
    }
});
</script>
