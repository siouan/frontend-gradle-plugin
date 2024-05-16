<template>
    <FgpLink :href="href" :title="title">{{ internalLabel }}</FgpLink>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const { t } = useI18n();

interface Props {
    readonly label?: string | null;
    readonly labelKey?: string;
    readonly path?: string;
    readonly title?: string | null;
}

const props = withDefaults(defineProps<Props>(), {
    label: null,
    labelKey: 'navigation.yarnBerry.label',
    path: '',
    title: null,
});

const href = computed<string>(() => `https://yarnpkg.com/${props.path}`);
const internalLabel = computed<string>(() => props.label || t(props.labelKey));
</script>
