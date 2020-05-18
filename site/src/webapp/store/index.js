import Vue from "vue";
import Vuex from "vuex";
import { fgpGradleScripts } from "./module/gradle-scripts";

Vue.use(Vuex);

const STORE_MODULE_GRADLE_SCRIPTS = "gradleScripts";
const fgpStore = new Vuex.Store({
    strict: process.env.NODE_ENV !== "production"
});

fgpStore.registerModule(STORE_MODULE_GRADLE_SCRIPTS, fgpGradleScripts);

export { STORE_MODULE_GRADLE_SCRIPTS, fgpStore };
