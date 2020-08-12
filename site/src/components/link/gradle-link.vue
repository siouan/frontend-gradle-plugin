<template>
    <fgp-link :href="gradleUrl">{{ $t("navigation.gradle.label") }}</fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

const GRADLE_BASE_URL = "https://gradle.org";

export default Vue.component("fgp-gradle-link", {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: null
        }
    },
    computed: {
        gradleUrl() {
            if (this.path) {
                return `${GRADLE_BASE_URL}${this.path}`;
            }
            return GRADLE_BASE_URL;
        }
    }
});
</script>
