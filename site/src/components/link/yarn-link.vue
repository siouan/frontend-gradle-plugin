<template>
    <FgpLink :href="href">{{ internalLabel }}</FgpLink>
</template>

<script setup lang="ts">
const { t } = useI18n();
interface Props {
    readonly labelKey?: string | null;
    readonly label?: string | null;
    readonly version?: 1 | 2 | null;
}

const props = withDefaults(defineProps<Props>(), {
    labelKey: null,
    version: 2
});

const href = computed(() => (props.version === 1) ? 'https://classic.yarnpkg.com/' : 'https://yarnpkg.com/' );
const internalLabel = computed<string>(() => {
    if (props.label) {
        return props.label
    }
    if (props.labelKey) {
        return t(props.labelKey);
    }
    return (props.version === 1) ? t('navigation.yarnClassic.label') : t('navigation.yarnBerry.label');
});
</script>
