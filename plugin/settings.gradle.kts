val fgpArtifactId: String by extra

pluginManagement {
    plugins {
        id("com.gradle.develocity") version "3.17.4"
        id("com.gradle.plugin-publish") version "1.2.1"
        id("org.sonarqube") version "5.0.0.4638"
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
