<template>
    <div id="app" class="text-body bg-body" :class="containerClass" :data-bs-theme="mainStore.themeId">
        <NuxtLayout>
            <NuxtPage />
        </NuxtLayout>
    </div>
</template>

<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
const router = useRouter();
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
    const selectedRelease = parseReleaseAndCanonicalPath(router.currentRoute.value.path, runtimeConfig.public.latestMajorRelease)[0];
    mainStore.initialize({
        localStorage,
        systemThemeId: window.matchMedia('(prefers-color-scheme: dark)').matches ? ThemeId.DARK : ThemeId.LIGHT,
        latestMajorRelease: runtimeConfig.public.latestMajorRelease,
        selectedRelease
    });
    afterMounted.value = true;
});
</script>
