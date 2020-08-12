<template>
    <fgp-link :href="yarnUrls[version - 1]" :title="$t(`navigation.yarn${version}.title`)">{{
        $t(`navigation.yarn${version}.label`)
    }}</fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

export default Vue.component("fgp-yarn-link", {
    components: { fgpLink },
    props: {
        version: {
            type: Number,
            default: 1
        }
    },
    data() {
        return {
            yarnUrls: ["https://classic.yarnpkg.com/", "https://yarnpkg.com/"]
        };
    }
});
</script>
