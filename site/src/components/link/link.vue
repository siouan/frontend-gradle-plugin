<template>
    <a :href="href" :class="hoverClass"><slot /></a>
</template>

<script setup lang="ts">
import { computed } from 'vue';

interface Props {
    readonly href: string;
    readonly hoverStyleDisabled?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    hoverStyleDisabled: false,
});
const hoverClass = computed<string | null>(() => (props.hoverStyleDisabled ? 'fgp-link-no-hover' : null));
</script>

<style scoped>
.fgp-link-no-hover {
    text-decoration: unset;
}
</style>
