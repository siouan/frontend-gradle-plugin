pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.4"
        id("com.gradle.plugin-publish") version "0.12.0"
    }
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("com.gradle.enterprise")
}

rootProject.name = "frontend-gradle-plugin"
include("site")

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
