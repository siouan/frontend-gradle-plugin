<template>
    <FgpLink :href="nodeUrl">{{ internalLabel }}</FgpLink>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const { t } = useI18n();
const NODEJS_BASE_URL = 'https://nodejs.org';

interface Props {
    readonly path?: string | null;
    readonly label?: string | null;
}

const props = withDefaults(defineProps<Props>(), {
    path: '/',
    label: null,
});
const internalLabel = computed<string>(() => props.label || t('navigation.nodejs.label'));
const nodeUrl = computed<string>(() => (props.path ? `${NODEJS_BASE_URL}${props.path}` : NODEJS_BASE_URL));
</script>
