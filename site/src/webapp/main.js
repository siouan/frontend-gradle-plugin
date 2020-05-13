import Vue from "vue";
import VueRouter from "vue-router";
import VueI18n from "vue-i18n";
import en from "./i18n/en";
import fr from "./i18n/fr";
import FgpApp from "./app.vue";
import FGP_ROUTES from "./routes.js";
import "bootstrap/dist/css/bootstrap.css";
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";

Vue.config.productionTip = true;
Vue.use(VueRouter);
Vue.use(VueI18n);

const FGP_ROUTER = new VueRouter({
    mode: "history",
    routes: FGP_ROUTES,
    base: "/frontend-gradle-plugin",
    linkExactActiveClass: "active"
});
const FGP_I18N = new VueI18n({
    fallbackLocale: false,
    locale: "en",
    missing: (locale, key) => {
        return `??? ${key} ???`;
    },
    messages: {
        en,
        fr
    }
});

new Vue({
    router: FGP_ROUTER,
    i18n: FGP_I18N,
    render: h => h(FgpApp)
}).$mount("#app");
