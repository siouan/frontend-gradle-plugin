import Vue from 'vue';
import VueI18n from 'vue-i18n';
import en from '@/locale/en';

Vue.use(VueI18n);

export default ({ app }) => {
    app.i18n = new VueI18n({
        fallbackLocale: false,
        locale: 'en',
        missing: (locale, key) => {
            return `??? ${key} ???`;
        },
        messages: { en }
    });
}
