<template>
    <blockquote class="blockquote mt-3 px-3 fgp-block-quote"><slot /></blockquote>
</template>

<script>
import Vue from "vue";

export default Vue.component("fgp-block-quote", {});
</script>

<style scoped>
.fgp-block-quote {
    color: #9f9f9f;
    border-color: #cfcfcf;
    border-style: solid;
    border-width: 0 0 0 3px;
    font-size: 1rem;
}
</style>
