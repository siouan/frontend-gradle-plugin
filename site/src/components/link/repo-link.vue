<template>
    <FgpLink :href="href"><slot /></FgpLink>
</template>

<script setup lang="ts">
const REPO_BASE_URL = 'https://github.com/siouan/frontend-gradle-plugin';

interface Props {
    readonly path?: string | null;
}

const props = withDefaults(defineProps<Props>(), {
    path: null,
});
const href = computed<string>(() => {
    if (props.path === null) {
        return REPO_BASE_URL;
    }
    return `${REPO_BASE_URL}${props.path}`;
});
</script>
