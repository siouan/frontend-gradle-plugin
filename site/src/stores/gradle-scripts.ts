import { defineStore } from 'pinia';

enum TabKey {
    GROOVY = 'groovy',
    KOTLIN = 'kotlin',
}

export const useMainStore = defineStore('mainStore', () => {
    const tabKey: Ref<TabKey> = ref(TabKey.GROOVY);
    const groovyTabVisible = computed(() => tabKey.value === TabKey.GROOVY);
    const kotlinTabVisible = computed(() => tabKey.value === TabKey.KOTLIN);

    function setGroovyTabVisible() {
        tabKey.value = TabKey.GROOVY;
    }
    function setKotlinTabVisible() {
        tabKey.value = TabKey.KOTLIN;
    }
    return {
        tabKey,
        groovyTabVisible,
        kotlinTabVisible,
        setGroovyTabVisible,
        setKotlinTabVisible,
    };
});
