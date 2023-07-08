<template>
    <header>
        <nav class="navbar navbar-expand-sm navbar-light">
            <a class="navbar-brand" href="https://github.com/siouan" :title="$t('siouan.organizationTooltip')">
                <img alt="Siouan logo" src="@/assets/siouan-icon.svg" width="32" height="32" />
            </a>
            <a
                class="text-dark"
                href="https://github.com/siouan/frontend-gradle-plugin"
                :title="$t('siouan.projectTooltip')"
            >
                <i class="fab fa-github fa-2x"></i>
            </a>
            <button
                class="navbar-toggler"
                type="button"
                data-toggle="collapse"
                data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent"
                aria-expanded="false"
                aria-label="Toggle navigation"
                @click="toggleMenuVisible()"
            >
                <span class="navbar-toggler-icon"></span>
            </button>

            <div id="navbarSupportedContent" class="collapse navbar-collapse" :class="{ show: menuVisible }">
                <ul class="navbar-nav ml-auto mr-auto">
                    <li class="nav-item">
                        <fgp-site-link
                            :path="fgp.paths.overview"
                            class="nav-link fgp-menu-item"
                            @click.native="hideMenu()"
                        >
                            {{ $t("menu.overview") }}
                        </fgp-site-link>
                    </li>
                    <li class="nav-item">
                        <fgp-site-link
                            :path="fgp.paths.gettingStarted"
                            class="nav-link fgp-menu-item"
                            @click.native="hideMenu()"
                        >
                            {{ $t("menu.installation") }}
                        </fgp-site-link>
                    </li>
                    <li class="nav-item">
                        <fgp-site-link
                            :path="fgp.paths.configuration"
                            class="nav-link fgp-menu-item"
                            @click.native="hideMenu()"
                        >
                            {{ $t("menu.configuration") }}
                        </fgp-site-link>
                    </li>
                    <li class="nav-item">
                        <fgp-site-link
                            :path="fgp.paths.tasks"
                            class="nav-link fgp-menu-item"
                            @click.native="hideMenu()"
                        >
                            {{ $t("menu.tasks") }}
                        </fgp-site-link>
                    </li>
                    <li class="nav-item">
                        <fgp-site-link :path="fgp.paths.faqs" class="nav-link fgp-menu-item" @click.native="hideMenu()">
                            {{ $t("menu.faq") }}
                        </fgp-site-link>
                    </li>
                </ul>
            </div>

            <form v-show="fgp.i18nEnabled">
                <label>
                    <select class="custom-select" @change="selectLang($event.target.value)">
                        <option value="en" selected>{{ $t("lang.english") }}</option>
                        <option value="fr">{{ $t("lang.french") }}</option>
                    </select>
                </label>
            </form>
        </nav>
    </header>
</template>

<script>
import Vue from 'vue';
import fgpAppConfig from '@/mixin/app-config';
import fgpSiteLink from '@/components/link/site-link';

export default Vue.component('fgp-header', {
    components: { fgpSiteLink },
    mixins: [fgpAppConfig],
    data: () => ({
        menuVisible: false,
    }),
    methods: {
        hideMenu() {
            this.toggleMenuVisible(false);
        },
        selectLang(lang) {
            this.$emit('lang-change', lang);
        },
        toggleMenuVisible() {
            this.menuVisible = !this.menuVisible;
        },
    }
});
</script>

<style scoped>
.fgp-menu-item {
    font-family: EtelkaTextPro, Helvetica, Arial, sans-serif;
}
</style>
