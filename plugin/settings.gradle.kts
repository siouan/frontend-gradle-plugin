val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.13.1"
        id("com.gradle.plugin-publish") version "1.2.0"
        id("org.sonarqube") version "4.0.0.2929"
   }
}

plugins {
    id("com.gradle.enterprise")
}

rootProject.name = fgpArtifactId

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
