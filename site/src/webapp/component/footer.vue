<template>
    <footer class="container text-center">
        <div class="row justify-content-center">
            <p class="text-muted col col-lg-9">
                <small>
                    Credits: this website is a single-page application based on
                    <fgp-link href="https://vuejs.org/">Vue.js</fgp-link>,
                    <fgp-link href="https://getbootstrap.com/">Bootstrap</fgp-link>, web font
                    <fgp-code>EtelkaTextPro</fgp-code> copied from
                    <fgp-link href="http://www.onlinewebfonts.com">oNline Web Fonts</fgp-link>, and whose construction
                    and publication are handled by the
                    <fgp-repo-link :path="fgp.paths.overview">Frontend Gradle plugin</fgp-repo-link>!
                </small>
            </p>
        </div>
    </footer>
</template>

<script>
import Vue from "vue";
import fgpLink from "./link/link";
import fgpAppConfig from "../mixin/app-config";

export default Vue.component("fgp-footer", {
    components: { fgpLink },
    mixins: [fgpAppConfig]
});
</script>
