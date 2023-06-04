<template>
    <a :href="href" :title="title" :class="hoverClass"><slot /></a>
</template>

<script>
import Vue from 'vue';

export default Vue.component('fgp-link', {
    props: {
        href: {
            type: String,
            required: true
        },
        title: {
            type: String,
            default: null
        },
        hoverStyleDisabled: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        hoverClass() {
            return this.hoverStyleDisabled ? 'fgp-link-no-hover' : null;
        }
    }
});
</script>

<style scoped>
.fgp-link-no-hover {
    text-decoration: unset;
}
</style>
