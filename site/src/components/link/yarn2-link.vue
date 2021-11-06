<template>
    <fgp-link
        href="https://yarnpkg.com/"
        :title="$t('navigation.yarn2.title')"
    >{{ $t('navigation.yarn2.label') }}</fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-yarn2-link", {
    components: { fgpLink },
});
</script>
