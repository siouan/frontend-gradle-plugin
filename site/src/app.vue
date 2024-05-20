<template>
    <div id="app" class="text-body bg-body" :class="containerClass" :data-bs-theme="mainStore.themeId">
        <NuxtLayout>
            <NuxtPage />
        </NuxtLayout>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const mainStore = useMainStore();

const afterMounted = ref(false);
const containerClass = computed(() => {
    if (afterMounted.value) {
        return { visible: true };
    } else {
        return { invisible: true };
    }
});

onMounted(() => {
    mainStore.initialize(
        localStorage,
        window.matchMedia('(prefers-color-scheme: dark)').matches ? ThemeId.DARK : ThemeId.LIGHT);
    afterMounted.value = true;
});
</script>
