import { defineStore } from 'pinia';

const PREFERED_SCRIPT_LANGUAGE_ID_STORAGE_KEY = 'preferedScriptLanguageId';
const PREFERED_THEME_ID_STORAGE_KEY = 'preferedThemeId';

export const useMainStore = defineStore('mainStore', () => {
    let localStorage: Storage;
    const systemThemeId: Ref<ThemeIdType | null> = ref(null);
    const preferedScriptLanguageId: Ref<ScriptLanguageIdType> = ref(ScriptLanguageId.GROOVY);
    const preferedThemeId: Ref<PreferedThemeIdType> = ref(PreferedThemeId.SYSTEM);
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

    function initialize(storage: Storage, themeId: ThemeIdType): void {
        localStorage = storage;
        systemThemeId.value = themeId;
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

    return {
        initialize,
        preferedScriptLanguageId,
        preferedThemeId,
        setPreferedScriptLanguageId,
        setPreferedThemeId,
        themeId
    };
});
