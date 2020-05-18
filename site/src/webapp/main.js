import Vue from "vue";
import VueRouter from "vue-router";
import VueI18n from "vue-i18n";
import en from "./i18n/en";
import fgpApp from "./app.vue";
import FGP_ROUTES from "./routes.js";
import { fgpStore } from "./store";
import "@fortawesome/fontawesome-free/js/all.js";
import "../assets/scss/custom.scss";

Vue.config.productionTip = true;
Vue.use(VueRouter);
Vue.use(VueI18n);

const fgpRouter = new VueRouter({
    mode: "history",
    routes: FGP_ROUTES,
    base: "/frontend-gradle-plugin",
    linkExactActiveClass: "active",
    scrollBehavior(to) {
        if (to.hash) {
            return { selector: to.hash };
        } else {
            return { x: 0, y: 0 };
        }
    }
});
const fgpI18n = new VueI18n({
    fallbackLocale: false,
    locale: "en",
    missing: (locale, key) => {
        return `??? ${key} ???`;
    },
    messages: { en }
});

new Vue({
    router: fgpRouter,
    i18n: fgpI18n,
    render: h => h(fgpApp),
    store: fgpStore
}).$mount("#app");
