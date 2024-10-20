val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.develocity") version "3.18.1"
        id("com.gradle.plugin-publish") version "1.3.0"
        id("org.sonarqube") version "5.1.0.4882"
   }
}

plugins {
    id("com.gradle.develocity")
}

rootProject.name = fgpArtifactId

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
    }
}
