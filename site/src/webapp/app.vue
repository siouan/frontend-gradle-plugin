<template>
    <div id="app" class="container-lg">
        <fgp-header class="mb-3" @lang-change="changeLang($event)" />

        <router-view />
        <div class="mx-auto my-5 w-50 p-5 border text-center">{{ $t("maintenance.inProgress") }}</div>
    </div>
</template>

<script>
import Vue from "vue";
import FgpHeader from "./components/header.vue";

export default Vue.component("fgp-app", {
    components: {
        FgpHeader
    },
    methods: {
        changeLang: function(lang) {
            //console.log("Catched lang change event:", lang);
            this.$i18n.locale = lang;
        }
    }
});
</script>

<style>
@font-face {
    font-family: EtelkaLightPro;
    src: url("~@/assets/EtelkaLightPro.otf") format("opentype");
}

@font-face {
    font-family: EtelkaTextPro;
    src: url("~@/assets/EtelkaTextPro.otf") format("opentype");
}

#app {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

* {
    font-family: EtelkaLightPro, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
</style>
