<template>
    <div class="container-lg">
        <fgp-header class="mb-3" @lang-change="changeLang($event)" />

        <main role="main">
            <nuxt />
        </main>
        <fgp-footer class="mt-3" />
    </div>
</template>

<script>
import Vue from 'vue';
import fgpFooter from '@/components/footer';
import fgpHeader from '@/components/header';

export default Vue.component('fgp-app', {
    components: { fgpFooter, fgpHeader },
    methods: {
        changeLang: lang => {
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
