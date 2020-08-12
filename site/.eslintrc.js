module.exports = {
    root: true,
    env: {
        browser: true,
        node: true,
    },
    parserOptions: {
        parser: "babel-eslint",
    },
    extends: ["plugin:vue/recommended", "eslint:recommended", "prettier/vue"],
    // extends: ['@nuxtjs', 'prettier', 'prettier/vue', 'plugin:prettier/recommended', 'plugin:nuxt/recommended'],
    plugins: ["vue", "prettier"],
    // add your custom rules here
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off"
    }
}
