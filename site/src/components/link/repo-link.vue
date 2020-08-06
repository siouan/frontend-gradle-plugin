<template>
    <fgp-link :href="href"><slot /></fgp-link>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link";

const REPO_BASE_URL = "https://github.com/siouan/frontend-gradle-plugin";

export default Vue.component("fgp-repo-link", {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: null
        }
    },
    computed: {
        href() {
            if (this.path === null) {
                return REPO_BASE_URL;
            }
            return `${REPO_BASE_URL}${this.path}`;
        }
    }
});
</script>
