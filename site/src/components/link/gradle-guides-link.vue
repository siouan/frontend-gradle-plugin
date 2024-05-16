<template>
    <FgpLink :href="`https://guides.gradle.org${path}`"><slot /></FgpLink>
</template>

<script setup lang="ts">
interface Props {
    readonly path?: string | null;
}

withDefaults(defineProps<Props>(), {
    path: null,
});
</script>
