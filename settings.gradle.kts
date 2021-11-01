val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.7.1"
        id("com.gradle.plugin-publish") version "0.16.0"
        id("org.sonarqube") version "3.3"
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
