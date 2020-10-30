val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.4.1"
        id("com.gradle.plugin-publish") version "0.12.0"
    }
}

plugins {
    id("com.gradle.enterprise")
}

rootProject.name = fgpArtifactId
include("site")

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
