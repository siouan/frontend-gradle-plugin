import { defineNuxtModule, extendRouteRules } from '@nuxt/kit'

export default defineNuxtModule ({
    setup(options, nuxt) {
        extendRouteRules('/node-npm-npx-yarn-tasks/', {
            redirect: {
                to: `${nuxt.options.app.baseURL}6/node-corepack-npm-pnpm-yarn-tasks/`,
                statusCode: 308
            }
        });
    }
});
