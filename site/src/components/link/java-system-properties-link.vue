<template>
    <fgp-link href="https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html">
        <slot />
    </fgp-link>
</template>

<script>
import Vue from 'vue';
import fgpLink from '@/components/link/link';

export default Vue.component('fgp-java-system-properties-link', {
    components: { fgpLink }
});
</script>
