val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.17.4"
        id("com.gradle.plugin-publish") version "1.2.1"
        id("org.sonarqube") version "5.0.0.4638"
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
