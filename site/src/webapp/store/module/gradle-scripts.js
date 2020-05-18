const GROOVY_TAB_KEY = "groovy";
const KOTLIN_TAB_KEY = "kotlin";

function setGradleScriptTabVisible(state, tabKey) {
    state.tabKey = tabKey;
}

export const fgpGradleScripts = {
    namespaced: true,
    state: () => {
        return {
            tabKey: GROOVY_TAB_KEY
        };
    },
    getters: {
        tabVisible(state) {
            return tabKey => state.tabKey === tabKey;
        },
        groovyTabVisible(state, getters) {
            return getters.tabVisible(GROOVY_TAB_KEY);
        },
        kotlinTabVisible(state, getters) {
            return getters.tabVisible(KOTLIN_TAB_KEY);
        }
    },
    mutations: {
        setGroovyTabVisible(state) {
            setGradleScriptTabVisible(state, GROOVY_TAB_KEY);
        },
        setKotlinTabVisible(state) {
            setGradleScriptTabVisible(state, KOTLIN_TAB_KEY);
        }
    }
};
