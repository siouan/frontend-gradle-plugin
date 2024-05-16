<template>
    <FgpLink :href="gradleUrl">{{ $t('navigation.gradle.label') }}</FgpLink>
</template>

<script setup lang="ts">
import { computed } from 'vue';

interface Props {
    readonly path?: string | null;
    readonly hoverStyleDisabled?: boolean;
}

const GRADLE_BASE_URL = 'https://gradle.org';

const props = withDefaults(defineProps<Props>(), {
    path: '/',
    hoverStyleDisabled: false,
});
const gradleUrl = computed(() => {
    if (props.path) {
        return `${GRADLE_BASE_URL}${props.path}`;
    }
    return GRADLE_BASE_URL;
});
</script>
