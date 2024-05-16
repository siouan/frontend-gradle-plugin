<template>
    <FgpSiteLink :id="name" :path="pathWithAnchor" class="text-info">#</FgpSiteLink>
</template>

<script setup lang="ts">
import { computed } from 'vue';

interface Props {
    readonly name: string;
    readonly path: string;
}

const props = defineProps<Props>();

const runtimeConfig = useRuntimeConfig();
const pathWithAnchor = computed(() => `${props.path}#${props.name}`);
</script>
