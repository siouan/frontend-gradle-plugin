module.exports = {
    root: true,
    env: {
        browser: true,
        node: true,
    },
    parserOptions: {
        parser: '@babel/eslint-parser',
        requireConfigFile: false,
        sourceType: 'module'
    },
    extends: ['@nuxtjs/eslint-config', 'prettier'],
    plugins: ['prettier'],
    // add your custom rules here
    rules: {
        'vue/component-definition-name-casing': ['error', 'kebab-case'],
        'quotes': ['error', 'single'],
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off'
    }
}
