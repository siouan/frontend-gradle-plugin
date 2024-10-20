<template>
    <FgpLink :href="href"><slot /></FgpLink>
</template>

<script setup lang="ts">
const GITHUB_BASE_URL = 'https://github.com/';

interface Props {
    readonly path?: string | null;
}

const props = withDefaults(defineProps<Props>(), {
    path: null,
});
const href = computed<string>(() => {
    if (props.path === null) {
        return GITHUB_BASE_URL;
    }
    return `${GITHUB_BASE_URL}${props.path}`;
});
</script>
