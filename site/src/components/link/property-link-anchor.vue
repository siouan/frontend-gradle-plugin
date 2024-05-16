<template>
    <FgpSiteLinkAnchor :name="name" :path="$config.public.paths.configuration" />
</template>

<script setup lang="ts">
interface Props {
    readonly name: string;
}

defineProps<Props>();
</script>
