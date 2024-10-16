<template>
    <img :src="src" :alt="alt!" :width="width!" :height="height!" />
</template>

<script setup lang="ts">
interface Props {
    readonly src: string;
    readonly alt?: string | null;
    readonly width?: number | null;
    readonly height?: number | null;
}

withDefaults(defineProps<Props>(), {
    alt: null,
});
</script>
