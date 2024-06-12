<template>
    <FgpLink :href="`https://docs.gradle.org${path}`" :hover-style-disabled="hoverStyleDisabled"><slot /></FgpLink>
</template>

<script setup lang="ts">
interface Props {
    readonly path?: string | null;
    readonly hoverStyleDisabled?: boolean;
}

withDefaults(defineProps<Props>(), {
    path: null,
    hoverStyleDisabled: false,
});
</script>
