<template>
    <fgp-link href="https://www.npmjs.com">{{ $t("navigation.npm.label") }}</fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-npm-link', {
    components: { fgpLink }
});
</script>
