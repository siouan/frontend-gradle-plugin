<template>
    <FgpLink :href="internalUrl">{{ internalLabel }}</FgpLink>
</template>

<script setup lang="ts">
const { t } = useI18n();
const COREPACK_BASE_URL = 'https://github.com/nodejs/corepack';

interface Props {
    readonly path?: string | null;
    readonly label?: string | null;
}

const props = defineProps<Props>();
const internalLabel = computed<string>(() => props.label || t('navigation.corepack.label'));
const internalUrl = computed<string>(() => (props.path ? `${COREPACK_BASE_URL}${props.path}` : COREPACK_BASE_URL));
</script>
