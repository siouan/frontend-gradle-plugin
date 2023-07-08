<template>
    <fgp-link href="https://pnpm.io/">{{ $t("navigation.pnpm.label") }}</fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-pnpm-link', {
    components: { fgpLink }
});
</script>
