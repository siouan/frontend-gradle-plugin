import { defineStore } from 'pinia';

const PREFERED_SCRIPT_LANGUAGE_ID_STORAGE_KEY = 'preferedScriptLanguageId';
const PREFERED_THEME_ID_STORAGE_KEY = 'preferedThemeId';

export interface StoreInitializeOptions {
    localStorage: Storage;
    systemThemeId: ThemeIdType;
    latestMajorRelease: number;
    selectedRelease: number;
}

export const useMainStore = defineStore('mainStore', () => {
    let localStorage: Storage;
    const latestMajorRelease = ref(0);
    const systemThemeId: Ref<ThemeIdType | null> = ref(null);
    const preferedScriptLanguageId: Ref<ScriptLanguageIdType> = ref(ScriptLanguageId.GROOVY);
    const preferedThemeId: Ref<PreferedThemeIdType> = ref(PreferedThemeId.SYSTEM);
    const selectedRelease: Ref<number | null> = ref(null);
    const selectedReleasePath = computed(() =>
        !selectedRelease.value || (selectedRelease.value === latestMajorRelease.value) ? '' : `/${selectedRelease.value}`);
    const themeId: Ref<ThemeIdType> = computed(() => {
        switch (preferedThemeId.value) {
        case PreferedThemeId.DARK:
            return ThemeId.DARK;
        case PreferedThemeId.SYSTEM:
            return systemThemeId.value || ThemeId.LIGHT;
        case PreferedThemeId.LIGHT:
        default:
            return ThemeId.LIGHT;
        }
    });

    function initialize(options: StoreInitializeOptions): void {
        localStorage = options.localStorage;
        systemThemeId.value = options.systemThemeId;
        const preferedThemeIdFromLocalStorageAsString: string | null = localStorage.getItem(PREFERED_THEME_ID_STORAGE_KEY);
        const preferedThemeIdFromLocalStorage =
            preferedThemeIdFromLocalStorageAsString && (<any>Object).values(PreferedThemeId).includes(preferedThemeIdFromLocalStorageAsString)
            ? preferedThemeIdFromLocalStorageAsString as PreferedThemeIdType
            : PreferedThemeId.SYSTEM;
        setPreferedThemeId(preferedThemeIdFromLocalStorage);
        const preferedLanguageIdFromLocalStorageAsString: string | null = localStorage.getItem(PREFERED_SCRIPT_LANGUAGE_ID_STORAGE_KEY);
        const preferedLanguageIdFromLocalStorage =
            preferedLanguageIdFromLocalStorageAsString && (<any>Object).values(ScriptLanguageId).includes(preferedLanguageIdFromLocalStorageAsString)
            ? preferedLanguageIdFromLocalStorageAsString as ScriptLanguageIdType
            : ScriptLanguageId.GROOVY;
        setPreferedScriptLanguageId(preferedLanguageIdFromLocalStorage);
        latestMajorRelease.value = options.latestMajorRelease;
        setSelectedRelease(options.selectedRelease);
    }

    function setPreferedThemeId(themeId: PreferedThemeIdType): void {
        if (themeId === PreferedThemeId.SYSTEM) {
            localStorage.removeItem(PREFERED_THEME_ID_STORAGE_KEY);
        } else {
            localStorage.setItem(PREFERED_THEME_ID_STORAGE_KEY, themeId);
        }
        preferedThemeId.value = themeId;
    }

    function setPreferedScriptLanguageId(scriptLanguageId: ScriptLanguageIdType): void {
        localStorage.setItem(PREFERED_SCRIPT_LANGUAGE_ID_STORAGE_KEY, scriptLanguageId);
        preferedScriptLanguageId.value = scriptLanguageId;
    }

    function setSelectedRelease(release: number): void {
        selectedRelease.value = release;
    }

    return {
        initialize,
        preferedScriptLanguageId,
        preferedThemeId,
        selectedRelease,
        selectedReleasePath,
        setPreferedScriptLanguageId,
        setPreferedThemeId,
        setSelectedRelease,
        themeId
    };
});
