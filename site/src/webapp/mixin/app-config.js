import fgpPaths from "../paths";

export default {
    data() {
        return {
            fgp: {
                i18nEnabled: process.env.VUE_APP_FEATURE_I18N_ENABLED === "true",
                paths: fgpPaths
            }
        };
    }
};
