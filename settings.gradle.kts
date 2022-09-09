val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.10.2"
        id("com.gradle.plugin-publish") version "0.21.0"
        id("org.sonarqube") version "3.4.0.2513"
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
