// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    app: {
        baseURL: '/frontend-gradle-plugin/',
        head: {
            base: {
                href: '/frontend-gradle-plugin/',
            },
            meta: [
                { 'http-equiv': 'X-UA-Compatible', content: 'IE=edge' },
                { 'http-equiv': 'content-type', content: 'text/html' },
                {
                    'http-equiv': 'Cache-Control',
                    content: 'no-cache,no-store,must-revalidate',
                },
                { 'http-equiv': 'Pragma', content: 'no-cache' },
                { 'http-equiv': 'Expires', content: '-1' },
                { name: 'robots', content: 'index,follow' },
                { name: 'og:locale', content: 'en_US' },
                { name: 'og:type', content: 'website' },
                { name: 'og:site_name', content: 'Frontend Gradle plugin' },
                { name: 'google', content: 'nositelinkssearchbox' },
            ],
            link: [{ rel: 'icon', type: 'image/x-icon', href: 'siouan-icon.png' }],
        },
    },
    components: [
        {
            path: '~/components',
            pathPrefix: false,
            prefix: 'Fgp',
        },
    ],
    css: ['~/assets/scss/custom.scss', '@fortawesome/fontawesome-free/css/all.css'],
    devServer: {
        port: 10000,
    },
    devtools: {
        enabled: true,
    },
    modules: ['@nuxtjs/i18n', '@pinia/nuxt', '@nuxt/eslint'],
    runtimeConfig: {
        public: {
            canonicalBaseUrl: 'https://siouan.github.io/frontend-gradle-plugin',
            i18nEnabled: false,
            latestMajorRelease: 8,
            paths: {
                configuration: '/configuration/',
                faqs: '/faqs/',
                gettingStarted: '/getting-started/',
                overview: '/',
                tasks: '/node-corepack-npm-pnpm-yarn-tasks/',
            },
        },
    },
    srcDir: 'src/',
    typescript: {
        typeCheck: true,
    },
});
