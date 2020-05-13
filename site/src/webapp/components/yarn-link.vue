<template>
    <fgp-link
        :href="yarnUrls[yarnVersion - 1]"
        :label="$t(`navigation.yarn${yarnVersion}.label`)"
        :title="$t(`navigation.yarn${yarnVersion}.title`)"
    />
</template>

<script>
import Vue from "vue";
import FgpLink from "./link.vue";

export default Vue.component("fgp-yarn-link", {
    components: { FgpLink },
    props: {
        yarnVersion: {
            type: Number,
            default: 1
        }
    },
    data: function() {
        return {
            yarnUrls: ["https://classic.yarnpkg.com/", "https://yarnpkg.com/"]
        };
    }
});
</script>
