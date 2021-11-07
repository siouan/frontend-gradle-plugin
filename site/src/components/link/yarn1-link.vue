<template>
    <fgp-link
        href="https://classic.yarnpkg.com/"
        :title="$t('navigation.yarn1.title')"
    >{{ $t('navigation.yarn1.label') }}</fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-yarn1-link", {
    components: { fgpLink },
});
</script>
