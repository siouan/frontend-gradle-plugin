<template>
    <footer class="container text-center">
        <div class="row justify-content-center">
            <p class="text-muted col col-lg-9">
                <small>
                    Credits: this website is a single-page application based on
                    <fgp-link href="https://nuxtjs.org/">NuxtJS</fgp-link>,
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
import Vue from 'vue';
import fgpAppConfig from '@/mixin/app-config';
import fgpCode from '@/components/code';
import fgpLink from '@/components/link/link';
import fgpRepoLink from '@/components/link/repo-link';

export default Vue.component('fgp-footer', {
    components: { fgpCode, fgpLink, fgpRepoLink },
    mixins: [fgpAppConfig]
});
</script>
