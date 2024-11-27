<template>
    <FgpSelect
        :options="options"
        :values="[mainStore.selectedRelease]"
        size="sm"
        @change="navigateToRelease($event)"
        aria-label="Selector of the documentation of a given release" />
</template>

<script setup lang="ts">
const router = useRouter();
const runtimeConfig = useRuntimeConfig();
const mainStore = useMainStore();

const options = [{
    label: '10.x',
    value: 10
}, {
    label: '9.x',
    value: 9
}, {
    label: '8.x',
    value: 8
}, {
    label: '7.x',
    value: 7
}, {
     label: '6.x',
     value: 6
}, {
    label: '5.x',
    value: 5
}];

function navigateToRelease(event: Event): void {
    const selectedRelease = Number((<HTMLSelectElement>event.target)?.value);
    const [previousSelectedRelease, canonicalPath] = parseReleaseAndCanonicalPath(router.currentRoute.value.path, runtimeConfig.public.latestMajorRelease);
    mainStore.setSelectedRelease(selectedRelease);
    router.push(`${mainStore.selectedReleasePath}${canonicalPath}`);
}
</script>
