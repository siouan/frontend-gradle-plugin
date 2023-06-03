<template>
    <fgp-link :href="`https://guides.gradle.org${path}`"><slot /></fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-gradle-guides-link', {
    components: { fgpLink },
    props: {
        path: {
            type: String,
            default: null
        }
    }
});
</script>
