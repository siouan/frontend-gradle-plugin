<template>
    <div id="app" class="container-lg">
        <fgp-header class="mb-3" @lang-change="changeLang($event)" />

        <main>
            <router-view />
        </main>
        <fgp-footer class="mt-3" />
    </div>
</template>

<script>
import Vue from "vue";
import fgpFooter from "./component/footer";
import fgpHeader from "./component/header";

export default Vue.component("fgp-app", {
    components: { fgpFooter, fgpHeader },
    methods: {
        changeLang: function(lang) {
            this.$i18n.locale = lang;
        }
    }
});
</script>

<style>
#app {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    min-height: 100vh;
}
</style>
