<template>
    <FgpLink :href="href">{{ $t(internalLabelKey) }}</FgpLink>
</template>

<script setup lang="ts">
interface Props {
    readonly labelKey?: string | null;
    readonly version?: 1 | 2 | null;
}

const props = withDefaults(defineProps<Props>(), {
    labelKey: null,
    version: 2
});

const href = computed(() => (props.version === 1) ? 'https://classic.yarnpkg.com/' : 'https://yarnpkg.com/' );
const internalLabelKey = computed<string>(() => {
    if (props.labelKey) {
        return props.labelKey;
    }
    return (props.version === 1) ? 'navigation.yarnClassic.label' : 'navigation.yarnBerry.label';
});
</script>
