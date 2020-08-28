export default {
    /*
     ** Nuxt rendering mode
     ** See https://nuxtjs.org/api/configuration-mode
     */
    mode: "universal",
    /*
     ** Nuxt target
     ** See https://nuxtjs.org/api/configuration-target
     */
    target: "static",
    /*
     ** Headers of the page
     ** See https://nuxtjs.org/api/configuration-head
     */
    head: {
        meta: [
            { charset: "utf-8" },
            { "http-equiv": "X-UA-Compatible", content: "IE=edge" },
            { "http-equiv": "content-type", content: "text/html" },
            { "http-equiv": "Cache-Control", content: "no-cache,no-store,must-revalidate" },
            { "http-equiv": "Pragma", content: "no-cache" },
            { "http-equiv": "Expires", content: "-1" },
            { name: "viewport", content: "width=device-width,initial-scale=1" },
            { name: "robots", content: "index,follow" },
            { name: "og:locale", content: "en_US" },
            { name: "og:type", content: "website" },
            { name: "og:url", content: process.env.FGP_WEBSITE_URL || "" },
            { name: "og:site_name", content: process.env.FGP_WEBSITE_NAME || "" },
            { name: "google", content: "nositelinkssearchbox" }
        ],
        link: [
            { rel: "icon", type: "image/x-icon", href: "siouan-icon.png" },
        ]
    },
    /*
     ** Global CSS
     */
    css: ["@/assets/scss/custom.scss", "@fortawesome/fontawesome-free/css/all.css"],
    /*
     ** Plugins to load before mounting the App
     ** https://nuxtjs.org/guide/plugins
     */
    plugins: ["@/plugins/i18n.js"],
    /*
     ** Auto import components
     ** See https://nuxtjs.org/api/configuration-components
     */
    components: true,
    /*
     ** Nuxt.js dev-modules
     */
    buildModules: [
        // Doc: https://github.com/nuxt-community/eslint-module
        "@nuxtjs/eslint-module",
    ],
    /*
     ** Nuxt.js modules
     */
    modules: [],
    /*
     ** Build configuration
     ** See https://nuxtjs.org/api/configuration-build/
     */
    build: {},
    router: {
        base: "/frontend-gradle-plugin/",
        mode: "history",
        linkExactActiveClass: "active",
        extendRoutes(routes, resolve) {
            routes.push({
                path: '*',
                component: resolve(__dirname, 'src/pages/not-found.vue')
            })
        }
    },
    srcDir: "src/",
    server: {
        port: 10000
    },
    env: {
        FGP_WEBSITE_DESCRIPTION: "Gradle plugin to build frontend applications with Node.js/NPM/Yarn: distribution management, configurable tasks (build, test, publish), NPX support",
        FGP_WEBSITE_NAME: "Frontend Gradle plugin",
        FGP_WEBSITE_TITLE: "Gradle Node plugin to build JS applications with NPM or Yarn",
        FGP_WEBSITE_URL: "https://siouan.github.io/frontend-gradle-plugin/",
        FGP_FEATURE_I18N_ENABLED: false
    }
};
