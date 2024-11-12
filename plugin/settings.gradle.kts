val fgpArtifactId: String by extra

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    plugins {
        id("com.gradle.develocity") version "3.18.1"
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
