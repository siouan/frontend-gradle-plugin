<template>
    <FgpGithubLink :path="internalPath"><slot /></FgpGithubLink>
</template>

<script setup lang="ts">
const REPOSITORY_BASE_PATH = 'siouan/frontend-gradle-plugin';

interface Props {
    readonly path?: string | null;
}

const props = withDefaults(defineProps<Props>(), {
    path: null,
});
const internalPath = computed<string>(() => {
    if (props.path === null) {
        return REPOSITORY_BASE_PATH;
    }
    return `${REPOSITORY_BASE_PATH}${props.path}`;
});
</script>
