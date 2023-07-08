<template>
    <fgp-link href="https://github.com/nodejs/corepack">{{ $t("navigation.corepack.label") }}</fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-corepack-link', {
    components: { fgpLink }
});
</script>
