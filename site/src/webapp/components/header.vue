<template>
    <header>
        <nav class="navbar navbar-expand-sm navbar-light bg-light">
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
            <router-link to="/" class="nav-link text-dark"><i class="fas fa-home fa-2x"></i></router-link>
            <!--<button
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
                        <router-link to="/home" class="nav-link fgp-menu-item" @click.native="hideMenu()">
                            {{ $t("menu.home") }}
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/installation" class="nav-link fgp-menu-item" @click.native="hideMenu()">
                            {{ $t("menu.installation") }}
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/configuration" class="nav-link fgp-menu-item" @click.native="hideMenu()">
                            {{ $t("menu.configuration") }}
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/tasks" class="nav-link fgp-menu-item" @click.native="hideMenu()">
                            {{ $t("menu.tasks") }}
                        </router-link>
                    </li>
                </ul>
            </div>

            <form>
                <select class="custom-select" @change="selectLang($event.target.value)">
                    <option value="en" selected>{{ $t("lang.english") }}</option>
                    <option value="fr">{{ $t("lang.french") }}</option>
                </select>
            </form>-->
        </nav>
    </header>
</template>

<script>
import Vue from "vue";

export default Vue.component("fgp-header", {
    data: () => ({
        menuVisible: false
    }),
    methods: {
        hideMenu: function() {
            this.toggleMenuVisible(false);
        },
        selectLang: function(lang) {
            //console.log("Lang change from", this.$root.$i18n.locale, "to", lang);
            this.$emit("lang-change", lang);
        },
        toggleMenuVisible: function() {
            //console.log("Menu visibility toggled");
            this.menuVisible = !this.menuVisible;
        }
    }
});
</script>

<style scoped>
.fgp-menu-item {
    font-family: EtelkaTextPro, Helvetica, Arial, sans-serif;
}
</style>
